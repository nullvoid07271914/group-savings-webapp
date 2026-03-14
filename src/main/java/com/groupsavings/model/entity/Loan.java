package com.groupsavings.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.groupsavings.model.enums.LoanStatus;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "loan_tbl", uniqueConstraints = { @UniqueConstraint(name = "uk_loan_code", columnNames = "loan_code") })
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "loan_id")
	private Long loanId;

	@Column(name = "loan_code", unique = true, nullable = false)
	private String loanCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "borrower_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pool_id", nullable = false)
	private SavingsPool savingsPool;

	@OneToMany(mappedBy = "loan", cascade = CascadeType.PERSIST)
	private List<LoanMemberAllocation> loanMembersAllocation;

	@OneToMany(mappedBy = "loan")
	private List<Payment> payments;

	@Column(name = "loan_amount", precision = 12, scale = 2, nullable = false)
	private BigDecimal loanAmount;

	@Column(name = "interest_rate", nullable = false)
	private float interestRate;

	@Column(name = "terms", nullable = false)
	private int terms;

	@Enumerated(EnumType.STRING)
	@Column(name = "loan_status", nullable = false)
	private LoanStatus loanStatus;

	@Column(name = "date_applied", nullable = false)
	private LocalDate dateApplied;

	@Column(name = "date_approved")
	private LocalDate dateApproved;

	@Column(name = "date_released")
	private LocalDate dateReleased;

	@Column(name = "date_fully_paid")
	private LocalDate dateFullyPaid;

	@Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
	private BigDecimal totalAmount;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Column(name = "amortization", precision = 12, scale = 2, nullable = false)
	private BigDecimal amortization;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "updated_date", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getLoanCode() {
		return loanCode;
	}

	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public SavingsPool getSavingsPool() {
		return savingsPool;
	}

	public void setSavingsPool(SavingsPool savingsPool) {
		this.savingsPool = savingsPool;
	}

	public List<LoanMemberAllocation> getLoanMembersAllocation() {
		return loanMembersAllocation;
	}

	public void setLoanMembersAllocation(List<LoanMemberAllocation> loanMembersAllocation) {
		this.loanMembersAllocation = loanMembersAllocation;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public int getTerms() {
		return terms;
	}

	public void setTerms(int terms) {
		this.terms = terms;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

	public LocalDate getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(LocalDate dateApplied) {
		this.dateApplied = dateApplied;
	}

	public LocalDate getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(LocalDate dateApproved) {
		this.dateApproved = dateApproved;
	}

	public LocalDate getDateReleased() {
		return dateReleased;
	}

	public void setDateReleased(LocalDate dateReleased) {
		this.dateReleased = dateReleased;
	}

	public LocalDate getDateFullyPaid() {
		return dateFullyPaid;
	}

	public void setDateFullyPaid(LocalDate dateFullyPaid) {
		this.dateFullyPaid = dateFullyPaid;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getAmortization() {
		return amortization;
	}

	public void setAmortization(BigDecimal amortization) {
		this.amortization = amortization;
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
		if (!(o instanceof Loan loan))
			return false;
		return Objects.equals(loanId, loan.loanId) && Objects.equals(loanCode, loan.loanCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(loanId, loanCode);
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", loanCode=" + loanCode + ", member=" + member + ", savingsPool="
				+ savingsPool + ", loanMembersAllocation=" + loanMembersAllocation + ", payments=" + payments
				+ ", loanAmount=" + loanAmount + ", interestRate=" + interestRate + ", terms=" + terms + ", loanStatus="
				+ loanStatus + ", dateApplied=" + dateApplied + ", dateApproved=" + dateApproved + ", dateReleased="
				+ dateReleased + ", dateFullyPaid=" + dateFullyPaid + ", totalAmount=" + totalAmount + ", dueDate="
				+ dueDate + ", amortization=" + amortization + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + "]";
	}

}
