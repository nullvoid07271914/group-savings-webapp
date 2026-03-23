package com.groupsavings.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.groupsavings.model.entity.Loan;
import com.groupsavings.model.entity.Member;
import com.groupsavings.model.enums.LoanStatus;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>, LoanRepositoryCustom {

	Optional<Loan> findByLoanCode(String loanCode);

	@Query("SELECT l FROM Loan l WHERE l.member = :member AND l.loanStatus = :loanStatus")
	List<Loan> findByBorrowerIdAndLoanStatus(@Param("member") Member member,
			@Param("loanStatus") LoanStatus loanStatus);

	List<Loan> findByMember(Member member);

	List<Loan> findByMemberMemberId(Long memberId);

	@Query("SELECT l FROM Loan l WHERE l.loanStatus = :status ORDER BY l.member")
	List<Loan> findByLoanStatus(@Param("status") LoanStatus loanStatus);

	List<Loan> findByDateAppliedBetween(LocalDate startDate, LocalDate endDate);

	List<Loan> findByDueDateBefore(LocalDate date);

	List<Loan> findByDueDateAfter(LocalDate date);

	@Query("SELECT l FROM Loan l WHERE l.loanStatus = :status AND l.dueDate < :currentDate")
	List<Loan> findOverdueLoans(@Param("status") LoanStatus status, @Param("currentDate") LocalDate currentDate);

	@Query("SELECT l FROM Loan l WHERE l.member.memberId = :memberId AND l.loanStatus = :status")
	List<Loan> findByMemberIdAndStatus(@Param("memberId") Long memberId, @Param("status") LoanStatus status);

	@Query("SELECT SUM(l.loanAmount) FROM Loan l WHERE l.loanStatus = :status")
	BigDecimal getTotalLoanAmountByStatus(@Param("status") LoanStatus status);

	boolean existsByLoanCode(String loanCode);
}