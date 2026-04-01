package com.groupsavings.service;

import java.util.List;

import com.groupsavings.model.dto.*;

public interface MemberService {

	MemberResponseDto createMember(MemberRequestDto member);

	MemberResponseDto findMemberByMemberCode(String code);

	List<MemberResponseDto> searchBorrower(MemberNameRequestDto member);

	List<MemberResponseDto> fetchMembers(String status, String type);

	List<MemberTotalContributionDto> fetchMemberTotalContributions();

	MemberContributions fetchMemberContributions(String memberCode);

	List<MemberSummaryProfit> membersSummaryProfits();

	MemberLoanProfitsDto memberLoanProfits(String memberCode);
}
