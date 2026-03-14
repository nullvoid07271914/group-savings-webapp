package com.groupsavings.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.groupsavings.model.enums.MemberStatus;
import com.groupsavings.model.enums.MemberType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "member_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "uk_member_code", columnNames = "member_code"),
		@UniqueConstraint(name = "uk_member_code_email_mobile", columnNames = { "member_code", "email",
				"mobile_number" }) })
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "member_code", unique = true, nullable = false)
	private String memberCode;

	@Column(name = "firstname", nullable = false)
	private String firstname;

	@Column(name = "lastname", nullable = false)
	private String lastname;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Embedded
	private Address address;

	@Column(name = "join_date", nullable = false)
	private LocalDate joinDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "member_type", nullable = false)
	private MemberType memberType;

	@Enumerated(EnumType.STRING)
	@Column(name = "member_status", nullable = false)
	private MemberStatus memberStatus;

	@OneToMany(mappedBy = "member")
	private List<Contribution> contributions;

	@OneToMany(mappedBy = "member")
	private List<Loan> loans;

	@OneToMany(mappedBy = "member")
	private List<LoanMemberAllocation> loanAllocations;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "updated_date", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedDate;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public MemberType getMemberType() {
		return memberType;
	}

	public void setMemberType(MemberType memberType) {
		this.memberType = memberType;
	}

	public MemberStatus getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(MemberStatus memberStatus) {
		this.memberStatus = memberStatus;
	}

	public List<Contribution> getContributions() {
		return contributions;
	}

	public void setContributions(List<Contribution> contributions) {
		this.contributions = contributions;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public List<LoanMemberAllocation> getLoanAllocations() {
		return loanAllocations;
	}

	public void setLoanAllocations(List<LoanMemberAllocation> loanAllocations) {
		this.loanAllocations = loanAllocations;
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
		if (!(o instanceof Member member))
			return false;
		return Objects.equals(memberId, member.memberId) && Objects.equals(memberCode, member.memberCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(memberId, memberCode);
	}
}