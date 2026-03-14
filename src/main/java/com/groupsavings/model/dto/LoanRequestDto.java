package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanRequestDto {

	private String memberCode;

	private String loanCode;

	private BigDecimal loanAmount;

	private int terms;

	private LocalDate dateApplied;

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public int getTerms() {
		return terms;
	}

	public void setTerms(int terms) {
		this.terms = terms;
	}

	public LocalDate getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(LocalDate dateApplied) {
		this.dateApplied = dateApplied;
	}

	@Override
	public String toString() {
		return "LoanRequestDto [memberCode=" + memberCode + ", loanCode=" + loanCode + ", loanAmount=" + loanAmount
				+ ", terms=" + terms + ", dateApplied=" + dateApplied + "]";
	}

}
