package com.groupsavings.model.dto;

import java.math.BigDecimal;

public class AmountPerMemberDto {

	private String memberCode;

	private BigDecimal amount;

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "AmountPerMemberDto [memberCode=" + memberCode + ", amount=" + amount + "]";
	}

}
