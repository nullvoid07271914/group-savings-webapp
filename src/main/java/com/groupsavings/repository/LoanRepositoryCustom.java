package com.groupsavings.repository;

import java.util.List;

public interface LoanRepositoryCustom {

	List<Object[]> fetchMembersPoolAmountForLoan(Long poolId);
}
