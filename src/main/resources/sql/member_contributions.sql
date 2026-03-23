SELECT
	m.member_code,
    m.firstname,
    m.lastname,
    m.join_date,
    c.contribution_code,
	c.amount,
	c.contribution_date,
	c.payment_method,
	c.reference_number,
	c.month_term
FROM member_tbl m
	JOIN contribution_tbl c
		ON c.member_id = m.member_id
	WHERE m.member_type = 'CONTRIBUTOR'
		AND m.member_status = 'ACTIVE'
        AND m.member_code = ?1;