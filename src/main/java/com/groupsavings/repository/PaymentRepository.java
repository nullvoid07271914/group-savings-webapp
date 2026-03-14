package com.groupsavings.repository;

import com.groupsavings.model.entity.Loan;
import com.groupsavings.model.entity.Payment;
import com.groupsavings.model.enums.PaymentMethod;
import com.groupsavings.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentCode(String paymentCode);

    Optional<Payment> findByReferenceNumber(String referenceNumber);

    List<Payment> findByLoan(Loan loan);

    List<Payment> findByLoanLoanId(Long loanId);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Payment> findByPaymentMethod(PaymentMethod paymentMethod);

    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT p FROM Payment p WHERE p.loan.loanId = :loanId AND p.payInTerm = :term")
    Optional<Payment> findByLoanIdAndTerm(@Param("loanId") Long loanId, @Param("term") int term);

    @Query("SELECT SUM(p.amountPaid) FROM Payment p WHERE p.loan.loanId = :loanId")
    BigDecimal getTotalPaidByLoan(@Param("loanId") Long loanId);

    @Query("SELECT SUM(p.amountPaid) FROM Payment p WHERE p.loan.loanId = :loanId AND p.paymentStatus = :status")
    BigDecimal getTotalPaidByLoanAndStatus(@Param("loanId") Long loanId, @Param("status") PaymentStatus status);

    @Query("SELECT p FROM Payment p WHERE p.loan.member.memberId = :memberId")
    List<Payment> findByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT p.loan, SUM(p.amountPaid) FROM Payment p GROUP BY p.loan")
    List<Object[]> getTotalPaymentsPerLoan();

    @Query("SELECT p FROM Payment p WHERE p.createdDate >= :since")
    List<Payment> findRecentPayments(@Param("since") LocalDateTime since);

    boolean existsByPaymentCode(String paymentCode);

    boolean existsByReferenceNumber(String referenceNumber);
}