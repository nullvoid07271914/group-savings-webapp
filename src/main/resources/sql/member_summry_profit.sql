SELECT 
    m.member_code,
    m.firstname,
    m.lastname,
    m.join_date,
    TRUNCATE(COALESCE(c.total_contribution, 0), 2) AS total_contribution,
    TRUNCATE(COALESCE(p.total_profit, 0), 2) AS total_profit,
    TRUNCATE(COALESCE(c.total_contribution, 0) + COALESCE(p.total_profit, 0), 2) AS total_summary
FROM member_tbl m

LEFT JOIN (
    SELECT
		member_id,
		COALESCE(SUM(amount), 0) AS total_contribution
	FROM contribution_tbl
    WHERE pool_id = ?1
    GROUP BY member_id
) c ON c.member_id = m.member_id

LEFT JOIN (
    SELECT
		m.member_id,
        COALESCE(SUM((p.amount_paid - (l.loan_amount / l.terms)) * lma.contribution_percentage), 0) AS total_profit
    FROM loan_tbl l
    JOIN payment_tbl p
		ON p.loan_id = l.loan_id
    JOIN loan_member_allocation_tbl lma
		ON lma.loan_id = l.loan_id
    JOIN member_tbl m
		ON m.member_id = lma.member_id
    GROUP BY m.member_id
) p ON p.member_id = m.member_id

WHERE m.member_status = 'ACTIVE'
    AND m.member_type = 'CONTRIBUTOR'
ORDER BY m.member_code