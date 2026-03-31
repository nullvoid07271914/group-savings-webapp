package com.groupsavings.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.groupsavings.model.dto.dashboard.ActiveLoanDTO;
import com.groupsavings.model.dto.dashboard.ApiResponse;
import com.groupsavings.model.dto.dashboard.ContributionTrendDTO;
import com.groupsavings.model.dto.dashboard.ContributionTypeDTO;
import com.groupsavings.model.dto.dashboard.DashboardSummaryDTO;
import com.groupsavings.model.dto.dashboard.LoanDistributionDTO;
import com.groupsavings.model.dto.dashboard.MonthlyPerformanceDTO;
import com.groupsavings.model.dto.dashboard.ProfitGrowthDTO;
import com.groupsavings.model.dto.dashboard.RecentActivityDTO;
import com.groupsavings.model.dto.dashboard.TopContributorDTO;
import com.groupsavings.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dashboard")
public class DashboardApiRestController {

	private final DashboardService dashboardService;

	@GetMapping("/summary")
	public ApiResponse<DashboardSummaryDTO> getDashboardSummary() {
		return ApiResponse.success(dashboardService.getDashboardSummary());
	}

	@GetMapping("/contribution-trends")
	public ApiResponse<List<ContributionTrendDTO>> getContributionTrends() {
		return ApiResponse.success(dashboardService.getContributionTrends());
	}

	@GetMapping("/loan-distribution")
	public ApiResponse<List<LoanDistributionDTO>> getLoanDistribution() {
		return ApiResponse.success(dashboardService.getLoanDistribution());
	}

	@GetMapping("/recent-activities")
	public ApiResponse<List<RecentActivityDTO>> getRecentActivities(@RequestParam(defaultValue = "5") int limit) {
		return ApiResponse.success(dashboardService.getRecentActivities(limit));
	}

	@GetMapping("/top-contributors")
	public ApiResponse<List<TopContributorDTO>> getTopContributors(@RequestParam(defaultValue = "5") int limit) {
		return ApiResponse.success(dashboardService.getTopContributors(limit));
	}

	@GetMapping("/active-loans")
	public ApiResponse<List<ActiveLoanDTO>> getActiveLoans(@RequestParam(defaultValue = "5") int limit) {
		return ApiResponse.success(dashboardService.getActiveLoans(limit));
	}

	@GetMapping("/monthly-performance")
	public ApiResponse<List<MonthlyPerformanceDTO>> getMonthlyPerformance() {
		return ApiResponse.success(dashboardService.getMonthlyPerformance());
	}

	@GetMapping("/contribution-type-distribution")
	public ApiResponse<List<ContributionTypeDTO>> getContributionTypeDistribution() {
		return ApiResponse.success(dashboardService.getContributionTypeDistribution());
	}

	@GetMapping("/profit-growth")
	public ApiResponse<List<ProfitGrowthDTO>> getProfitGrowth() {
		return ApiResponse.success(dashboardService.getProfitGrowth());
	}
}
