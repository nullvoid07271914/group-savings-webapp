package com.groupsavings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupsavings.model.entity.LoanMemberAllocation;

@Repository
public interface LoanMemberAllocationRepository extends JpaRepository<LoanMemberAllocation, Long> {

}
