package com.groupsavings.test.util;

import com.groupsavings.model.entity.*;
import com.groupsavings.model.enums.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TestEntityFactory {

    public static Member createMember(String code, String firstName, String lastName) {
        Member member = new Member();
        member.setMemberCode(code);
        member.setFirstname(firstName);
        member.setLastname(lastName);
        member.setEmail(code + "@email.com");
        member.setMobileNumber("091712345" + code.substring(code.length() - 2));
        member.setJoinDate(LocalDate.now());
        member.setMemberType(MemberType.CONTRIBUTOR);
        member.setMemberStatus(MemberStatus.ACTIVE);

        Address address = new Address();
        address.setStreet("123 Test St");
        address.setBarangay("Barangay Test");
        address.setCity("Test City");
        address.setProvince("Test Province");
        address.setZipCode("1234");
        member.setAddress(address);

        return member;
    }

    public static SavingsPool createSavingsPool(String code, String name) {
        SavingsPool pool = new SavingsPool();
        pool.setPoolCode(code);
        pool.setPoolName(name);
        pool.setDescription("Test Pool Description for " + name);
        pool.setTotalAmount(new BigDecimal("10000.00"));
        pool.setStartDate(LocalDate.now().plusMonths(6));
        pool.setEndDate(LocalDate.now().plusMonths(6));
        return pool;
    }

    public static Contribution createContribution(String code, BigDecimal amount,
                                                  Member member, SavingsPool pool) {
        Contribution contribution = new Contribution();
        contribution.setContributionCode(code);
        contribution.setMember(member);
        contribution.setPool(pool);
        contribution.setAmount(amount);
        contribution.setContributionDate(LocalDate.now());
        contribution.setPaymentMethod(PaymentMethod.CASH);
        contribution.setReferenceNumber("REF-" + code + "-" + System.currentTimeMillis());
        contribution.setStatus(ContributionStatus.CONFIRMED);
        contribution.setNotes("Test contribution");
        return contribution;
    }

    public static Loan createLoan(String code, Member borrower, BigDecimal amount) {
        Loan loan = new Loan();
        loan.setLoanCode(code);
        loan.setMember(borrower);
        loan.setLoanAmount(amount);
        loan.setInterestRate(5.0f); // 5%
        loan.setTerms(12);
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setDateApplied(LocalDate.now());
        loan.setTotalAmount(amount.multiply(new BigDecimal("1.05"))); // 5% interest
        loan.setDueDate(LocalDate.now().plusMonths(12));
        return loan;
    }

    public static LoanMemberAllocation createLoanAllocation(Member member, Loan loan,
                                                            float percentage, BigDecimal amount) {
        LoanMemberAllocation allocation = new LoanMemberAllocation();
        allocation.setMember(member);
        allocation.setLoan(loan);
        allocation.setContributionPercentage(percentage);
        allocation.setContributionAmount(amount);
        return allocation;
    }
}