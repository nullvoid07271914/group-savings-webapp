package com.groupsavings.component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.groupsavings.model.dto.LoanDto;
import com.groupsavings.model.dto.MemberLoanDto;
import com.groupsavings.model.dto.MemberLoansDto;
import com.groupsavings.model.enums.LoanStatus;

@Component
public class MemberLoansMapper {

	private enum RowColumn {
		MEMBER_CODE(0), FIRSTNAME(1), LASTNAME(2), LOAN_CODE(3), LOAN_AMOUNT(4), INTEREST_RATE(5), TERMS(6),
		LOAN_STATUS(7), DATE_APPLIED(8), DATE_APPROVED(9), DATE_RELEASED(10), DUE_DATE(11), TOTAL_AMOUNT(12),
		AMORTIZATION(13);

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
			MemberLoansDto memberDto = new MemberLoansDto();
			List<LoanDto> loans = new ArrayList<>();

			extractMember(row, memberDto);
			loans.add(extractLoanDetails(row));
			memberDto.setLoans(loans);

			memberLoanList.add(memberDto);
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

	private BigDecimal getBigDecimal(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? (BigDecimal) row[col.getIndex()] : null;
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
