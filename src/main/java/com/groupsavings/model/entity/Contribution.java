package com.groupsavings.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.groupsavings.model.enums.ContributionStatus;
import com.groupsavings.model.enums.PaymentMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "contribution_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "uk_contribution_reference", columnNames = "reference_number"),
		@UniqueConstraint(name = "uk_member_pool_reference", columnNames = { "member_id", "pool_id",
				"reference_number" }) })
public class Contribution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contribution_id")
	private Long contributionId;

	@Column(name = "contribution_code", unique = true, nullable = false)
	private String contributionCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pool_id", nullable = false)
	private SavingsPool pool;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(name = "amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	@Column(name = "contribution_date", nullable = false)
	private LocalDate contributionDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method", nullable = false)
	private PaymentMethod paymentMethod;

	@Column(name = "reference_number", unique = true, nullable = false)
	private String referenceNumber;

	@Column(name = "month_term", nullable = false)
	private int monthTerm;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ContributionStatus status;

	@Column(name = "notes")
	private String notes;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "updated_date", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	public Long getContributionId() {
		return contributionId;
	}

	public void setContributionId(Long contributionId) {
		this.contributionId = contributionId;
	}

	public String getContributionCode() {
		return contributionCode;
	}

	public void setContributionCode(String contributionCode) {
		this.contributionCode = contributionCode;
	}

	public SavingsPool getPool() {
		return pool;
	}

	public void setPool(SavingsPool pool) {
		this.pool = pool;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getContributionDate() {
		return contributionDate;
	}

	public void setContributionDate(LocalDate contributionDate) {
		this.contributionDate = contributionDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public int getMonthTerm() {
		return monthTerm;
	}

	public void setMonthTerm(int monthTerm) {
		this.monthTerm = monthTerm;
	}

	public ContributionStatus getStatus() {
		return status;
	}

	public void setStatus(ContributionStatus status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Contribution that))
			return false;
		return Objects.equals(contributionId, that.contributionId)
				&& Objects.equals(contributionCode, that.contributionCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(contributionId, contributionCode);
	}
}