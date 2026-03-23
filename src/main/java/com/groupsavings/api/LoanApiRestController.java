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

import com.groupsavings.model.dto.LoanPaymentDto;
import com.groupsavings.model.dto.LoanRequestDto;
import com.groupsavings.model.dto.LoanResponseDto;
import com.groupsavings.model.dto.LoanStatusRequestDto;
import com.groupsavings.model.dto.MemberLoanDto;
import com.groupsavings.model.dto.MemberLoanRequestDto;
import com.groupsavings.model.dto.MemberLoansDto;
import com.groupsavings.model.enums.LoanStatus;
import com.groupsavings.service.LoanService;
import com.groupsavings.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
public class LoanApiRestController {

	private final LoanService loanService;

	private final PaymentService paymentService;

	@PostMapping("/apply")
	public ResponseEntity<LoanResponseDto> apply(@Valid @RequestBody LoanRequestDto request) {
		LoanResponseDto loan = loanService.applyLoan(request);
		return new ResponseEntity<>(loan, HttpStatus.CREATED);
	}

	@GetMapping("/manage/{status}")
	public ResponseEntity<List<LoanResponseDto>> manage(@PathVariable String status) {
		List<LoanResponseDto> loans = loanService.loanByStatus(LoanStatus.valueOf(status));
		return new ResponseEntity<>(loans, HttpStatus.CREATED);
	}

	@PostMapping("/status")
	public ResponseEntity<LoanResponseDto> apply(@Valid @RequestBody LoanStatusRequestDto request) {
		LoanResponseDto loan = loanService.loanStatusProcess(request);
		return new ResponseEntity<>(loan, HttpStatus.CREATED);
	}

	@PostMapping("/manage/member-loans")
	public ResponseEntity<List<MemberLoansDto>> manageLoan(@Valid @RequestBody MemberLoanRequestDto request) {
		List<MemberLoansDto> memberLoans = loanService.memberLoans(request);
		return new ResponseEntity<>(memberLoans, HttpStatus.CREATED);
	}

	@GetMapping("/manage/{memberCode}/{loanCode}")
	public ResponseEntity<MemberLoanDto> manageLoan(@PathVariable String memberCode, @PathVariable String loanCode) {
		MemberLoanDto loan = null;// loanService.memberLoan(memberCode, loanCode);
		return new ResponseEntity<>(loan, HttpStatus.CREATED);
	}

	@PostMapping("/payment")
	public ResponseEntity<LoanPaymentDto> payment(@Valid @RequestBody LoanPaymentDto request) {
		LoanPaymentDto loanPayment = paymentService.loanPaymentByTerm(request);
		return new ResponseEntity<>(loanPayment, HttpStatus.CREATED);
	}
}
