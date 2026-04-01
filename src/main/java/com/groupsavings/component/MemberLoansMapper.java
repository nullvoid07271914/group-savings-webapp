package com.groupsavings.component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.groupsavings.model.dto.LoanDto;
import com.groupsavings.model.dto.MemberLoanDto;
import com.groupsavings.model.dto.MemberLoansDto;
import com.groupsavings.model.dto.PaymentDto;
import com.groupsavings.model.enums.LoanStatus;

@Component
public class MemberLoansMapper {

	private enum RowColumn {
		MEMBER_CODE(0), FIRSTNAME(1), LASTNAME(2), LOAN_CODE(3), LOAN_AMOUNT(4), INTEREST_RATE(5), TERMS(6),
		LOAN_STATUS(7), DATE_APPLIED(8), DATE_APPROVED(9), DATE_RELEASED(10), DUE_DATE(11), TOTAL_AMOUNT(12),
		AMORTIZATION(13), PAYMENT_CODE(14), PAY_IN_TERM(15), AMOUNT_PAID(16), PAYMENT_DATE(17), PAYMENT_STATUS(18);

		private final int index;

		RowColumn(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

	public MemberLoansDto toDto(List<Object[]> rows) {
		if (rows == null || rows.isEmpty()) {
			return new MemberLoansDto();
		}

		MemberLoansDto memberDto = new MemberLoansDto();
		List<LoanDto> loans = new ArrayList<>();

		for (Object[] row : rows) {
			if (memberDto.getMemberCode() == null) {
				extractMember(row, memberDto);
			}
			loans.add(extractLoanDetails(row));
		}

		memberDto.setLoans(loans);
		return memberDto;
	}

	public List<MemberLoansDto> toDtoList(List<Object[]> rows) {
		if (rows == null || rows.isEmpty()) {
			return List.of();
		}

		List<MemberLoansDto> memberLoanList = new ArrayList<MemberLoansDto>();

		for (Object[] row : rows) {

			MemberLoansDto fetchedMember = new MemberLoansDto();
			extractMember(row, fetchedMember);

			LoanDto fetchedLoan = extractLoanDetails(row);
			PaymentDto fetchedPayment = extractLoanPayment(row);

			if (memberLoanList.isEmpty()) {
				List<LoanDto> loans = new ArrayList<>();
				List<PaymentDto> payments = new ArrayList<>();

				if (Objects.nonNull(fetchedPayment)) {
					payments.add(fetchedPayment);
				}

				fetchedLoan.setPayments(payments);
				loans.add(fetchedLoan);
				fetchedMember.setLoans(loans);

				memberLoanList.add(fetchedMember);

			} else {

				Optional<MemberLoansDto> existMember = memberLoanList.stream()
						.filter(item -> Objects.equals(item.getMemberCode(), fetchedMember.getMemberCode())).findFirst();

				MemberLoansDto matchedMember = null;
				if (existMember.isPresent()) {
					matchedMember = existMember.get();

					Optional<LoanDto> matchedLoan = matchedMember.getLoans().stream()
							.filter(item -> Objects.equals(item.getLoanCode(), fetchedLoan.getLoanCode())).findFirst();

					if (matchedLoan.isPresent()) {
						LoanDto loan = matchedLoan.get();
						loan.getPayments().add(fetchedPayment);
					} else {
						List<PaymentDto> payments = new ArrayList<>();

						if (Objects.nonNull(fetchedPayment)) {
							payments.add(fetchedPayment);
						}

						fetchedLoan.setPayments(payments);
						
						List<LoanDto> loans = matchedMember.getLoans();
						if (Objects.isNull(loans) || loans.isEmpty()) {
							List<LoanDto> loanList = new ArrayList<>();
							loanList.add(fetchedLoan);
							matchedMember.setLoans(loanList);
						} else {
							matchedMember.getLoans().add(fetchedLoan);
						}
					}

				} else {
					List<LoanDto> loans = new ArrayList<>();
					List<PaymentDto> payments = new ArrayList<>();

					if (Objects.nonNull(fetchedPayment)) {
						payments.add(fetchedPayment);
					}

					fetchedLoan.setPayments(payments);
					loans.add(fetchedLoan);
					fetchedMember.setLoans(loans);

					memberLoanList.add(fetchedMember);
				}
			}
		}

		return memberLoanList;
	}

	public MemberLoanDto toSingleDto(Object[] rows) {
		if (rows == null || rows.length == 0) {
			return null;
		}

		MemberLoanDto memberDto = new MemberLoanDto();
		extractMember(rows, memberDto);
		LoanDto loan = extractLoanDetails(rows);
		memberDto.setLoan(loan);

		return memberDto;
	}

	private void extractMember(Object[] row, MemberLoanDto memberDto) {
		memberDto.setMemberCode(getString(row, RowColumn.MEMBER_CODE));
		memberDto.setFirstname(getString(row, RowColumn.FIRSTNAME));
		memberDto.setLastname(getString(row, RowColumn.LASTNAME));
	}

	private void extractMember(Object[] row, MemberLoansDto memberDto) {
		memberDto.setMemberCode(getString(row, RowColumn.MEMBER_CODE));
		memberDto.setFirstname(getString(row, RowColumn.FIRSTNAME));
		memberDto.setLastname(getString(row, RowColumn.LASTNAME));
	}

	private PaymentDto extractLoanPayment(Object[] row) {
		PaymentDto paymentDto = null;

		String paymentCode = getString(row, RowColumn.PAYMENT_CODE);
		if (Objects.nonNull(paymentCode)) {
			paymentDto = new PaymentDto();
			paymentDto.setPaymentCode(paymentCode);
			paymentDto.setAmountPaid(getBigDecimal(row, RowColumn.AMOUNT_PAID));
			paymentDto.setPaymentDate(getLocalDate(row, RowColumn.PAYMENT_DATE));
			paymentDto.setStatus(getString(row, RowColumn.PAYMENT_STATUS));
			paymentDto.setTerm(getInteger(row, RowColumn.PAY_IN_TERM));
		}
		return paymentDto;
	}

	private LoanDto extractLoanDetails(Object[] row) {
		LoanDto loanDto = new LoanDto();

		loanDto.setLoanCode(getString(row, RowColumn.LOAN_CODE));
		loanDto.setLoanAmount(getBigDecimal(row, RowColumn.LOAN_AMOUNT));
		loanDto.setInterestRate(getFloat(row, RowColumn.INTEREST_RATE));
		loanDto.setTerms(getInt(row, RowColumn.TERMS));
		loanDto.setLoanStatus(getLoanStatus(row, RowColumn.LOAN_STATUS));
		loanDto.setDateApplied(getLocalDate(row, RowColumn.DATE_APPLIED));
		loanDto.setDateApproved(getLocalDate(row, RowColumn.DATE_APPROVED));
		loanDto.setDateReleased(getLocalDate(row, RowColumn.DATE_RELEASED));
		loanDto.setDueDate(getLocalDate(row, RowColumn.DUE_DATE));
		loanDto.setTotalAmount(getBigDecimal(row, RowColumn.TOTAL_AMOUNT));
		loanDto.setAmortization(getBigDecimal(row, RowColumn.AMORTIZATION));

		return loanDto;
	}

	private String getString(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? (String) row[col.getIndex()] : null;
	}

	private Integer getInteger(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? Integer.valueOf((Integer) row[col.getIndex()]) : null;
	}

	private BigDecimal getBigDecimal(Object[] row, RowColumn col) {
		Object value = row[col.getIndex()];
		if (value == null)
			return null;

		// Handle BigDecimal directly
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}

		// Handle Double (from MySQL SUM, addition, etc.)
		if (value instanceof Double) {
			return BigDecimal.valueOf((Double) value);
		}

		// Handle Integer
		if (value instanceof Integer) {
			return BigDecimal.valueOf((Integer) value);
		}

		// Handle Long
		if (value instanceof Long) {
			return BigDecimal.valueOf((Long) value);
		}

		// Handle Float
		if (value instanceof Float) {
			return BigDecimal.valueOf((Float) value);
		}

		// Handle String (just in case)
		if (value instanceof String) {
			try {
				return new BigDecimal((String) value);
			} catch (NumberFormatException e) {
				return BigDecimal.ZERO;
			}
		}

		// Default return
		return BigDecimal.ZERO;
	}

	private Float getFloat(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? ((Number) row[col.getIndex()]).floatValue() : null;
	}

	private Integer getInt(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? ((Number) row[col.getIndex()]).intValue() : null;
	}

	private LoanStatus getLoanStatus(Object[] row, RowColumn col) {
		String status = getString(row, col);
		return status != null ? LoanStatus.valueOf(status) : null;
	}

	private LocalDate getLocalDate(Object[] row, RowColumn col) {
		Object date = row[col.getIndex()];
		if (date == null)
			return null;

		if (date instanceof java.sql.Date) {
			return ((java.sql.Date) date).toLocalDate();
		}
		if (date instanceof java.sql.Timestamp) {
			return ((java.sql.Timestamp) date).toLocalDateTime().toLocalDate();
		}
		if (date instanceof LocalDate) {
			return (LocalDate) date;
		}

		return null;
	}
}
