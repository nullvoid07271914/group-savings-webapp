package com.groupsavings.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupsavings.component.MemberContributionMapper;
import com.groupsavings.component.MemberTotalContributionMapper;
import com.groupsavings.constants.LoanConstants;
import com.groupsavings.exception.DuplicateResourceException;
import com.groupsavings.exception.RequestInsufficientException;
import com.groupsavings.mapper.MemberMapper;
import com.groupsavings.model.dto.MemberContributions;
import com.groupsavings.model.dto.MemberNameRequestDto;
import com.groupsavings.model.dto.MemberRequestDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.model.dto.MemberTotalContribution;
import com.groupsavings.model.entity.Member;
import com.groupsavings.model.enums.MemberStatus;
import com.groupsavings.model.enums.MemberType;
import com.groupsavings.repository.MemberRepository;
import com.groupsavings.utils.LoanUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, LoanConstants {

	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

	private final MemberRepository memberRepository;

	private final MemberMapper memberMapper;

	private final MemberTotalContributionMapper memberTotalContributionMapper;

	private final MemberContributionMapper memberContributionMapper;

	@Override
	public MemberResponseDto createMember(MemberRequestDto member) {
		log.info("Creating new member: {}", member);

		if (Objects.nonNull(member.getEmail()) && !member.getEmail().isBlank()
				&& memberRepository.isEmailExist(member.getEmail())) {
			throw new DuplicateResourceException("Email " + member.getEmail() + " already exists.");
		}

		if (Objects.nonNull(member.getMobileNumber()) && !member.getMobileNumber().isBlank()
				&& memberRepository.isPhoneNumberExist(member.getMobileNumber())) {
			throw new DuplicateResourceException("Phone number " + member.getMobileNumber() + " already exists.");
		}

		Member memberEntity = memberMapper.toEntity(member);
		Member savedMember = memberRepository.save(memberEntity);

		MemberResponseDto createdMember = memberMapper.toResponseDto(savedMember);
		log.info("createdMember: {}", createdMember);

		return createdMember;
	}

	@Override
	public List<MemberResponseDto> searchBorrower(MemberNameRequestDto member) {
		log.info("Search borrower: {}", member);

		if (Objects.isNull(member) || StringUtils.isEmpty(member.getName())) {
			throw new RequestInsufficientException("Name is not provided from the request.");
		}

		List<Member> memberList = memberRepository.searchByName(member.getName());
		List<MemberResponseDto> resultMemberList = memberList.stream().map(mem -> memberMapper.toResponseDto(mem))
				.toList();

		log.info("resultMemberList: {}", resultMemberList);
		return resultMemberList;
	}

	@Override
	public MemberResponseDto findMemberByMemberCode(String code) {
		log.info("Member code: {}", code);
		Member member = memberRepository.findByMemberCode(code);

		MemberResponseDto borrower = memberMapper.toResponseDto(member);
		borrower.setLoanCode(LoanUtils.buildLoanCode());

		if (MemberStatus.valueOf(borrower.getStatus()).equals(MemberStatus.ACTIVE)) {
			if (MemberType.valueOf(borrower.getType()).equals(MemberType.CONTRIBUTOR)) {
				borrower.setInterest(INTEREST_RATE_FOR_MEMBER);
			} else if (MemberType.valueOf(borrower.getType()).equals(MemberType.BORROWER)) {
				borrower.setInterest(INTEREST_RATE_FOR_BORROWER);
			}
		}

		log.info("borrower: {}", borrower);
		return borrower;
	}

	@Override
	public List<MemberResponseDto> fetchMembers(String status, String type) {
		List<Member> members = List.of();

		if (Objects.equals("ALL", status) && Objects.equals("ALL", type)) {
			members = memberRepository.findAll();
		} else if (Objects.equals("ALL", status)) {
			members = memberRepository.findByMemberType(MemberType.valueOf(type));
		} else if (Objects.equals("ALL", type)) {
			members = memberRepository.findByMemberStatus(MemberStatus.valueOf(status));
		} else {
			MemberStatus memberStatus = MemberStatus.valueOf(status);
			MemberType memberType = MemberType.valueOf(type.toUpperCase());
			members = memberRepository.findByMemberStatusAndType(memberStatus, memberType);
		}

		List<MemberResponseDto> memberDtos = memberMapper.toResponseDto(members);
		log.info("memberDtos: {}", memberDtos);

		return memberDtos;
	}

	@Override
	public List<MemberTotalContribution> fetchMemberTotalContributions() {
		List<Object[]> listResult = memberRepository.fetchMemberTotalContributions();
		List<MemberTotalContribution> memberTotalContribution = memberTotalContributionMapper
				.toMemberTotalContributionDto(listResult);
		log.info("memberTotalContribution: {}", memberTotalContribution);
		return memberTotalContribution;
	}

	@Override
	public MemberContributions fetchMemberContributions(String memberCode) {
		List<Object[]> listResult = memberRepository.fetchMemberContributions(memberCode);
		MemberContributions memberContributions = memberContributionMapper.toMemberContributionsDto(listResult);
		log.info("memberContributions: {}", memberContributions);
		return memberContributions;
	}

}
