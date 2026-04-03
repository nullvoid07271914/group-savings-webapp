package com.groupsavings.mapper.custom;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.groupsavings.model.dto.AmountPerMemberDto;

@Component
public class AmountInPoolPerMemberMapper extends BaseMapper {

	public AmountPerMemberDto toDto(Object[] row) {
		if (row == null || row.length < 2)
			return null;

		AmountPerMemberDto dto = new AmountPerMemberDto();
		dto.setMemberCode(row[0] != null ? row[0].toString() : null);
		dto.setAmount(getBigDecimal(row, () -> 1));

		return dto;
	}

	public List<AmountPerMemberDto> toDtoList(List<Object[]> rows) {
		if (rows == null)
			return List.of();
		return rows.stream().map(this::toDto).collect(Collectors.toList());
	}

}
