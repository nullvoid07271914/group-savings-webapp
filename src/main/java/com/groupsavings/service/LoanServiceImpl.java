package com.groupsavings.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.groupsavings.exception.*;
import com.groupsavings.repository.LoanMemberAllocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupsavings.mapper.custom.AmountInPoolPerMemberMapper;
import com.groupsavings.mapper.custom.MemberLoansMapper;
import com.groupsavings.constants.LoanConstants;
import com.groupsavings.mapper.LoanMapper;
import com.groupsavings.model.dto.AmountPerMemberDto;
import com.groupsavings.model.dto.LoanRequestDto;
import com.groupsavings.model.dto.LoanResponseDto;
import com.groupsavings.model.dto.LoanStatusRequestDto;
import com.groupsavings.model.dto.MemberLoanAmortizationDto;
import com.groupsavings.model.dto.MemberLoanRequestDto;
import com.groupsavings.model.dto.MemberLoansDto;
import com.groupsavings.model.entity.Loan;
import com.groupsavings.model.entity.LoanMemberAllocation;
import com.groupsavings.model.entity.Member;
import com.groupsavings.model.entity.SavingsPool;
import com.groupsavings.model.enums.LoanStatus;
import com.groupsavings.model.enums.MemberStatus;
import com.groupsavings.model.enums.MemberType;
import com.groupsavings.repository.ConfigRepositoty;
import com.groupsavings.repository.LoanRepository;
import com.groupsavings.repository.MemberRepository;
import com.groupsavings.utils.LoanUtils;
import com.groupsavings.utils.TermDueDateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService, LoanConstants {

	private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

	private static final BigDecimal LOAN_AMOUNT_BELOW_3K = BigDecimal.valueOf(3000.00);

	private static final BigDecimal LOAN_AMOUNT_BELOW_5K = BigDecimal.valueOf(5000.00);

	private final MemberRepository memberRepository;

	private final LoanRepository loanRepository;

	private final LoanMemberAllocationRepository loanMemberAllocationRepository;

	private final ConfigRepositoty configRepositoty;

	private final AmountInPoolPerMemberMapper amountInPoolPerMemberMapper;

	private final MemberLoansMapper memberLoansMapper;

	private final LoanMapper loanMapper;

	@Transactional
	@Override
	public LoanResponseDto applyLoan(LoanRequestDto request) {
		log.info("LoanRequestDto: {}", request);

		String code = request.getMemberCode();
		Member borrower = memberRepository.findByMemberCode(code);

		if (Objects.nonNull(borrower)) {
			Loan loan = new Loan();

			List<Loan> activeLoans = loanRepository.findByBorrowerIdAndLoanStatus(borrower, LoanStatus.ACTIVE);
			if (Objects.nonNull(activeLoans) && !activeLoans.isEmpty()) {
				Member fetchMember = activeLoans.getFirst().getMember();
				Optional<Member> member = memberRepository.findById(fetchMember.getMemberId());
				if (member.isPresent() && MemberType.BORROWER.equals(member.get().getMemberType())) {
					throw new BorrowerLoanExistException(
							"Borrower " + borrower.getFirstname() + " has existing active loan.");
				}
			}

			SavingsPool savingsPool = configRepositoty.findConfigByIdOne().getSavingsPool();

			if (borrower.getMemberStatus().equals(MemberStatus.ACTIVE)) {
				if (borrower.getMemberType().equals(MemberType.CONTRIBUTOR)) {
					loan.setInterestRate(LoanUtils.toDecimal(INTEREST_RATE_FOR_MEMBER).floatValue());
				} else if (borrower.getMemberType().equals(MemberType.BORROWER)) {
					if (request.getLoanAmount().compareTo(LOAN_AMOUNT_BELOW_5K) > 0) {
						throw new LoanApplicationViolationException("Borrower which is not CONTRIBUTOR - can loan only up to Php 5,000.00.");
					}

					if (!(request.getLoanAmount().compareTo(LOAN_AMOUNT_BELOW_5K) <= 0 && request.getTerms() <= 6)) {
						throw new LoanApplicationViolationException("Borrower which is not CONTRIBUTOR - Php 5,000.00 must be paid up to 6 terms only.");
					}

					if (!(request.getLoanAmount().compareTo(LOAN_AMOUNT_BELOW_3K) <= 0 && request.getTerms() <= 4)) {
						throw new LoanApplicationViolationException("Borrower which is not CONTRIBUTOR - Php 3,000.00 must be paid up to 4 terms only.");
					}

					loan.setInterestRate(LoanUtils.toDecimal(INTEREST_RATE_FOR_BORROWER).floatValue());
				}
			} else {
				throw new MemberInActiveStatusException("Member " + borrower.getMemberCode() + " was found InActive or Terminated status.");
			}

			LocalDate dateApplied = request.getDateApplied();
			BigDecimal loanAmount = request.getLoanAmount();

			BigDecimal interestRate = BigDecimal.valueOf(loan.getInterestRate());
			BigDecimal totalAmount = loanAmount.multiply(interestRate).add(loanAmount);
			BigDecimal terms = BigDecimal.valueOf(request.getTerms());
			BigDecimal amortization = totalAmount.divide(terms, 2, RoundingMode.DOWN);

			LocalDate dueDate = TermDueDateUtils.calculateDueDate(dateApplied, request.getTerms());

			loan.setMember(borrower);
			loan.setLoanCode(request.getLoanCode());
			loan.setSavingsPool(savingsPool);

			loan.setLoanAmount(loanAmount);
			loan.setTerms(request.getTerms());
			loan.setDateApplied(dateApplied);
			loan.setTotalAmount(totalAmount.setScale(2, RoundingMode.DOWN));
			loan.setLoanStatus(LoanStatus.PENDING);
			loan.setDueDate(dueDate);
			loan.setAmortization(amortization.setScale(2, RoundingMode.DOWN));

			Loan savedLoan = loanRepository.save(loan);
            return loanMapper.toDto(savedLoan);

        } else {
			throw new BorrowerNotFoundException("Required borrower to apply loan.");
		}
	}

	@Override
	public List<LoanResponseDto> loanByStatus(LoanStatus status) {
		List<Loan> pendingsLoans = loanRepository.findByLoanStatus(status);
		List<LoanResponseDto> loansDto = pendingsLoans.stream().map(loanMapper::toDto).toList();
		log.info("loansDto: {}", loansDto);
		return loansDto;
	}

	@Override
	public LoanResponseDto loanStatusProcess(LoanStatusRequestDto request) {
		log.info("LoanStatusRequestDto: {}", request);
		Optional<Loan> loan = loanRepository.findByLoanCode(request.getLoanCode());
		if (loan.isPresent()) {
			Loan currentLoan = loan.get();

			LoanStatus status = LoanStatus.valueOf(request.getActionStatus());
			currentLoan.setLoanStatus(status);

			switch (status) {
				case LoanStatus.APPROVED -> {
					currentLoan.setDateApproved(LocalDate.now());
					List<LoanMemberAllocation> contributors = approveLoanAndGenerateContributors(currentLoan);
					log.info("contributors: {}", contributors);
					loanMemberAllocationRepository.saveAll(contributors);
				}
				case LoanStatus.ACTIVE -> currentLoan.setDateReleased(LocalDate.now());
				case LoanStatus.FULLY_PAID -> currentLoan.setDateFullyPaid(LocalDate.now());
				default -> {
				}
			}

			log.info("currentLoan: {}", currentLoan);
			loanRepository.save(currentLoan); // update status
			Optional<Loan> fetchedLoan = loanRepository.findByLoanCode(request.getLoanCode()); // fetch updated loan with contributors
			if (fetchedLoan.isPresent()) {
				Loan savedLoan = fetchedLoan.get();
				return loanMapper.toDto(savedLoan);
			}
		}

		return new LoanResponseDto();
	}

	@Override
	public List<MemberLoansDto> memberLoans(MemberLoanRequestDto request) {
		List<Object[]> fetchedMemberLoans = loanRepository.fetchMemberLoans(request.getName(), request.getStatus());
		log.info("fetchedMemberLoans: {}", fetchedMemberLoans);
		List<MemberLoansDto> memberLoans = memberLoansMapper.toDtoList(fetchedMemberLoans);
		log.info("memberLoans: {}", memberLoans);
		return memberLoans;
	}

	@Override
	public MemberLoanAmortizationDto memberLoanAmortizations(String memberCode, String loanCode) {
		return null;
	}

	private List<LoanMemberAllocation> approveLoanAndGenerateContributors(Loan currentLoan) {
		SavingsPool savingsPool = configRepositoty.findConfigByIdOne().getSavingsPool();
		List<AmountPerMemberDto> memberAmountPoolList = availableAmountInPoolPerMember(savingsPool.getPoolId(), currentLoan.getDateApplied());

		List<AmountPerMemberDto> contributorList = filterContributorsWithNonZeroAmount(memberAmountPoolList);
		log.info("contributorList: {}", contributorList);

		BigDecimal totalAmountInPool = contributorList.stream().map(AmountPerMemberDto::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		log.info("totalAmountInPool: {}", totalAmountInPool);

		BigDecimal loanAmount = currentLoan.getLoanAmount();
		if (totalAmountInPool.compareTo(loanAmount) >= 0) {
            return fetchLoanContributors(contributorList, currentLoan, totalAmountInPool, loanAmount);
		} else {
			throw new RequestInsufficientException("Insufficient Amount in pool for loan Php " + loanAmount);
		}
	}

	private List<AmountPerMemberDto> filterContributorsWithNonZeroAmount(
			List<AmountPerMemberDto> memberAmountPoolList) {
		return memberAmountPoolList.stream().filter(Objects::nonNull)
				.filter(dto -> Objects.nonNull(dto.getAmount()) && dto.getAmount().compareTo(BigDecimal.ZERO) > 0)
				.toList();
	}

	private List<LoanMemberAllocation> fetchLoanContributors(List<AmountPerMemberDto> memberAmountPoolList, Loan loan,
	                                                         BigDecimal totalAmountInPool, BigDecimal loanAmount) {
		List<LoanMemberAllocation> loanContributors = new ArrayList<LoanMemberAllocation>();
		BigDecimal baseAmount = totalAmountInPool.subtract(loanAmount);

		for (AmountPerMemberDto item : memberAmountPoolList) {
			BigDecimal memberAmount = item.getAmount();
			BigDecimal percentage = memberAmount.divide(totalAmountInPool, 5, RoundingMode.DOWN);
			BigDecimal lessAmount = percentage.multiply(baseAmount);
			BigDecimal contribution = memberAmount.subtract(lessAmount);

			LoanMemberAllocation contributorDto = new LoanMemberAllocation();
			Member member = memberRepository.findByMemberCode(item.getMemberCode());
			contributorDto.setMember(member);
			contributorDto.setContributionPercentage(percentage.floatValue());
			contributorDto.setContributionAmount(contribution.setScale(2, RoundingMode.DOWN));
			contributorDto.setLoan(loan);

			loanContributors.add(contributorDto);
		}

		return loanContributors;
	}

	private List<AmountPerMemberDto> availableAmountInPoolPerMember(Long poolId, LocalDate dateApplied) {
		List<Object[]> poolAmountPerMember = loanRepository.fetchMembersPoolAmountForLoan(poolId, dateApplied);
		log.info("poolAmountPerMember: {}", poolAmountPerMember);
		List<AmountPerMemberDto> perMemberList = amountInPoolPerMemberMapper.toDtoList(poolAmountPerMember);
		log.info("perMemberList: {}", perMemberList);
		return perMemberList;
	}

	private Map<Integer, LocalDate> generateSchedule(int term, LocalDate dueDate) {
		Map<Integer, LocalDate> result = new HashMap<>();
		LocalDate current = dueDate;

		for (int i = term; i >= 1; i--) {
			result.put(i, current);
			if (i > 1) {
				current = getPreviousCutoff(current);
			}
		}

		return result;
	}

	private LocalDate getPreviousCutoff(LocalDate date) {
		if (date.getDayOfMonth() == 15) {
			LocalDate prevMonth = date.minusMonths(1);
			return prevMonth.withDayOfMonth(YearMonth.from(prevMonth).lengthOfMonth());
		} else {
			return date.withDayOfMonth(15);
		}
	}

}
