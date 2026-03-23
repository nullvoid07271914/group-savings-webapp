package com.groupsavings.model.dto;

import java.time.LocalDate;
import java.util.List;

public class MemberContributions {

	private String memberCode;

	private String firstname;

	private String lastname;

	private LocalDate joinedDate;

	private List<ContributionDto> contributions;

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

	public LocalDate getJoinedDate() {
		return joinedDate;
	}

	public void setJoinedDate(LocalDate joinedDate) {
		this.joinedDate = joinedDate;
	}

	public List<ContributionDto> getContributions() {
		return contributions;
	}

	public void setContributions(List<ContributionDto> contributions) {
		this.contributions = contributions;
	}

}
