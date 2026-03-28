SELECT
	m.member_code,
    m.firstname,
    m.lastname,
    l.loan_code,
    l.loan_amount,
    l.interest_rate,
    l.terms,
    l.loan_status,
    l.date_applied,
    l.date_approved,
    l.date_released,
    l.due_date,
    l.total_amount,
    l.amortization,
    p.payment_code,
    p.pay_in_term,
    p.amount_paid,
    p.payment_date,
    p.payment_status
FROM member_tbl m
	JOIN loan_tbl l
		ON l.borrower_id = m.member_id
	LEFT JOIN payment_tbl p
		ON p.loan_id = l.loan_id