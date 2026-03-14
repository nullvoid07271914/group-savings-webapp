package com.groupsavings.model.dto;

import com.groupsavings.model.enums.ContributionStatus;

public class ContributionResponseDto {

	private String code;

	private ContributionStatus status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ContributionStatus getStatus() {
		return status;
	}

	public void setStatus(ContributionStatus status) {
		this.status = status;
	}

}
