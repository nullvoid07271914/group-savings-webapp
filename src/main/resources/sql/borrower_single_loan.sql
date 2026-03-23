use group_savings_db;

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
	l.date_fully_paid,
	l.total_amount,
	l.due_date,
	l.amortization
 FROM member_tbl m
	JOIN loan_tbl l
	ON l.borrower_id = m.member_id
WHERE m.member_code LIKE ?1
	AND l.loan_code LIKE ?2