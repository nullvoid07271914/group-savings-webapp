package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.groupsavings.model.enums.LoanStatus;

public class MemberLoanAmortizationDto {

	private String memberCode;

	private String firstname;

	private String lastname;

	private String loanCode;

	private BigDecimal loanAmount;

	private float interestRate;

	private LoanStatus loanStatus;

	private LocalDate dateApplied;

	private LocalDate dateApproved;

	private LocalDate dateReleased;

	private LocalDate dateFullyPaid;

	private BigDecimal totalAmount;

	private AmortizationDto amortizations;

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

	public LocalDate getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(LocalDate dateApplied) {
		this.dateApplied = dateApplied;
	}

	public LocalDate getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(LocalDate dateApproved) {
		this.dateApproved = dateApproved;
	}

	public LocalDate getDateReleased() {
		return dateReleased;
	}

	public void setDateReleased(LocalDate dateReleased) {
		this.dateReleased = dateReleased;
	}

	public LocalDate getDateFullyPaid() {
		return dateFullyPaid;
	}

	public void setDateFullyPaid(LocalDate dateFullyPaid) {
		this.dateFullyPaid = dateFullyPaid;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public AmortizationDto getAmortizations() {
		return amortizations;
	}

	public void setAmortizations(AmortizationDto amortizations) {
		this.amortizations = amortizations;
	}

}
