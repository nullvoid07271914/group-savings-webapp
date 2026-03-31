package com.groupsavings.model.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyPerformanceDTO {
	private String month;
	private double collections;
	private double disbursements;
	private double profit;
}
