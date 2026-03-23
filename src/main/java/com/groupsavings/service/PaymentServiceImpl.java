package com.groupsavings.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupsavings.exception.PaymentReceiptException;
import com.groupsavings.mapper.LoanMapper;
import com.groupsavings.model.dto.LoanPaymentDto;
import com.groupsavings.model.entity.Loan;
import com.groupsavings.model.entity.Payment;
import com.groupsavings.model.enums.PaymentMethod;
import com.groupsavings.model.enums.PaymentStatus;
import com.groupsavings.repository.LoanRepository;
import com.groupsavings.repository.PaymentRepository;
import com.groupsavings.utils.PaymentUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	private final LoanRepository loanRepository;

	private final LoanMapper loanMapper;

	@Override
	public LoanPaymentDto loanPaymentByTerm(LoanPaymentDto loanPaymentDto) {
		String loanCode = loanPaymentDto.getLoanCode();
		Optional<Loan> loan = loanRepository.findByLoanCode(loanCode);
		if (loan.isPresent()) {
			Loan currentLoan = loan.get();
			Payment payment = new Payment();
			payment.setLoan(currentLoan);
			payment.setPaymentCode(PaymentUtils.buildPaymentCode());
			payment.setPaymentDate(loanPaymentDto.getPaymentDate());
			payment.setAmountPaid(loanPaymentDto.getAmount());
			payment.setTotalBalanced(null);
			payment.setPayInTerm(loanPaymentDto.getPayInTerm());
			payment.setPaymentMethod(PaymentMethod.valueOf(loanPaymentDto.getPaymentMethod()));
			payment.setPaymentStatus(PaymentStatus.PAID);
			payment.setReferenceNumber(loanPaymentDto.getReferenceNumber());
			Payment savedPayment = paymentRepository.save(payment);
			return loanMapper.toLoanPaymentDto(savedPayment);
		}

		throw new PaymentReceiptException("Loan " + loanCode + " not found.");
	}

}
