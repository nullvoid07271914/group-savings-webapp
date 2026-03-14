package com.groupsavings.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.groupsavings.component.AmountInPoolPerMemberMapper;
import com.groupsavings.constants.LoanConstants;
import com.groupsavings.exception.BorrowerLoanExistException;
import com.groupsavings.exception.BorrowerNotFoundException;
import com.groupsavings.mapper.LoanMapper;
import com.groupsavings.mapper.MemberMapper;
import com.groupsavings.model.dto.AmountPerMemberDto;
import com.groupsavings.model.dto.LoanContributorDto;
import com.groupsavings.model.dto.LoanRequestDto;
import com.groupsavings.model.dto.LoanResponseDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.model.entity.Loan;
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

@Service
public class LoanServiceImpl implements LoanService, LoanConstants {

	private static final Logger log = LoggerFactory.getLogger(LoanService.class);

	private final MemberRepository memberRepository;

	private final LoanRepository loanRepository;

	private final ConfigRepositoty configRepositoty;

	private final AmountInPoolPerMemberMapper amountInPoolPerMemberMapper;

	private final LoanMapper loanMapper;

	private final MemberMapper memberMapper;

	public LoanServiceImpl(MemberRepository memberRepository, LoanRepository loanRepository,
			ConfigRepositoty configRepositoty, AmountInPoolPerMemberMapper amountInPoolPerMemberMapper,
			LoanMapper loanMapper, MemberMapper memberMapper) {
		this.memberRepository = memberRepository;
		this.loanRepository = loanRepository;
		this.configRepositoty = configRepositoty;
		this.amountInPoolPerMemberMapper = amountInPoolPerMemberMapper;
		this.loanMapper = loanMapper;
		this.memberMapper = memberMapper;
	}

	@Override
	public LoanResponseDto applyLoan(LoanRequestDto request) {
		String code = request.getMemberCode();
		Member borrower = memberRepository.findByMemberCode(code);

		LoanResponseDto loanDto = new LoanResponseDto();
		if (Objects.nonNull(borrower)) {
			Loan loan = new Loan();

			Loan activeLoan = loanRepository.findByBorrowerIdAndLoanStatus(borrower, LoanStatus.ACTIVE);
			if (Objects.nonNull(activeLoan)) {
				Optional<Member> member = memberRepository.findById(activeLoan.getMember().getMemberId());
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

			LocalDate currentDate = LocalDate.now();
			BigDecimal loanAmount = request.getLoanAmount();

			BigDecimal interestRate = BigDecimal.valueOf(loan.getInterestRate());
			BigDecimal totalAmount = loanAmount.multiply(interestRate).add(loanAmount);
			BigDecimal terms = BigDecimal.valueOf(request.getTerms());
			BigDecimal amortization = totalAmount.divide(terms, 2, RoundingMode.FLOOR);

			LocalDate dueDate = TermDueDateUtils.calculateDueDate(currentDate, request.getTerms());

			loan.setMember(borrower);
			loan.setLoanCode(request.getLoanCode());
			loan.setSavingsPool(savingsPool);

			loan.setLoanAmount(loanAmount);
			loan.setTerms(request.getTerms());
			loan.setDateApplied(currentDate);
			loan.setTotalAmount(totalAmount.setScale(2, RoundingMode.FLOOR));
			loan.setLoanStatus(LoanStatus.PENDING);
			loan.setDueDate(dueDate);
			loan.setAmortization(amortization.setScale(2, RoundingMode.FLOOR));

			Loan savedLoan = loanRepository.save(loan);
			loanDto = loanMapper.toDto(savedLoan);

			log.info("loanDto: {}", loanDto);

			List<AmountPerMemberDto> memberAmountPoolList = availableAmountInPoolPerMember(savingsPool.getPoolId());
			BigDecimal totalAmountInPool = memberAmountPoolList.stream().map(AmountPerMemberDto::getAmount)
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

			List<LoanContributorDto> loanContributors = new ArrayList<LoanContributorDto>();
			if (totalAmountInPool.compareTo(loanAmount) > 0) {
				BigDecimal baseAmount = totalAmountInPool.subtract(loanAmount);

				List<AmountPerMemberDto> memberDtoList = memberAmountPoolList.stream().filter(this::canContributeLoan)
						.toList();

				for (AmountPerMemberDto item : memberDtoList) {
					BigDecimal memberAmount = item.getAmount();
					BigDecimal percentage = memberAmount.divide(totalAmountInPool, 2, RoundingMode.FLOOR);
					BigDecimal lessAmount = percentage.multiply(baseAmount);
					BigDecimal contribution = memberAmount.subtract(lessAmount);

					LoanContributorDto contributorDto = new LoanContributorDto();

					Member memberContributor = memberRepository.findByMemberCode(item.getMemberCode());
					MemberResponseDto memberDto = memberMapper.toResponseDto(memberContributor);

					contributorDto.setMemberCode(item.getMemberCode());
					contributorDto.setFullname(memberDto.getFirstname() + " " + memberDto.getLastname());
					contributorDto.setAmount(contribution.setScale(2, RoundingMode.FLOOR));
					contributorDto.setPercentage(percentage.floatValue());
					loanContributors.add(contributorDto);
				}

				loanDto.setLoanContributors(loanContributors);
			}

		} else {
			throw new BorrowerNotFoundException("Required borrower to apply loan.");
		}

		return loanDto;
	}

	@Override
	public List<LoanResponseDto> loanByStatus(LoanStatus status) {
		List<Loan> pendingsLoans = loanRepository.findByLoanStatus(status);
		List<LoanResponseDto> loansDto = pendingsLoans.stream().map(loanMapper::toDto).toList();
		return loansDto;
	}

	private boolean canContributeLoan(AmountPerMemberDto memberDto) {
		BigDecimal amount = memberDto.getAmount();
		return amount.compareTo(BigDecimal.valueOf(100)) > 0;
	}

	private List<AmountPerMemberDto> availableAmountInPoolPerMember(Long poolId) {
		List<Object[]> poolAmountPerMember = loanRepository.fetchMembersPoolAmountForLoan(poolId);
		log.info("poolAmountPerMember: {}", poolAmountPerMember);
		List<AmountPerMemberDto> perMemberList = amountInPoolPerMemberMapper.toDtoList(poolAmountPerMember);
		return perMemberList;
	}
}
