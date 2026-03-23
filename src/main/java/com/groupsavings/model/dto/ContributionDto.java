package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContributionDto {

	private String contributionCode;

	private BigDecimal amount;

	private LocalDate contributionDate;

	private String paymentMethod;

	private String referenceNumber;

	private int monthTerm;

	public String getContributionCode() {
		return contributionCode;
	}

	public void setContributionCode(String contributionCode) {
		this.contributionCode = contributionCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getContributionDate() {
		return contributionDate;
	}

	public void setContributionDate(LocalDate contributionDate) {
		this.contributionDate = contributionDate;
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

	public int getMonthTerm() {
		return monthTerm;
	}

	public void setMonthTerm(int monthTerm) {
		this.monthTerm = monthTerm;
	}

}
