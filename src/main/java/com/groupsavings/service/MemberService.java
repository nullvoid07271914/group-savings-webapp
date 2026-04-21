package com.groupsavings.service;

import java.util.List;

import com.groupsavings.model.dto.*;

public interface MemberService {

	MemberResponseDto createMember(MemberRequestDto member);

	MemberResponseDto findMemberByMemberCode(String code);

	List<MemberResponseDto> searchBorrower(MemberNameRequestDto member);

	PageResponse<MemberResponseDto> fetchMembers(String status, String type, int page, int size, String sortBy, String sortDir);

	List<MemberTotalContributionDto> fetchMemberTotalContributions();

	MemberContributions fetchMemberContributions(String memberCode);

	List<MemberSummaryProfit> membersSummaryProfits();

	MemberLoanProfitsDto memberLoanProfits(String memberCode);
}
