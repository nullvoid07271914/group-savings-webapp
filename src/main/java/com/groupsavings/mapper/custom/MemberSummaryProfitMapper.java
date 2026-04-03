package com.groupsavings.mapper.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.groupsavings.model.dto.MemberSummaryProfit;

@Component
public class MemberSummaryProfitMapper extends BaseMapper {

	private enum RowColumn implements BaseColumnIndex {

		MEMBER_CODE(0), FIRSTNAME(1), LASTNAME(2), JOIN_DATE(3), TOTAL_CONTRIBUTION(4), TOTAL_PROFIT(5),
		TOTAL_SUMMARY(6);

		private final int index;

		RowColumn(int index) {
			this.index = index;
		}

		@Override
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

}
