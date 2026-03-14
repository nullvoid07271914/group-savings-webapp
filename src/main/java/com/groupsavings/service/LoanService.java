package com.groupsavings.service;

import java.util.List;

import com.groupsavings.model.dto.LoanRequestDto;
import com.groupsavings.model.dto.LoanResponseDto;
import com.groupsavings.model.enums.LoanStatus;

public interface LoanService {

	public LoanResponseDto applyLoan(LoanRequestDto request);

	public List<LoanResponseDto> loanByStatus(LoanStatus status);
}
