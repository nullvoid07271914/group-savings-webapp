package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MemberErningsFromLoansDto {

    private String borrowerCode;

    private String borrowerFirstname;

    private String borrowerLastname;

    private String loanCode;

    private BigDecimal loanAmount;

    private BigDecimal totalAmount;

    private LocalDate dateReleased;

    private BigDecimal contributionAmount;

    private String contributionPercentage;

    private List<BorrowerLoanPaymentsDto> borrowerPayments;

    public String getBorrowerCode() {
        return borrowerCode;
    }

    public void setBorrowerCode(String borrowerCode) {
        this.borrowerCode = borrowerCode;
    }

    public String getBorrowerFirstname() {
        return borrowerFirstname;
    }

    public void setBorrowerFirstname(String borrowerFirstname) {
        this.borrowerFirstname = borrowerFirstname;
    }

    public String getBorrowerLastname() {
        return borrowerLastname;
    }

    public void setBorrowerLastname(String borrowerLastname) {
        this.borrowerLastname = borrowerLastname;
    }

    public String getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDateReleased() {
        return dateReleased;
    }

    public void setDateReleased(LocalDate dateReleased) {
        this.dateReleased = dateReleased;
    }

    public BigDecimal getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(BigDecimal contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public String getContributionPercentage() {
        return contributionPercentage;
    }

    public void setContributionPercentage(String contributionPercentage) {
        this.contributionPercentage = contributionPercentage;
    }

    public List<BorrowerLoanPaymentsDto> getBorrowerPayments() {
        return borrowerPayments;
    }

    public void setBorrowerPayments(List<BorrowerLoanPaymentsDto> borrowerPayments) {
        this.borrowerPayments = borrowerPayments;
    }
}
