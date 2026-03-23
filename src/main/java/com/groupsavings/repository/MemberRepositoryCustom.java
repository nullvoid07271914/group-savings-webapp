package com.groupsavings.repository;

import java.util.List;

public interface MemberRepositoryCustom {

	List<Object[]> fetchMemberTotalContributions();

	List<Object[]> fetchMemberContributions(String memberCode);
}
