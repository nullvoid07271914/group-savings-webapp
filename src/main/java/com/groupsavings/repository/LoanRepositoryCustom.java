package com.groupsavings.repository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepositoryCustom {

	List<Object[]> fetchMembersPoolAmountForLoan(Long poolId, LocalDate loanDateApplied);

	List<Object[]> fetchMemberLoans(String name, String status);

	Object[] fetchMemberLoan(String memberCode, String loanCode);
}
