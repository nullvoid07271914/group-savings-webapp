package com.groupsavings.model.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDTO {
	private SummaryDTO summary;
	private MembersDTO members;
	private ContributionsDTO contributions;
	private LoansDTO loans;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SummaryDTO {
		private int totalMembers;
		private double totalContributions;
		private double totalProfit;
		private double loanableAmount;
		private int memberGrowth;
		private double contributionGrowth;
		private double profitGrowth;
		private double loanGrowth;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MembersDTO {
		private int total;
		private int active;
		private int inactive;
		private int newThisMonth;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ContributionsDTO {
		private double totalAmount;
		private double thisMonth;
		private double lastMonth;
		private double averagePerMember;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LoansDTO {
		private double active;
		private double fullyPaid;
		private double pending;
		private int totalActiveCount;
		private int totalFullyPaidCount;
		private int totalPendingCount;
	}
}
