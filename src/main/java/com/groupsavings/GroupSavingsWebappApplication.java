package com.groupsavings;

import com.groupsavings.model.entity.*;
import com.groupsavings.model.enums.*;
import com.groupsavings.repository.ContributionRepository;
import com.groupsavings.repository.LoanRepository;
import com.groupsavings.repository.MemberRepository;
import com.groupsavings.repository.PaymentRepository;
import com.groupsavings.repository.SavingsPoolRepository;
import com.groupsavings.utils.ContributionUtils;
import com.groupsavings.utils.LoanUtils;
import com.groupsavings.utils.MemberUtils;
import com.groupsavings.utils.SavingsPoolUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class GroupSavingsWebappApplication {

    private final LoanRepository loanRepository;

    GroupSavingsWebappApplication(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(GroupSavingsWebappApplication.class, args);
	}

//	@Bean
//	@Transactional
//	public CommandLineRunner demo(
//			MemberRepository memberRepository,
//			SavingsPoolRepository savingsPoolRepository,
//			ContributionRepository contributionRepository,
//			LoanRepository loanRepository,
//			PaymentRepository paymentRepository) {
//
//		return args -> {
//			Member m1 = new Member();
//			m1.setMemberCode(MemberUtils.buildMemberCode());
//			m1.setFirstname("Lolita");
//			m1.setLastname("Torralba");
//			m1.setEmail("");
//			m1.setMobileNumber("00000000002");
//			m1.setJoinDate(LocalDate.of(2026, 2, 15));
//			m1.setMemberType(MemberType.CONTRIBUTOR);
//			m1.setMemberStatus(MemberStatus.ACTIVE);
//
//			Address address = new Address();
//			address.setStreet("");
//			address.setBarangay("Tibungco");
//			address.setCity("Davao City");
//			address.setProvince("Davao del Sur");
//			address.setZipCode("8000");
//
//			m1.setAddress(address);
//
//			memberRepository.save(m1);

			
//			Optional<SavingsPool> savings = savingsPoolRepository.findById(1L);
//
//			Optional<Member> mem = memberRepository.findById(9L);
//			if (mem.isPresent()) {
//				Member member = mem.get();
//
//				Contribution contri = new Contribution();
//				contri.setContributionCode(ContributionUtils.buildContributionCode());
//				contri.setPool(savings.get());
//				contri.setMember(member);
//				contri.setMonthTerm(4);
//				contri.setAmount(new BigDecimal("300.00"));
//				contri.setContributionDate(LocalDate.of(2026, 3, 4));
//				contri.setPaymentMethod(PaymentMethod.GCASH);
//				contri.setReferenceNumber("0038 398 920748 - 1");
//				contri.setStatus(ContributionStatus.CONFIRMED);
//
//				contributionRepository.save(contri);
//			}


//			Optional<Member> mem = memberRepository.findById(13L);
//			if (mem.isPresent()) {
//				Member borrower = mem.get();
//
//				Loan loan = new Loan();
//				loan.setLoanCode(LoanUtils.buildLoanCode());
//				loan.setMember(borrower);
//				loan.setLoanAmount(BigDecimal.valueOf(2700.00));
//				loan.setTerms(4);
//				loan.setInterestRate(0.05f);
//				loan.setLoanStatus(LoanStatus.ACTIVE);
//				loan.setDateApplied(LocalDate.of(2026, 3, 4));
//				loan.setDateApproved(LocalDate.of(2026, 3, 4));
//				loan.setDateReleased(LocalDate.of(2026, 3, 4));
//				loan.setDueDate(LocalDate.of(2026, 4, 30));
//				loan.setTotalAmount(BigDecimal.valueOf(2835.00));
//				loan.setSavingsPool(savings.get());
//
//				loanRepository.save(loan);
//
//			}

//		};
//	}
}
