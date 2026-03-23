package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AmortizationDto {

	private int term;

	private BigDecimal amortization;

	private LocalDate amoutDueDate;

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public BigDecimal getAmortization() {
		return amortization;
	}

	public void setAmortization(BigDecimal amortization) {
		this.amortization = amortization;
	}

	public LocalDate getAmoutDueDate() {
		return amoutDueDate;
	}

	public void setAmoutDueDate(LocalDate amoutDueDate) {
		this.amoutDueDate = amoutDueDate;
	}

}
