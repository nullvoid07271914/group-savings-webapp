package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MemberSummaryProfit {

	private String memberCode;

	private String firstname;

	private String lastname;

	private LocalDate joinDate;

	private BigDecimal totalContribution;

	private BigDecimal totalProfit;

	private BigDecimal totalSummary;

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

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public BigDecimal getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(BigDecimal totalContribution) {
		this.totalContribution = totalContribution;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	public BigDecimal getTotalSummary() {
		return totalSummary;
	}

	public void setTotalSummary(BigDecimal totalSummary) {
		this.totalSummary = totalSummary;
	}

}
