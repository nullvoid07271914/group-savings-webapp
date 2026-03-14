package com.groupsavings.service;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.groupsavings.exception.MemberNotFoundException;
import com.groupsavings.exception.PaymentReceiptException;
import com.groupsavings.mapper.ContributionMapper;
import com.groupsavings.model.dto.ContributionRequestDto;
import com.groupsavings.model.dto.ContributionResponseDto;
import com.groupsavings.model.entity.Contribution;
import com.groupsavings.model.entity.Member;
import com.groupsavings.model.entity.SavingsPool;
import com.groupsavings.model.enums.ContributionStatus;
import com.groupsavings.repository.ConfigRepositoty;
import com.groupsavings.repository.ContributionRepository;
import com.groupsavings.repository.MemberRepository;

@Service
public class ContributionServiceImpl implements ContributionService {

	private static final Logger log = LoggerFactory.getLogger(ContributionServiceImpl.class);

	private final ContributionRepository contributionRepository;

	private final MemberRepository memberRepository;

	private final ConfigRepositoty configRepositoty;

	private final ContributionMapper contributionMapper;

	private final FileStorageService fileService;

	public ContributionServiceImpl(ContributionRepository contributionRepository, MemberRepository memberRepository,
			ConfigRepositoty configRepositoty, ContributionMapper contributionMapper, FileStorageService fileService) {
		this.contributionRepository = contributionRepository;
		this.memberRepository = memberRepository;
		this.configRepositoty = configRepositoty;
		this.contributionMapper = contributionMapper;
		this.fileService = fileService;
	}

	@Override
	public ContributionResponseDto create(ContributionRequestDto contributionDto, MultipartFile paymentReceipt) {
		Contribution entity = contributionMapper.toEntity(contributionDto);

		Member member = memberRepository.findByMemberCode(contributionDto.getMemberCode());
		if (Objects.isNull(member)) {
			throw new MemberNotFoundException("Member with code " + contributionDto.getMemberCode() + " not found.");
		}

		try {
			fileService.storePaymentReceipt(paymentReceipt, contributionDto.getMemberCode());
		} catch (IOException e) {
			throw new PaymentReceiptException(e.getMessage());
		}

		SavingsPool savingsPool = configRepositoty.findConfigByIdOne().getSavingsPool();
		entity.setPool(savingsPool);
		entity.setMember(member);
		entity.setStatus(ContributionStatus.CONFIRMED);

		log.info("Persisting contribution: {}", entity);

		Contribution savedEntity = contributionRepository.save(entity);
		ContributionResponseDto responseDto = contributionMapper.toResponseDto(savedEntity);
		return responseDto;
	}

}
