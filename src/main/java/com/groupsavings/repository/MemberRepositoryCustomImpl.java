package com.groupsavings.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.groupsavings.service.SqlQueryLoader;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

	private final EntityManager entityManager;

	private final SqlQueryLoader sqlLoader;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchMemberTotalContributions() {
		String sql = sqlLoader.getQuery("member_total_contributions.sql");
		Query query = entityManager.createNativeQuery(sql);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchMemberContributions(String memberCode) {
		String sql = sqlLoader.getQuery("member_contributions.sql");
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, memberCode);
		return query.getResultList();
	}

}
