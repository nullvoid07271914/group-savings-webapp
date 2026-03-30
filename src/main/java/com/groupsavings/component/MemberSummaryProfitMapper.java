package com.groupsavings.component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.groupsavings.model.dto.MemberSummaryProfit;

@Component
public class MemberSummaryProfitMapper {

	private enum RowColumn {

		MEMBER_CODE(0), FIRSTNAME(1), LASTNAME(2), JOIN_DATE(3), TOTAL_CONTRIBUTION(4), TOTAL_PROFIT(5),
		TOTAL_SUMMARY(6);

		private final int index;

		RowColumn(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

	public List<MemberSummaryProfit> toMemberSummaryProfitList(List<Object[]> rows) {
		if (rows == null || rows.isEmpty()) {
			return List.of();
		}

		List<MemberSummaryProfit> profits = new ArrayList<>();
		for (Object[] row : rows) {
			profits.add(extractProfit(row));
		}

		return profits;
	}

	private MemberSummaryProfit extractProfit(Object[] row) {
		MemberSummaryProfit summary = new MemberSummaryProfit();
		summary.setMemberCode(getString(row, RowColumn.MEMBER_CODE));
		summary.setFirstname(getString(row, RowColumn.FIRSTNAME));
		summary.setLastname(getString(row, RowColumn.LASTNAME));
		summary.setJoinDate(getLocalDate(row, RowColumn.JOIN_DATE));
		summary.setTotalContribution(getBigDecimal(row, RowColumn.TOTAL_CONTRIBUTION));
		summary.setTotalProfit(getBigDecimal(row, RowColumn.TOTAL_PROFIT));
		summary.setTotalSummary(getBigDecimal(row, RowColumn.TOTAL_SUMMARY));
		return summary;
	}

	private String getString(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? (String) row[col.getIndex()] : null;
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
