package com.groupsavings.component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.groupsavings.model.dto.AmountPerMemberDto;

@Component
public class AmountInPoolPerMemberMapper {

	public AmountPerMemberDto toDto(Object[] row) {
		if (row == null || row.length < 2)
			return null;

		AmountPerMemberDto dto = new AmountPerMemberDto();
		dto.setMemberCode(row[0] != null ? row[0].toString() : null);
		dto.setAmount(toBigDecimal(row[1]));

		return dto;
	}

	public List<AmountPerMemberDto> toDtoList(List<Object[]> rows) {
		if (rows == null)
			return List.of();
		return rows.stream().map(this::toDto).collect(Collectors.toList());
	}

	private BigDecimal toBigDecimal(Object value) {
		if (value == null)
			return BigDecimal.ZERO;
		if (value instanceof BigDecimal)
			return (BigDecimal) value;
		if (value instanceof Double)
			return BigDecimal.valueOf((Double) value);
		if (value instanceof Integer)
			return BigDecimal.valueOf((Integer) value);
		if (value instanceof Long)
			return BigDecimal.valueOf((Long) value);
		if (value instanceof String)
			return new BigDecimal((String) value);
		return BigDecimal.ZERO;
	}
}
