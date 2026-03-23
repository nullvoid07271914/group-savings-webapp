package com.groupsavings.model.dto;

public class MemberLoanDto {

	private String memberCode;

	private String firstname;

	private String lastname;

	LoanDto loan;

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

	public LoanDto getLoan() {
		return loan;
	}

	public void setLoan(LoanDto loan) {
		this.loan = loan;
	}

}
