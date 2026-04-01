package com.groupsavings.model.dto;

import java.time.LocalDate;
import java.util.List;

public class MemberLoanProfitsDto {

    private String memberCode;

    private String firstname;

    private String lastname;

    private LocalDate joinedDate;

    private List<MemberErningsFromLoansDto> earningsFromLoans;

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

    public LocalDate getJoinedDate() { return joinedDate; }

    public void setJoinedDate(LocalDate joinedDate) { this.joinedDate = joinedDate; }

    public List<MemberErningsFromLoansDto> getEarningsFromLoans() {
        return earningsFromLoans;
    }

    public void setEarningsFromLoans(List<MemberErningsFromLoansDto> earningsFromLoans) {
        this.earningsFromLoans = earningsFromLoans;
    }
}
