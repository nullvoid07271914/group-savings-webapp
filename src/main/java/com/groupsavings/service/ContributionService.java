package com.groupsavings.service;

import org.springframework.web.multipart.MultipartFile;

import com.groupsavings.model.dto.ContributionRequestDto;
import com.groupsavings.model.dto.ContributionResponseDto;

public interface ContributionService {

	public ContributionResponseDto create(ContributionRequestDto contributionDto, MultipartFile paymentReceipt);
}
