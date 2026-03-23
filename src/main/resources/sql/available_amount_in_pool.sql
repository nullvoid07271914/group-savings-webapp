SELECT 
    code,
    SUM(amount) AS total_amount
FROM (
    -- Contribution (positive)
    SELECT
        m.member_code AS code,
        COALESCE(SUM(c.amount), 0) AS amount
    FROM member_tbl m
    LEFT JOIN contribution_tbl c ON c.member_id = m.member_id 
        AND c.pool_id = ?1
        AND c.contribution_date <= ?2
    WHERE m.member_status = 'ACTIVE' 
        AND m.member_type = 'CONTRIBUTOR'
    GROUP BY m.member_code
    
    UNION ALL
    
    -- Profit (positive)
    SELECT
        m.member_code AS code,
        COALESCE(SUM(lma.contribution_percentage * p.amount_paid), 0) AS amount
    FROM loan_tbl l
    INNER JOIN payment_tbl p ON p.loan_id = l.loan_id
    INNER JOIN loan_member_allocation_tbl lma ON lma.loan_id = l.loan_id
    INNER JOIN member_tbl m ON m.member_id = lma.member_id
    GROUP BY m.member_code
    
    UNION ALL
    
    -- Deduction (negative)
    SELECT
        m.member_code AS code,
        -COALESCE(SUM(lma.contribution_amount), 0) AS amount
    FROM loan_tbl l
    JOIN loan_member_allocation_tbl lma ON lma.loan_id = l.loan_id 
        AND l.loan_status IN ('APPROVED', 'ACTIVE')
    JOIN member_tbl m ON m.member_id = lma.member_id
    WHERE l.date_fully_paid IS NULL
    GROUP BY m.member_code
) combined
GROUP BY code
ORDER BY code
;