package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.groupsavings.model.enums.LoanStatus;

public class LoanDto {

	private String loanCode;

	private BigDecimal loanAmount;

	private float interestRate;

	private int terms;

	private LoanStatus loanStatus;

	private LocalDate dateApplied;

	private LocalDate dateApproved;

	private LocalDate dateReleased;

	private LocalDate dateFullyPaid;

	private BigDecimal totalAmount;

	private LocalDate dueDate;

	private BigDecimal amortization;

	private List<PaymentDto> payments;

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

	public int getTerms() {
		return terms;
	}

	public void setTerms(int terms) {
		this.terms = terms;
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

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getAmortization() {
		return amortization;
	}

	public void setAmortization(BigDecimal amortization) {
		this.amortization = amortization;
	}

	public List<PaymentDto> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentDto> payments) {
		this.payments = payments;
	}

}
