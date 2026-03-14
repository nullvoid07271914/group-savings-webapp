package com.groupsavings.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupsavings.constants.LoanConstants;
import com.groupsavings.exception.DuplicateResourceException;
import com.groupsavings.exception.RequestInsufficientException;
import com.groupsavings.mapper.MemberMapper;
import com.groupsavings.model.dto.MemberNameRequestDto;
import com.groupsavings.model.dto.MemberRequestDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.model.entity.Member;
import com.groupsavings.model.enums.MemberStatus;
import com.groupsavings.model.enums.MemberType;
import com.groupsavings.repository.MemberRepository;
import com.groupsavings.utils.LoanUtils;

@Service
@Transactional
public class MemberServiceImpl implements MemberService, LoanConstants {

	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);

	private final MemberRepository memberRepository;

	private final MemberMapper memberMapper;

	public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper) {
		this.memberRepository = memberRepository;
		this.memberMapper = memberMapper;
	}

	@Override
	public MemberResponseDto createMember(MemberRequestDto member) {
		log.info("Creating new member: {}", member);

		if (memberRepository.isEmailExist(member.getEmail())) {
			throw new DuplicateResourceException("Email " + member.getEmail() + " already exists.");
		}

		if (memberRepository.isPhoneNumberExist(member.getMobileNumber())) {
			throw new DuplicateResourceException("Phone number " + member.getMobileNumber() + " already exists.");
		}

		Member memberEntity = memberMapper.toEntity(member);
		Member savedMember = memberRepository.save(memberEntity);

		return memberMapper.toResponseDto(savedMember);
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

		return borrower;
	}

}
