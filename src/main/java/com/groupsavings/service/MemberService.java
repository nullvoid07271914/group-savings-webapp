package com.groupsavings.service;

import java.util.List;

import com.groupsavings.model.dto.MemberContributions;
import com.groupsavings.model.dto.MemberNameRequestDto;
import com.groupsavings.model.dto.MemberRequestDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.model.dto.MemberTotalContribution;

public interface MemberService {

	MemberResponseDto createMember(MemberRequestDto member);

	MemberResponseDto findMemberByMemberCode(String code);

	List<MemberResponseDto> searchBorrower(MemberNameRequestDto member);

	List<MemberResponseDto> fetchMembers(String status, String type);

	List<MemberTotalContribution> fetchMemberTotalContributions();

	MemberContributions fetchMemberContributions(String memberCode);
}
