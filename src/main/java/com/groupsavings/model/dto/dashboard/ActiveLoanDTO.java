package com.groupsavings.model.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveLoanDTO {
	private String loanCode;
	private String memberCode;
	private String borrowerName;
	private double loanAmount;
	private int terms;
	private double amortization;
	private int paidTerms;
	private String status;
	private int progress;
}
