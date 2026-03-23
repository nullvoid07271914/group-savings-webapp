SELECT
	m.member_code,
    m.firstname,
    m.lastname,
    m.join_date,
	COALESCE(SUM(c.amount), 0) AS amount
FROM member_tbl m
	JOIN contribution_tbl c
		ON c.member_id = m.member_id
	WHERE m.member_type = 'CONTRIBUTOR'
		AND m.member_status = 'ACTIVE'
GROUP BY m.member_code;