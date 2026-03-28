package com.groupsavings.service;

import java.util.List;

import com.groupsavings.model.dto.MemberContributions;
import com.groupsavings.model.dto.MemberNameRequestDto;
import com.groupsavings.model.dto.MemberRequestDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.model.dto.MemberTotalContributionDto;

public interface MemberService {

	MemberResponseDto createMember(MemberRequestDto member);

	MemberResponseDto findMemberByMemberCode(String code);

	List<MemberResponseDto> searchBorrower(MemberNameRequestDto member);

	List<MemberResponseDto> fetchMembers(String status, String type);

	List<MemberTotalContributionDto> fetchMemberTotalContributions();

	MemberContributions fetchMemberContributions(String memberCode);
}
