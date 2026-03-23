package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanPaymentDto {

	private String loanCode;

	private BigDecimal amount;

	private Integer payInTerm;

	private String paymentMethod;

	private String referenceNumber;

	private LocalDate paymentDate;

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getPayInTerm() {
		return payInTerm;
	}

	public void setPayInTerm(Integer payInTerm) {
		this.payInTerm = payInTerm;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

}