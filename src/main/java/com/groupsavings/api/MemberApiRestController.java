package com.groupsavings.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupsavings.model.dto.MemberNameRequestDto;
import com.groupsavings.model.dto.MemberRequestDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/member")
public class MemberApiRestController {

	private final MemberService memberService;

	public MemberApiRestController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/register")
	public ResponseEntity<MemberResponseDto> createMember(@Valid @RequestBody MemberRequestDto request) {
		MemberResponseDto createdMember = memberService.createMember(request);
		return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
	}

	@PostMapping("/info")
	public ResponseEntity<List<MemberResponseDto>> memberInfo(@Valid @RequestBody MemberNameRequestDto request) {
		List<MemberResponseDto> memberList = memberService.searchBorrower(request);
		return new ResponseEntity<>(memberList, HttpStatus.CREATED);
	}

	@GetMapping("/info/{code}")
	public ResponseEntity<MemberResponseDto> memberInfo(@PathVariable String code) {
		MemberResponseDto member = memberService.findMemberByMemberCode(code);
		return new ResponseEntity<>(member, HttpStatus.CREATED);
	}
}
