package com.groupsavings.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.groupsavings.service.SqlQueryLoader;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class LoanRepositoryCustomImpl implements LoanRepositoryCustom {

	private final EntityManager entityManager;

	private final SqlQueryLoader sqlLoader;

	public LoanRepositoryCustomImpl(EntityManager entityManager, SqlQueryLoader sqlLoader) {
		this.entityManager = entityManager;
		this.sqlLoader = sqlLoader;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchMembersPoolAmountForLoan(Long poolId) {
		String sql = sqlLoader.getQuery("available_amount_in_pool.sql");
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
