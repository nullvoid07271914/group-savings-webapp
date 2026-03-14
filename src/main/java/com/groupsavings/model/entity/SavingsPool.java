package com.groupsavings.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "savings_pool_tbl")
public class SavingsPool {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pool_id")
	private Long poolId;

	@Column(name = "pool_code", unique = true, nullable = false)
	private String poolCode;

	@OneToMany(mappedBy = "savingsPool")
	private List<Loan> loans;

	@Column(name = "pool_name", nullable = false)
	private String poolName;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "total_amount", precision = 15, scale = 2)
	private BigDecimal totalAmount = BigDecimal.ZERO;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	public Long getPoolId() {
		return poolId;
	}

	public void setPoolId(Long poolId) {
		this.poolId = poolId;
	}

	public String getPoolCode() {
		return poolCode;
	}

	public void setPoolCode(String poolCode) {
		this.poolCode = poolCode;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SavingsPool that))
			return false;
		return Objects.equals(poolId, that.poolId) && Objects.equals(poolCode, that.poolCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(poolId, poolCode);
	}
}