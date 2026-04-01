package com.groupsavings.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BorrowerLoanPaymentsDto {
    
    private Integer payInTerm;

    private BigDecimal amountPaid;

    private BigDecimal loanProfitAmount;

    private LocalDate paymentDate;

    public Integer getPayInTerm() {
        return payInTerm;
    }

    public void setPayInTerm(Integer payInTerm) {
        this.payInTerm = payInTerm;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getLoanProfitAmount() {
        return loanProfitAmount;
    }

    public void setLoanProfitAmount(BigDecimal loanProfitAmount) {
        this.loanProfitAmount = loanProfitAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
