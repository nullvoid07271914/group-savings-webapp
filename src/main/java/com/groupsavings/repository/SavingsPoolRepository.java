package com.groupsavings.repository;

import com.groupsavings.model.entity.SavingsPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsPoolRepository extends JpaRepository<SavingsPool, Long> {

    Optional<SavingsPool> findByPoolCode(String poolCode);

    Optional<SavingsPool> findByPoolName(String poolName);

    List<SavingsPool> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<SavingsPool> findByEndDateBefore(LocalDate date);

    List<SavingsPool> findByEndDateAfter(LocalDate date);

    @Query("SELECT sp FROM SavingsPool sp WHERE sp.startDate <= :currentDate AND sp.endDate >= :currentDate")
    List<SavingsPool> findActivePools(@Param("currentDate") LocalDate currentDate);

    boolean existsByPoolCode(String poolCode);

    boolean existsByPoolName(String poolName);
}