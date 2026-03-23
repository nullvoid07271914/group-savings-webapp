package com.groupsavings.service;

import com.groupsavings.model.dto.LoanPaymentDto;

public interface PaymentService {

	public LoanPaymentDto loanPaymentByTerm(LoanPaymentDto loanPaymentDto);
}
