package com.groupsavings.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.groupsavings.model.dto.ContributionRequestDto;
import com.groupsavings.model.dto.ContributionResponseDto;
import com.groupsavings.service.ContributionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/contribution")
public class ContributionApiRestController {

	private static final Logger log = LoggerFactory.getLogger(ContributionApiRestController.class);

	private final ContributionService contributionService;

	public ContributionApiRestController(ContributionService contributionService) {
		this.contributionService = contributionService;
	}

	@PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ContributionResponseDto> createContribution(
			@RequestPart("contributionDto") @Valid ContributionRequestDto contributionDto,
			@RequestPart(value = "paymentReceipt", required = false) MultipartFile paymentReceipt) {
		log.info("ContributionRequestDto: {}", contributionDto);
		ContributionResponseDto resposeDto = contributionService.create(contributionDto, paymentReceipt);
		return new ResponseEntity<>(resposeDto, HttpStatus.CREATED);
	}
}
