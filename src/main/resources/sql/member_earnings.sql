SELECT
    m.member_code,
    m.firstname,
    m.lastname,
    m.join_date,
    l.loan_code,
    bm.member_code AS borrower_code,
    bm.firstname AS borrower_firstname,
    bm.lastname AS borrower_lastname,
    l.loan_amount,
    l.total_amount,
    l.date_released,
    lma.contribution_amount,
    lma.contribution_percentage,
    p.pay_in_term,
    p.amount_paid,
    p.payment_date,
    TRUNCATE(COALESCE(p.amount_paid - (l.loan_amount / l.terms)) * lma.contribution_percentage, 0), 2) AS loan_profit
FROM loan_tbl l
         LEFT JOIN payment_tbl p
              ON p.loan_id = l.loan_id
         LEFT JOIN loan_member_allocation_tbl lma
              ON lma.loan_id = l.loan_id
         JOIN member_tbl m
              ON m.member_id = lma.member_id
         JOIN member_tbl bm
              ON bm.member_id = l.borrower_id
WHERE m.member_code = ?1