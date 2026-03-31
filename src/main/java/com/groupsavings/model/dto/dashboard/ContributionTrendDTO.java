package com.groupsavings.model.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributionTrendDTO {
	private String month;
	private int year;
	private double amount;
}