package com.groupsavings.repository;

import com.groupsavings.model.entity.Contribution;
import com.groupsavings.model.entity.Member;
import com.groupsavings.model.entity.SavingsPool;
import com.groupsavings.model.enums.ContributionStatus;
import com.groupsavings.model.enums.PaymentMethod;
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
public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    Optional<Contribution> findByContributionCode(String contributionCode);

    Optional<Contribution> findByReferenceNumber(String referenceNumber);

    List<Contribution> findByMember(Member member);

    List<Contribution> findByPool(SavingsPool pool);

    List<Contribution> findByMemberAndPool(Member member, SavingsPool pool);

    List<Contribution> findByStatus(ContributionStatus status);

    List<Contribution> findByPaymentMethod(PaymentMethod paymentMethod);

    List<Contribution> findByContributionDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT c FROM Contribution c WHERE c.member.memberId = :memberId AND c.pool.poolId = :poolId")
    List<Contribution> findByMemberIdAndPoolId(@Param("memberId") Long memberId, @Param("poolId") Long poolId);

    @Query("SELECT SUM(c.amount) FROM Contribution c WHERE c.pool.poolId = :poolId")
    BigDecimal getTotalContributionsByPool(@Param("poolId") Long poolId);

    @Query("SELECT SUM(c.amount) FROM Contribution c WHERE c.member.memberId = :memberId")
    BigDecimal getTotalContributionsByMember(@Param("memberId") Long memberId);

    @Query("SELECT SUM(c.amount) FROM Contribution c WHERE c.member.memberId = :memberId AND c.pool.poolId = :poolId")
    BigDecimal getTotalContributionsByMemberAndPool(@Param("memberId") Long memberId, @Param("poolId") Long poolId);

    @Query("SELECT c.member, SUM(c.amount) FROM Contribution c WHERE c.pool.poolId = :poolId GROUP BY c.member")
    List<Object[]> getContributionsPerMemberInPool(@Param("poolId") Long poolId);

    @Query("SELECT c FROM Contribution c WHERE c.createdDate >= :since")
    List<Contribution> findRecentContributions(@Param("since") LocalDateTime since);

    boolean existsByContributionCode(String contributionCode);

    boolean existsByReferenceNumber(String referenceNumber);
}