package com.groupsavings.mapper;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.groupsavings.model.dto.MemberRequestDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.model.entity.Address;
import com.groupsavings.model.entity.Member;
import com.groupsavings.model.enums.MemberStatus;
import com.groupsavings.model.enums.MemberType;
import com.groupsavings.utils.MemberUtils;

@Mapper(componentModel = "spring", imports = { LocalDate.class })
public interface MemberMapper {

	@Mapping(target = "memberId", ignore = true)
	@Mapping(target = "memberCode", expression = "java(generateMemberCode())")
	@Mapping(target = "firstname", source = "firstname")
	@Mapping(target = "lastname", source = "lastname")
	@Mapping(target = "mobileNumber", source = "mobileNumber")
	@Mapping(target = "email", source = "email")
	@Mapping(target = "joinDate", expression = "java(LocalDate.now())")
	@Mapping(target = "memberType", source = "memberType", qualifiedByName = "stringToMemberType")
	@Mapping(target = "memberStatus", constant = "ACTIVE")
	@Mapping(target = "address", expression = "java(mapAddress(requestDto))")
	@Mapping(target = "contributions", ignore = true)
	@Mapping(target = "loans", ignore = true)
	@Mapping(target = "loanAllocations", ignore = true)
	@Mapping(target = "createdDate", ignore = true)
	@Mapping(target = "updatedDate", ignore = true)
	Member toEntity(MemberRequestDto requestDto);

	@Mapping(target = "memberCode", source = "memberCode")
	@Mapping(target = "loanCode", ignore = true)
	@Mapping(target = "firstname", source = "firstname")
	@Mapping(target = "lastname", source = "lastname")
	@Mapping(target = "mobileNumber", source = "mobileNumber")
	@Mapping(target = "email", source = "email")
	@Mapping(target = "type", source = "memberType", qualifiedByName = "memberTypeToString")
	@Mapping(target = "status", source = "memberStatus", qualifiedByName = "memberStatusToString")
	@Mapping(target = "interest", ignore = true)
	@Mapping(target = "joinedDate", source = "joinDate")
	@Mapping(target = "street", expression = "java(fetchAddressStreet(member))")
	@Mapping(target = "barangay", expression = "java(fetchAddressBarangay(member))")
	@Mapping(target = "city", expression = "java(fetchAddressCity(member))")
	@Mapping(target = "province", expression = "java(fetchAddressProvince(member))")
	@Mapping(target = "zipcode", expression = "java(fetchAddressZipCode(member))")
	MemberResponseDto toResponseDto(Member member);

	// ========== UPDATE ENTITY ==========
//	@Mapping(target = "memberId", ignore = true)
//	@Mapping(target = "memberCode", ignore = true)
//	@Mapping(target = "address", expression = "java(mapAddress(requestDto))")
//	@Mapping(target = "joinDate", ignore = true)
//	@Mapping(target = "memberType", source = "memberType", qualifiedByName = "stringToMemberType")
//	@Mapping(target = "memberStatus", ignore = true)
//	@Mapping(target = "contributions", ignore = true)
//	@Mapping(target = "loans", ignore = true)
//	@Mapping(target = "loanAllocations", ignore = true)
//	@Mapping(target = "createdDate", ignore = true)
//	@Mapping(target = "updatedDate", expression = "java(LocalDateTime.now())")
//	void updateEntity(@MappingTarget Member member, MemberRequestDto requestDto);

	// ========== CUSTOM MAPPING METHODS ==========

	@Named("stringToMemberType")
	default MemberType stringToMemberType(String memberType) {
		try {
			return MemberType.valueOf(memberType.toUpperCase());
		} catch (IllegalArgumentException e) {
			return MemberType.UNKNOWN;
		}
	}

	@Named("memberTypeToString")
	default String memberTypeToString(MemberType memberType) {
		return memberType != null ? memberType.name() : MemberType.UNKNOWN.name();
	}

	@Named("memberStatusToString")
	default String memberStatusToString(MemberStatus memberStatus) {
		return memberStatus != null ? memberStatus.name() : null;
	}

	default Address mapAddress(MemberRequestDto dto) {
		if (dto == null)
			return null;

		Address address = new Address();
		address.setStreet(dto.getStreet());
		address.setBarangay(dto.getBarangay());
		address.setCity(dto.getCity());
		address.setProvince(dto.getProvince());
		address.setZipCode(dto.getZipcode());
		return address;
	}

	default String generateMemberCode() {
		return MemberUtils.buildMemberCode();
	}

	default String fetchAddressStreet(Member member) {
		return member.getAddress().getStreet();
	}

	default String fetchAddressBarangay(Member member) {
		return member.getAddress().getBarangay();
	}

	default String fetchAddressCity(Member member) {
		return member.getAddress().getCity();
	}

	default String fetchAddressProvince(Member member) {
		return member.getAddress().getProvince();
	}

	default String fetchAddressZipCode(Member member) {
		return member.getAddress().getZipCode();
	}
}
