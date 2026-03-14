package com.groupsavings.service;

import java.util.List;

import com.groupsavings.model.dto.MemberNameRequestDto;
import com.groupsavings.model.dto.MemberRequestDto;
import com.groupsavings.model.dto.MemberResponseDto;

public interface MemberService {

	MemberResponseDto createMember(MemberRequestDto member);

	MemberResponseDto findMemberByMemberCode(String code);

	List<MemberResponseDto> searchBorrower(MemberNameRequestDto member);
}
