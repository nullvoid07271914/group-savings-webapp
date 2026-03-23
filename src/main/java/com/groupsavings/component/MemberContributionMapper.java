package com.groupsavings.component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import com.groupsavings.model.dto.ContributionDto;
import com.groupsavings.model.dto.MemberContributions;

@Component
public class MemberContributionMapper {

	private enum RowColumn {
		MEMBER_CODE(0), FIRSTNAME(1), LASTNAME(2), JOIN_DATE(3), CONTRIBUTION_CODE(4), AMOUNT(5), CONTRIBUTION_DATE(6),
		PAYMENT_METHOD(7), REFERENCE_NUMBER(8), MONTH_TERM(9);

		private final int index;

		RowColumn(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

	public List<MemberContributions> toMemberContributionsDtoList(List<Object[]> rows) {
		if (rows == null || rows.isEmpty()) {
			return List.of();
		}

		List<MemberContributions> memberContributions = new ArrayList<MemberContributions>();
		for (Object[] row : rows) {
			if (memberContributions.isEmpty()) {
				MemberContributions member = new MemberContributions();
				extractMember(row, member);
				List<ContributionDto> contributions = new ArrayList<>();
				contributions.add(extractContribution(row));
				member.setContributions(contributions);
				memberContributions.add(member);
			} else {
				MemberContributions member = new MemberContributions();
				extractMember(row, member);

				Optional<MemberContributions> currentMember = memberContributions.stream()
						.filter(mem -> Objects.equals(mem.getMemberCode(), member.getMemberCode())).findFirst();
				if (currentMember.isEmpty()) {
					List<ContributionDto> contributions = new ArrayList<>();
					contributions.add(extractContribution(row));
					member.setContributions(contributions);
					memberContributions.add(member);
				} else {
					MemberContributions fetchedMember = currentMember.get();
					fetchedMember.getContributions().add(extractContribution(row));
				}
			}
		}

		return memberContributions;
	}

	public MemberContributions toMemberContributionsDto(List<Object[]> rows) {
		if (rows == null || rows.isEmpty()) {
			return new MemberContributions();
		}

		MemberContributions member = new MemberContributions();

		for (Object[] row : rows) {
			if (Strings.isEmpty(member.getMemberCode())) {
				extractMember(row, member);
				List<ContributionDto> contributions = new ArrayList<>();
				contributions.add(extractContribution(row));
				member.setContributions(contributions);
			} else {
				member.getContributions().add(extractContribution(row));
			}
		}

		return member;
	}

	private void extractMember(Object[] row, MemberContributions memberDto) {
		memberDto.setMemberCode(getString(row, RowColumn.MEMBER_CODE));
		memberDto.setFirstname(getString(row, RowColumn.FIRSTNAME));
		memberDto.setLastname(getString(row, RowColumn.LASTNAME));
		memberDto.setJoinedDate(getLocalDate(row, RowColumn.JOIN_DATE));
	}

	private ContributionDto extractContribution(Object[] row) {
		ContributionDto contribution = new ContributionDto();

		contribution.setContributionCode(getString(row, RowColumn.CONTRIBUTION_CODE));
		contribution.setAmount(getBigDecimal(row, RowColumn.AMOUNT));
		contribution.setContributionDate(getLocalDate(row, RowColumn.CONTRIBUTION_DATE));
		contribution.setPaymentMethod(getString(row, RowColumn.PAYMENT_METHOD));
		contribution.setReferenceNumber(getString(row, RowColumn.REFERENCE_NUMBER));
		contribution.setMonthTerm(getInt(row, RowColumn.MONTH_TERM));

		return contribution;
	}

	private String getString(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? (String) row[col.getIndex()] : null;
	}

	private BigDecimal getBigDecimal(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? (BigDecimal) row[col.getIndex()] : null;
	}

	private Integer getInt(Object[] row, RowColumn col) {
		return row[col.getIndex()] != null ? ((Number) row[col.getIndex()]).intValue() : null;
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
