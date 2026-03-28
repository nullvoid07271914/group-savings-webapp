package com.groupsavings.component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.groupsavings.model.dto.MemberTotalContributionDto;

@Component
public class MemberTotalContributionMapper {

	private enum RowColumn {
		MEMBER_CODE(0), FIRSTNAME(1), LASTNAME(2), JOIN_DATE(3), AMOUNT(4);

		private final int index;

		RowColumn(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

	public List<MemberTotalContributionDto> toMemberTotalContributionDto(List<Object[]> rows) {
		if (rows == null || rows.isEmpty()) {
			return List.of();
		}

		List<MemberTotalContributionDto> memberContributions = new ArrayList<MemberTotalContributionDto>();
		for (Object[] row : rows) {
			MemberTotalContributionDto details = new MemberTotalContributionDto();
			extract(row, details);
			memberContributions.add(details);
		}

		return memberContributions;
	}

	private void extract(Object[] row, MemberTotalContributionDto memberDto) {
		memberDto.setMemberCode(getString(row, RowColumn.MEMBER_CODE));
		memberDto.setFirstname(getString(row, RowColumn.FIRSTNAME));
		memberDto.setLastname(getString(row, RowColumn.LASTNAME));
		memberDto.setJoinedDate(getLocalDate(row, RowColumn.JOIN_DATE));
		memberDto.setAmount(getBigDecimal(row, RowColumn.AMOUNT));
	}

	private String getString(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? (String) row[col.getIndex()] : null;
	}

	private BigDecimal getBigDecimal(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? (BigDecimal) row[col.getIndex()] : null;
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
