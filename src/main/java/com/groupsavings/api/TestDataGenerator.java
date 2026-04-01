package com.groupsavings.api;

import com.groupsavings.model.dto.BorrowerLoanPaymentsDto;
import com.groupsavings.model.dto.MemberErningsFromLoansDto;
import com.groupsavings.model.dto.MemberLoanProfitsDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {

    /**
     * Sample data for member "Prince Lowie Nalasa" (202673C88490)
     * He participated in multiple loans as a contributor
     */
    public static MemberLoanProfitsDto generateSampleMemberProfits() {
        MemberLoanProfitsDto dto = new MemberLoanProfitsDto();
        dto.setMemberCode("202673C88490");
        dto.setFirstname("Prince Lowie");
        dto.setLastname("Nalasa");

        List<MemberErningsFromLoansDto> earningsFromLoans = new ArrayList<>();

        // LOAN 1: Mary Abalos's Loan - Prince contributed 10%
        MemberErningsFromLoansDto loan1 = new MemberErningsFromLoansDto();
        loan1.setBorrowerCode("2026035B234A");
        loan1.setBorrowerFirstname("Mary");
        loan1.setBorrowerLastname("Abalos");
        loan1.setLoanCode("20260327G61G82B1");
        loan1.setLoanAmount(new BigDecimal("5000.00"));
        loan1.setTotalAmount(new BigDecimal("5750.00"));
        loan1.setDateReleased(LocalDate.of(2026, 3, 27));
        loan1.setContributionAmount(new BigDecimal("500.00"));      // Prince contributed ₱500
        loan1.setContributionPercentage("10.00");                   // 10% of the loan

        // Payments made by Mary (borrower) - Prince gets his profit share
        List<BorrowerLoanPaymentsDto> payments1 = new ArrayList<>();
        payments1.add(createPayment(1, new BigDecimal("958.33"), new BigDecimal("95.83"), LocalDate.of(2026, 4, 15)));
        payments1.add(createPayment(2, new BigDecimal("958.33"), new BigDecimal("95.83"), LocalDate.of(2026, 4, 30)));
        payments1.add(createPayment(3, new BigDecimal("958.33"), new BigDecimal("95.83"), LocalDate.of(2026, 5, 15)));
        payments1.add(createPayment(4, new BigDecimal("958.33"), new BigDecimal("95.83"), LocalDate.of(2026, 5, 30)));
        payments1.add(createPayment(5, new BigDecimal("958.33"), new BigDecimal("95.83"), LocalDate.of(2026, 6, 15)));
        payments1.add(createPayment(6, new BigDecimal("958.33"), new BigDecimal("95.83"), LocalDate.of(2026, 6, 30)));
        loan1.setBorrowerPayments(payments1);
        earningsFromLoans.add(loan1);

        // LOAN 2: Joel Idul's Loan - Prince contributed 15%
        MemberErningsFromLoansDto loan2 = new MemberErningsFromLoansDto();
        loan2.setBorrowerCode("202691D87C7F");
        loan2.setBorrowerFirstname("Joel");
        loan2.setBorrowerLastname("Idul");
        loan2.setLoanCode("20260215FRAQASUN");
        loan2.setLoanAmount(new BigDecimal("5000.00"));
        loan2.setTotalAmount(new BigDecimal("5250.00"));
        loan2.setDateReleased(LocalDate.of(2026, 3, 2));
        loan2.setContributionAmount(new BigDecimal("750.00"));      // Prince contributed ₱750
        loan2.setContributionPercentage("15.00");                   // 15% of the loan

        // Payments made by Joel (borrower) - Prince gets his profit share
        List<BorrowerLoanPaymentsDto> payments2 = new ArrayList<>();
        payments2.add(createPayment(1, new BigDecimal("875.00"), new BigDecimal("131.25"), LocalDate.of(2026, 3, 15)));
        payments2.add(createPayment(2, new BigDecimal("875.00"), new BigDecimal("131.25"), LocalDate.of(2026, 3, 30)));
        payments2.add(createPayment(3, new BigDecimal("875.00"), new BigDecimal("131.25"), LocalDate.of(2026, 4, 15)));
        payments2.add(createPayment(4, new BigDecimal("875.00"), new BigDecimal("131.25"), LocalDate.of(2026, 4, 30)));
        payments2.add(createPayment(5, new BigDecimal("875.00"), new BigDecimal("131.25"), LocalDate.of(2026, 5, 15)));
        payments2.add(createPayment(6, new BigDecimal("875.00"), new BigDecimal("131.25"), LocalDate.of(2026, 5, 30)));
        loan2.setBorrowerPayments(payments2);
        earningsFromLoans.add(loan2);

        // LOAN 3: Richard Garcia's Loan - Prince contributed 5%
        MemberErningsFromLoansDto loan3 = new MemberErningsFromLoansDto();
        loan3.setBorrowerCode("2026E4AF5177");
        loan3.setBorrowerFirstname("Richard");
        loan3.setBorrowerLastname("Garcia");
        loan3.setLoanCode("20260329A1B2C3D4");
        loan3.setLoanAmount(new BigDecimal("10000.00"));
        loan3.setTotalAmount(new BigDecimal("10500.00"));
        loan3.setDateReleased(LocalDate.of(2026, 3, 29));
        loan3.setContributionAmount(new BigDecimal("500.00"));      // Prince contributed ₱500
        loan3.setContributionPercentage("5.00");                    // 5% of the loan

        // Payments made by Richard (borrower) - Prince gets his profit share
        List<BorrowerLoanPaymentsDto> payments3 = new ArrayList<>();
        payments3.add(createPayment(1, new BigDecimal("875.00"), new BigDecimal("43.75"), LocalDate.of(2026, 4, 15)));
        payments3.add(createPayment(2, new BigDecimal("875.00"), new BigDecimal("43.75"), LocalDate.of(2026, 4, 30)));
        payments3.add(createPayment(3, new BigDecimal("875.00"), new BigDecimal("43.75"), LocalDate.of(2026, 5, 15)));
        loan3.setBorrowerPayments(payments3);
        earningsFromLoans.add(loan3);

        dto.setEarningsFromLoans(earningsFromLoans);

        return dto;
    }

    /**
     * Sample for multiple members (for the main profits list page)
     * Each member's total contribution and profit are summarized
     */
    public static List<MemberProfitSummaryDto> generateMemberProfitSummaries() {
        List<MemberProfitSummaryDto> list = new ArrayList<>();

        // Member 1: Prince Lowie Nalasa - Active contributor with earnings
        MemberProfitSummaryDto member1 = new MemberProfitSummaryDto();
        member1.setMemberCode("202673C88490");
        member1.setFirstname("Prince Lowie");
        member1.setLastname("Nalasa");
        member1.setJoinDate(LocalDate.of(2026, 2, 15));
        member1.setTotalContribution(new BigDecimal("1750.00"));    // ₱500 + ₱750 + ₱500
        member1.setTotalProfit(new BigDecimal("820.86"));           // ₱575 + ₱787.5 + ₱131.25
        member1.setTotalSummary(new BigDecimal("2570.86"));         // Contribution + Profit
        list.add(member1);

        // Member 2: Roselyn Bernales - Contributor to multiple loans
        MemberProfitSummaryDto member2 = new MemberProfitSummaryDto();
        member2.setMemberCode("2026D281DE51");
        member2.setFirstname("Roselyn");
        member2.setLastname("Bernales");
        member2.setJoinDate(LocalDate.of(2026, 2, 17));
        member2.setTotalContribution(new BigDecimal("400.00"));
        member2.setTotalProfit(new BigDecimal("210.00"));
        member2.setTotalSummary(new BigDecimal("610.00"));
        list.add(member2);

        // Member 3: Joel Idul - Contributor (also a borrower)
        MemberProfitSummaryDto member3 = new MemberProfitSummaryDto();
        member3.setMemberCode("202691D87C7F");
        member3.setFirstname("Joel");
        member3.setLastname("Idul");
        member3.setJoinDate(LocalDate.of(2026, 2, 18));
        member3.setTotalContribution(new BigDecimal("0.00"));
        member3.setTotalProfit(new BigDecimal("0.00"));
        member3.setTotalSummary(new BigDecimal("0.00"));
        list.add(member3);

        // Member 4: Mary Abalos - Borrower only
        MemberProfitSummaryDto member4 = new MemberProfitSummaryDto();
        member4.setMemberCode("2026035B234A");
        member4.setFirstname("Mary");
        member4.setLastname("Abalos");
        member4.setJoinDate(LocalDate.of(2026, 3, 15));
        member4.setTotalContribution(new BigDecimal("0.00"));
        member4.setTotalProfit(new BigDecimal("0.00"));
        member4.setTotalSummary(new BigDecimal("0.00"));
        list.add(member4);

        // Member 5: Cresilda Pandian - Active contributor
        MemberProfitSummaryDto member5 = new MemberProfitSummaryDto();
        member5.setMemberCode("20263A774670");
        member5.setFirstname("Cresilda");
        member5.setLastname("Pandian");
        member5.setJoinDate(LocalDate.of(2026, 2, 16));
        member5.setTotalContribution(new BigDecimal("500.00"));
        member5.setTotalProfit(new BigDecimal("150.00"));
        member5.setTotalSummary(new BigDecimal("650.00"));
        list.add(member5);

        // Member 6: Queeny Lyn Nalasa - Active contributor
        MemberProfitSummaryDto member6 = new MemberProfitSummaryDto();
        member6.setMemberCode("2026A9AFA466");
        member6.setFirstname("Queeny Lyn");
        member6.setLastname("Nalasa");
        member6.setJoinDate(LocalDate.of(2026, 2, 16));
        member6.setTotalContribution(new BigDecimal("300.00"));
        member6.setTotalProfit(new BigDecimal("90.00"));
        member6.setTotalSummary(new BigDecimal("390.00"));
        list.add(member6);

        // Member 7: Vina Jane Nalasa - Active contributor
        MemberProfitSummaryDto member7 = new MemberProfitSummaryDto();
        member7.setMemberCode("2026AB1DFEE8");
        member7.setFirstname("Vina Jane");
        member7.setLastname("Nalasa");
        member7.setJoinDate(LocalDate.of(2026, 2, 15));
        member7.setTotalContribution(new BigDecimal("300.00"));
        member7.setTotalProfit(new BigDecimal("90.00"));
        member7.setTotalSummary(new BigDecimal("390.00"));
        list.add(member7);

        // Member 8: Sherlyn Torralba - Active contributor
        MemberProfitSummaryDto member8 = new MemberProfitSummaryDto();
        member8.setMemberCode("20268444B20E");
        member8.setFirstname("Sherlyn");
        member8.setLastname("Torralba");
        member8.setJoinDate(LocalDate.of(2026, 2, 15));
        member8.setTotalContribution(new BigDecimal("200.00"));
        member8.setTotalProfit(new BigDecimal("60.00"));
        member8.setTotalSummary(new BigDecimal("260.00"));
        list.add(member8);

        return list;
    }

    private static BorrowerLoanPaymentsDto createPayment(Integer term, BigDecimal amount, BigDecimal profit, LocalDate date) {
        BorrowerLoanPaymentsDto payment = new BorrowerLoanPaymentsDto();
        payment.setPayInTerm(term);
        payment.setAmountPaid(amount);
        payment.setLoanProfitAmount(profit);
        payment.setPaymentDate(date);
        return payment;
    }

    // ========== DTO FOR MAIN PROFITS LIST (SUMMARY) ==========
    public static class MemberProfitSummaryDto {
        private String memberCode;
        private String firstname;
        private String lastname;
        private LocalDate joinDate;
        private BigDecimal totalContribution;
        private BigDecimal totalProfit;
        private BigDecimal totalSummary;

        // Getters and Setters
        public String getMemberCode() { return memberCode; }
        public void setMemberCode(String memberCode) { this.memberCode = memberCode; }
        public String getFirstname() { return firstname; }
        public void setFirstname(String firstname) { this.firstname = firstname; }
        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }
        public LocalDate getJoinDate() { return joinDate; }
        public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
        public BigDecimal getTotalContribution() { return totalContribution; }
        public void setTotalContribution(BigDecimal totalContribution) { this.totalContribution = totalContribution; }
        public BigDecimal getTotalProfit() { return totalProfit; }
        public void setTotalProfit(BigDecimal totalProfit) { this.totalProfit = totalProfit; }
        public BigDecimal getTotalSummary() { return totalSummary; }
        public void setTotalSummary(BigDecimal totalSummary) { this.totalSummary = totalSummary; }
    }
}
