package com.groupsavings.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groupsavings.model.dto.dashboard.ActiveLoanDTO;
import com.groupsavings.model.dto.dashboard.ContributionTrendDTO;
import com.groupsavings.model.dto.dashboard.ContributionTypeDTO;
import com.groupsavings.model.dto.dashboard.DashboardSummaryDTO;
import com.groupsavings.model.dto.dashboard.LoanDistributionDTO;
import com.groupsavings.model.dto.dashboard.MonthlyPerformanceDTO;
import com.groupsavings.model.dto.dashboard.ProfitGrowthDTO;
import com.groupsavings.model.dto.dashboard.RecentActivityDTO;
import com.groupsavings.model.dto.dashboard.TopContributorDTO;

@Service
public class DashboardService {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	public DashboardSummaryDTO getDashboardSummary() {
		// Summary
		DashboardSummaryDTO.SummaryDTO summary = new DashboardSummaryDTO.SummaryDTO(156, // totalMembers
				1284500.00, // totalContributions
				234800.00, // totalProfit
				3200.00, // loanableAmount
				12, // memberGrowth
				8.5, // contributionGrowth
				15.2, // profitGrowth
				5.2 // loanGrowth
		);

		// Members
		DashboardSummaryDTO.MembersDTO members = new DashboardSummaryDTO.MembersDTO(156, // total
				142, // active
				14, // inactive
				12 // newThisMonth
		);

		// Contributions
		DashboardSummaryDTO.ContributionsDTO contributions = new DashboardSummaryDTO.ContributionsDTO(1284500.00, // totalAmount
				268000.00, // thisMonth
				245000.00, // lastMonth
				8234.00 // averagePerMember
		);

		// Loans
		DashboardSummaryDTO.LoansDTO loans = new DashboardSummaryDTO.LoansDTO(892300.00, // active
				456000.00, // fullyPaid
				128000.00, // pending
				12, // totalActiveCount
				8, // totalFullyPaidCount
				3 // totalPendingCount
		);

		return new DashboardSummaryDTO(summary, members, contributions, loans);
	}

	public List<ContributionTrendDTO> getContributionTrends() {
		return Arrays.asList(new ContributionTrendDTO("Jan", 2026, 85000.00),
				new ContributionTrendDTO("Feb", 2026, 92000.00), new ContributionTrendDTO("Mar", 2026, 108000.00),
				new ContributionTrendDTO("Apr", 2026, 115000.00), new ContributionTrendDTO("May", 2026, 142000.00),
				new ContributionTrendDTO("Jun", 2026, 158000.00), new ContributionTrendDTO("Jul", 2026, 175000.00),
				new ContributionTrendDTO("Aug", 2026, 192000.00), new ContributionTrendDTO("Sep", 2026, 210000.00),
				new ContributionTrendDTO("Oct", 2026, 228000.00), new ContributionTrendDTO("Nov", 2026, 245000.00),
				new ContributionTrendDTO("Dec", 2026, 268000.00));
	}

	public List<LoanDistributionDTO> getLoanDistribution() {
		return Arrays.asList(new LoanDistributionDTO("ACTIVE", 892300.00, 12),
				new LoanDistributionDTO("FULLY_PAID", 456000.00, 8), new LoanDistributionDTO("PENDING", 128000.00, 3));
	}

	public List<RecentActivityDTO> getRecentActivities(int limit) {
		List<RecentActivityDTO> activities = Arrays.asList(
				createActivity("act_001", "CONTRIBUTION", "made a contribution",
						new RecentActivityDTO.UserDTO("202691D87C7F", "Joel", "Idul"), 1000.00, null, null, null, null,
						getTimeAgo(5), "COMPLETED"),

				createActivity("act_002", "MEMBER_REGISTRATION", "registered as new member",
						new RecentActivityDTO.UserDTO("20260331A1B2C3", "Maria", "Santos"), null, null, null, null,
						null, getTimeAgo(120), "NEW"),

				createActivity("act_003", "LOAN_APPROVED", "loan approved",
						new RecentActivityDTO.UserDTO("2026D281DE51", "Roselyn", "Bernales"), null, "20260330L0A1B2",
						2700.00, null, null, getTimeAgo(300), "APPROVED"),

				createActivity("act_004", "PAYMENT_RECEIVED", "loan payment received",
						new RecentActivityDTO.UserDTO("2026035B234A", "Mary", "Abalos"), 958.33, null, null, 2, 6,
						getTimeAgo(1440), "RECEIVED"),

				createActivity("act_005", "PROFIT_DISTRIBUTION", "monthly profit distributed",
						new RecentActivityDTO.UserDTO("SYSTEM", "System", ""), 23450.00, null, null, null, null,
						getTimeAgo(2880), "DISTRIBUTED"));

		return activities.stream().limit(limit).toList();
	}

