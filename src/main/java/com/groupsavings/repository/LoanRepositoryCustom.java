package com.groupsavings.repository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepositoryCustom {

	List<Object[]> fetchMembersPoolAmountForLoan(Long poolId, LocalDate loanDateApplied);
}
