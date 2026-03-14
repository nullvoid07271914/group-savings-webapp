package com.groupsavings.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupsavings.model.dto.LoanRequestDto;
import com.groupsavings.model.dto.LoanResponseDto;
import com.groupsavings.model.enums.LoanStatus;
import com.groupsavings.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanApiRestController {

	private final LoanService loanService;

	public LoanApiRestController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping("/apply")
	public ResponseEntity<LoanResponseDto> apply(@Valid @RequestBody LoanRequestDto request) {
		LoanResponseDto loan = loanService.applyLoan(request);
		return new ResponseEntity<>(loan, HttpStatus.CREATED);
	}

	@GetMapping("/manage")
	public ResponseEntity<List<LoanResponseDto>> manage() {
		List<LoanResponseDto> loans = loanService.loanByStatus(LoanStatus.PENDING);
		return new ResponseEntity<>(loans, HttpStatus.CREATED);
	}
}
