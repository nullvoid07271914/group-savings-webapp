package com.groupsavings.mapper;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.groupsavings.model.dto.ContributionRequestDto;
import com.groupsavings.model.dto.ContributionResponseDto;
import com.groupsavings.model.entity.Contribution;
import com.groupsavings.utils.ContributionUtils;

@Mapper(componentModel = "spring", imports = { LocalDate.class })
public interface ContributionMapper {

	@Mapping(target = "contributionId", ignore = true)
	@Mapping(target = "contributionCode", expression = "java(generateContributionCode())")
	@Mapping(target = "pool", ignore = true)
	@Mapping(target = "member", ignore = true)
	@Mapping(target = "amount", source = "amount")
	@Mapping(target = "contributionDate", source = "contributionDate")
	@Mapping(target = "paymentMethod", source = "paymentMethod")
	@Mapping(target = "referenceNumber", source = "referenceNumber")
	@Mapping(target = "monthTerm", source = "term")
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "notes", source = "notes")
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "updatedDate", ignore = true)
	Contribution toEntity(ContributionRequestDto contributionDto);

	@Mapping(target = "code", source = "contributionCode")
	@Mapping(target = "status", source = "status")
	ContributionResponseDto toResponseDto(Contribution contribution);

	default String generateContributionCode() {
		return ContributionUtils.buildContributionCode();
	}
}
