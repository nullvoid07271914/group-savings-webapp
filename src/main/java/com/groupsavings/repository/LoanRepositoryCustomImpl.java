package com.groupsavings.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.groupsavings.service.SqlQueryLoader;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LoanRepositoryCustomImpl implements LoanRepositoryCustom {

	private final EntityManager entityManager;

	private final SqlQueryLoader sqlLoader;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchMembersPoolAmountForLoan(Long poolId, LocalDate loanDateApplied) {
		String sql = sqlLoader.getQuery("available_amount_in_pool.sql");
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, poolId);
		query.setParameter(2, loanDateApplied);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchMemberLoans(String name, String status) {
		String sql = sqlLoader.getQuery("borrower_loans.sql");

		boolean hasName = false;
		boolean hasStatus = false;

		boolean hasAppend = false;
		if (Objects.nonNull(name) && !name.isBlank()) {
			sql = sql + " WHERE (m.firstname LIKE ?1 OR m.lastname LIKE ?2) ";
			hasAppend = true;
			hasName = true;
		}

		if (!Objects.equals("ALL", status)) {
			sql = sql + (hasAppend ? " AND " : " WHERE ");
			sql = sql + " l.loan_status = ?" + (hasAppend ? "3" : "1");
			hasStatus = true;
		}

		sql = sql + " ORDER BY m.member_code";

		Query query = entityManager.createNativeQuery(sql);

		if (hasName) {
			String queryName = "%" + name + "%";
			query.setParameter(1, queryName);
			query.setParameter(2, queryName);
		}

		if (hasName && hasStatus) {
			query.setParameter(3, status);
		} else if (hasStatus) {
			query.setParameter(1, status);
		}

		return query.getResultList();
	}

	@Override
	public Object[] fetchMemberLoan(String memberCode, String loanCode) {
		String sql = sqlLoader.getQuery("borrower_single_loan.sql");
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, memberCode);
		query.setParameter(2, loanCode);
		return (Object[]) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchMembersSummaryProfit(Long poolId) {
		String sql = sqlLoader.getQuery("member_summry_profit.sql");
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, poolId);
		return query.getResultList();
	}

//	@Override
//    public BigDecimal getTotalLoanAmountByStatusNative(String status) {
//        String sql = sqlLoader.getQuery("total_loan_by_status.sql");
//        
//        Query query = entityManager.createNativeQuery(sql);
//        query.setParameter("status", status);
//        
//        return (BigDecimal) query.getSingleResult();
//    }
//    
//    @Override
//    public List<Object[]> getLoanSummaryReport(Map<String, Object> params) {
//        String sql = sqlLoader.getQuery("loan_summary_report.sql");
//        
//        Query query = entityManager.createNativeQuery(sql);
//        params.forEach(query::setParameter);
//        
//        return query.getResultList();
//    }
//    
//    @Override
//    public List<Object[]> getMemberLoanHistory(Long memberId) {
//        String sql = sqlLoader.getQuery("member_loan_history.sql");
//        
//        Query query = entityManager.createNativeQuery(sql);
//        query.setParameter("memberId", memberId);
//        
//        return query.getResultList();
//    }
//    
//    @Override
//    public List<Object[]> getOverdueLoansReport() {
//        String sql = sqlLoader.getQuery("overdue_loans_report.sql");
//        
//        Query query = entityManager.createNativeQuery(sql);
//        return query.getResultList();
//    }

}
