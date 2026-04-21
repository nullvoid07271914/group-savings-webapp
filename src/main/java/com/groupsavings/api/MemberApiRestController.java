package com.groupsavings.api;

import java.util.List;

import com.groupsavings.model.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupsavings.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiRestController {

	private final MemberService memberService;

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

	@GetMapping("/list")
	public ResponseEntity<PageResponse<MemberResponseDto>> members(
			@RequestParam String status,
			@RequestParam String type,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "memberId") String sortBy,
			@RequestParam(defaultValue = "ASC") String sortDir) {
		PageResponse<MemberResponseDto> memberPage = memberService.fetchMembers(status, type, page, size, sortBy, sortDir);
		return new ResponseEntity<>(memberPage, HttpStatus.OK);
	}

	@GetMapping("/info/{code}")
	public ResponseEntity<MemberResponseDto> memberInfo(@PathVariable String code) {
		MemberResponseDto member = memberService.findMemberByMemberCode(code);
		return new ResponseEntity<>(member, HttpStatus.CREATED);
	}

	@GetMapping("/contributions")
	public ResponseEntity<List<MemberTotalContributionDto>> memberContributions() {
		List<MemberTotalContributionDto> memberTotalContributions = memberService.fetchMemberTotalContributions();
		return new ResponseEntity<>(memberTotalContributions, HttpStatus.CREATED);
	}

	@GetMapping("/contributions/{memberCode}")
	public ResponseEntity<MemberContributions> memberContributions(@PathVariable String memberCode) {
		MemberContributions memberContributions = memberService.fetchMemberContributions(memberCode);
		return new ResponseEntity<>(memberContributions, HttpStatus.CREATED);
	}

	@GetMapping("/profits")
	public ResponseEntity<List<MemberSummaryProfit>> profits() {
		List<MemberSummaryProfit> summaryProfits = memberService.membersSummaryProfits();
		return new ResponseEntity<>(summaryProfits, HttpStatus.CREATED);
	}

	@GetMapping("/profits/{memberCode}")
	public ResponseEntity<MemberLoanProfitsDto> memberProfits(@PathVariable String memberCode) {
		MemberLoanProfitsDto summaryProfits = memberService.memberLoanProfits(memberCode);
		return new ResponseEntity<>(summaryProfits, HttpStatus.CREATED);
	}
}
