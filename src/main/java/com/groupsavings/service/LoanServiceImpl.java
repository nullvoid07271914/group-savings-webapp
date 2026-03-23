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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupsavings.component.AmountInPoolPerMemberMapper;
import com.groupsavings.component.MemberLoansMapper;
import com.groupsavings.constants.LoanConstants;
import com.groupsavings.exception.BorrowerLoanExistException;
import com.groupsavings.exception.BorrowerNotFoundException;
import com.groupsavings.exception.LoanUnSuccessfulTransactionException;
import com.groupsavings.exception.RequestInsufficientException;
import com.groupsavings.mapper.LoanMapper;
import com.groupsavings.model.dto.AmountPerMemberDto;
import com.groupsavings.model.dto.LoanRequestDto;
import com.groupsavings.model.dto.LoanResponseDto;
import com.groupsavings.model.dto.LoanStatusRequestDto;
import com.groupsavings.model.dto.MemberLoanAmortizationDto;
import com.groupsavings.model.dto.MemberLoanDto;
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

	private static final Logger log = LoggerFactory.getLogger(LoanService.class);

	private final MemberRepository memberRepository;

	private final LoanRepository loanRepository;

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
			if (Objects.nonNull(activeLoans) && activeLoans.size() > 0) {
				Member fetchMember = activeLoans.get(0).getMember();
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
					loan.setInterestRate(LoanUtils.toDecimal(INTEREST_RATE_FOR_BORROWER).floatValue());
				}
			}

			LocalDate dateApplied = request.getDateApplied();
			BigDecimal loanAmount = request.getLoanAmount();

			BigDecimal interestRate = BigDecimal.valueOf(loan.getInterestRate());
			BigDecimal totalAmount = loanAmount.multiply(interestRate).add(loanAmount);
			BigDecimal terms = BigDecimal.valueOf(request.getTerms());
			BigDecimal amortization = totalAmount.divide(terms, 2, RoundingMode.FLOOR);

			LocalDate dueDate = TermDueDateUtils.calculateDueDate(dateApplied, request.getTerms());

			loan.setMember(borrower);
			loan.setLoanCode(request.getLoanCode());
			loan.setSavingsPool(savingsPool);

			loan.setLoanAmount(loanAmount);
			loan.setTerms(request.getTerms());
			loan.setDateApplied(dateApplied);
			loan.setTotalAmount(totalAmount.setScale(2, RoundingMode.FLOOR));
			loan.setLoanStatus(LoanStatus.PENDING);
			loan.setDueDate(dueDate);
			loan.setAmortization(amortization.setScale(2, RoundingMode.FLOOR));

			List<AmountPerMemberDto> memberAmountPoolList = availableAmountInPoolPerMember(savingsPool.getPoolId(),
					dateApplied);

			List<AmountPerMemberDto> contributorList = filterContributorsWithNonZeroAmount(memberAmountPoolList);
			log.info("contributorList: {}", contributorList);

			BigDecimal totalAmountInPool = contributorList.stream().map(AmountPerMemberDto::getAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
			log.info("totalAmountInPool: {}", totalAmountInPool);

			if (totalAmountInPool.compareTo(loanAmount) >= 0) {
				List<LoanMemberAllocation> loanContributors = fetchLoanContributors(contributorList, loan,
						totalAmountInPool, loanAmount);

				loan.setLoanMembersAllocation(loanContributors);
				log.info("saveLoanContributors: {}", loanContributors);
				Loan savedLoan = loanRepository.save(loan);

				if (Objects.nonNull(savedLoan)) {
					LoanResponseDto responseDto = loanMapper.toDto(savedLoan);
					return responseDto;
				} else {
					throw new LoanUnSuccessfulTransactionException(
							"Unsuccessfull Loan Transaction Please review Inputs.");
				}

			} else {
				throw new RequestInsufficientException(
						"Insufficient Amount in pool for loan Php " + request.getLoanAmount());
			}

		} else {
			throw new BorrowerNotFoundException("Required borrower to apply loan.");
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
			BigDecimal percentage = memberAmount.divide(totalAmountInPool, 5, RoundingMode.FLOOR);
			BigDecimal lessAmount = percentage.multiply(baseAmount);
			BigDecimal contribution = memberAmount.subtract(lessAmount);

			LoanMemberAllocation contributorDto = new LoanMemberAllocation();
			Member member = memberRepository.findByMemberCode(item.getMemberCode());
			contributorDto.setMember(member);
			contributorDto.setContributionPercentage(percentage.floatValue());
			contributorDto.setContributionAmount(contribution.setScale(2, RoundingMode.FLOOR));
			contributorDto.setLoan(loan);
			loanContributors.add(contributorDto);
		}

		return loanContributors;
	}

	@Override
	public List<LoanResponseDto> loanByStatus(LoanStatus status) {
		List<Loan> pendingsLoans = loanRepository.findByLoanStatus(status);
		List<LoanResponseDto> loansDto = pendingsLoans.stream().map(loanMapper::toDto).toList();
		return loansDto;
	}

	private List<AmountPerMemberDto> availableAmountInPoolPerMember(Long poolId, LocalDate dateApplied) {
		List<Object[]> poolAmountPerMember = loanRepository.fetchMembersPoolAmountForLoan(poolId, dateApplied);
		log.info("poolAmountPerMember: {}", poolAmountPerMember);
		List<AmountPerMemberDto> perMemberList = amountInPoolPerMemberMapper.toDtoList(poolAmountPerMember);
		return perMemberList;
	}

	@Override
	public LoanResponseDto loanStatusProcess(LoanStatusRequestDto request) {
		log.info("LoanStatusRequestDto: {}", request);
		Optional<Loan> loan = loanRepository.findByLoanCode(request.getLoanCode());
		if (loan.isPresent()) {
			Loan currentLoan = loan.get();
			currentLoan.setLoanStatus(LoanStatus.valueOf(request.getActionStatus()));
			currentLoan.setDateApproved(LocalDate.now());
			Loan savedLoan = loanRepository.save(currentLoan);
			return loanMapper.toDto(savedLoan);
		}

		return new LoanResponseDto();
	}

	@Override
	public List<MemberLoansDto> memberLoans(MemberLoanRequestDto request) {
		List<Object[]> fetchedMemberLoans = loanRepository.fetchMemberLoans(request.getName(), request.getStatus());
		log.info("fetchedMemberLoans: {}", fetchedMemberLoans);
		List<MemberLoansDto> memberLoans = memberLoansMapper.toDtoList(fetchedMemberLoans);
		return memberLoans;
	}

	@Override
	public MemberLoanAmortizationDto memberLoanAmortizations(String memberCode, String loanCode) {
//		Object[] fetchMemberLoan = loanRepository.fetchMemberLoan(memberCode, loanCode);
//		log.info("fetchMemberLoan: {}", fetchMemberLoan);
//		MemberLoanDto memberLoan = memberLoansMapper.toSingleDto(fetchMemberLoan);
//
//		if (Objects.isNull(memberLoan)) {
//			// throw exception
//		}
//
//		int terms = memberLoan.getLoan().getTerms();
//		LocalDate dueDate = memberLoan.getLoan().getDueDate();
//
//		Map<Integer, LocalDate> schedule = generateSchedule(terms, dueDate);
//
//		return memberLoan;
		return null;
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