	public List<TopContributorDTO> getTopContributors(int limit) {
		List<TopContributorDTO> contributors = Arrays.asList(
				new TopContributorDTO(1, "202673C88490", "Prince Lowie", "Nalasa", 2500.00, "#f6ad55"),
				new TopContributorDTO(2, "2026D281DE51", "Roselyn", "Bernales", 2000.00, "#48bb78"),
				new TopContributorDTO(3, "2026A9AFA466", "Queeny Lyn", "Nalasa", 1500.00, "#4299e1"),
				new TopContributorDTO(4, "2026AB1DFEE8", "Vina Jane", "Nalasa", 1500.00, "#9f7aea"),
				new TopContributorDTO(5, "20263A774670", "Cresilda", "Pandian", 1500.00, "#ed64a6"),
				new TopContributorDTO(6, "20267783B147", "Lyshiela", "Mosqueda", 900.00, "#48bb78"),
				new TopContributorDTO(7, "20268444B20E", "Sherlyn", "Torralba", 1000.00, "#f6ad55"));

		return contributors.stream().limit(limit).toList();
	}

	public List<ActiveLoanDTO> getActiveLoans(int limit) {
		List<ActiveLoanDTO> loans = Arrays.asList(
				new ActiveLoanDTO("20260327G61G82B1", "2026035B234A", "Mary Abalos", 5000.00, 6, 958.33, 2, "ACTIVE",
						33),
				new ActiveLoanDTO("20260215FRAQASUN", "202691D87C7F", "Joel Idul", 5000.00, 6, 875.00, 1, "ACTIVE", 17),
				new ActiveLoanDTO("20260304X1K6SZXU", "2026D281DE51", "Roselyn Bernales", 2700.00, 4, 708.75, 1,
						"ACTIVE", 25),
				new ActiveLoanDTO("20260314GR0ZAEBS", "2026D281DE51", "Roselyn Bernales", 1300.00, 4, 341.25, 1,
						"ACTIVE", 25),
				new ActiveLoanDTO("20260329A1B2C3D4", "2026E4AF5177", "Richard Garcia", 10000.00, 12, 875.00, 0,
						"ACTIVE", 0));

		return loans.stream().limit(limit).toList();
	}

	public List<MonthlyPerformanceDTO> getMonthlyPerformance() {
		return Arrays.asList(new MonthlyPerformanceDTO("Jan", 142000.00, 85000.00, 18500.00),
				new MonthlyPerformanceDTO("Feb", 158000.00, 92000.00, 21200.00),
				new MonthlyPerformanceDTO("Mar", 175000.00, 108000.00, 24800.00),
				new MonthlyPerformanceDTO("Apr", 192000.00, 115000.00, 28900.00),
				new MonthlyPerformanceDTO("May", 210000.00, 142000.00, 32500.00),
				new MonthlyPerformanceDTO("Jun", 228000.00, 158000.00, 37800.00));
	}

	public List<ContributionTypeDTO> getContributionTypeDistribution() {
		return Arrays.asList(new ContributionTypeDTO("REGULAR_SAVINGS", 684500.00, 40.2),
				new ContributionTypeDTO("LOAN_PAYMENTS", 892300.00, 52.4),
				new ContributionTypeDTO("SPECIAL_CONTRIBUTIONS", 128000.00, 7.4));
	}

	public List<ProfitGrowthDTO> getProfitGrowth() {
		return Arrays.asList(new ProfitGrowthDTO("Jan", 18500.00), new ProfitGrowthDTO("Feb", 21200.00),
				new ProfitGrowthDTO("Mar", 24800.00), new ProfitGrowthDTO("Apr", 28900.00),
				new ProfitGrowthDTO("May", 32500.00), new ProfitGrowthDTO("Jun", 37800.00));
	}

	// Helper methods
	private RecentActivityDTO createActivity(String id, String type, String title, RecentActivityDTO.UserDTO user,
			Double amount, String loanCode, Double loanAmount, Integer term, Integer totalTerms, String timeAgo,
			String status) {

		RecentActivityDTO activity = new RecentActivityDTO();
		activity.setId(id);
		activity.setType(type);
		activity.setTitle(title);
		activity.setUser(user);
		activity.setAmount(amount);
		activity.setLoanCode(loanCode);
		activity.setLoanAmount(loanAmount);
		activity.setTerm(term);
		activity.setTotalTerms(totalTerms);
		activity.setTimestamp(LocalDateTime.now().format(FORMATTER));
		activity.setTimeAgo(timeAgo);
		activity.setStatus(status);
		return activity;
	}

	private String getTimeAgo(int minutes) {
		if (minutes < 60) {
			return minutes + " minutes ago";
		} else if (minutes < 1440) {
			return (minutes / 60) + " hours ago";
		} else {
			return (minutes / 1440) + " days ago";
		}
	}
}