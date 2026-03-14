package com.groupsavings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.groupsavings.model.dto.LoanResponseDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.model.entity.Loan;
import com.groupsavings.model.entity.Member;

@Mapper(componentModel = "spring", uses = { MemberMapper.class })
public interface LoanMapper {

	@Mapping(target = "loanCode", source = "loanCode")
	@Mapping(target = "member", source = "member", qualifiedByName = "toMemberResponseDto")
	@Mapping(target = "loanAmount", source = "loanAmount")
	@Mapping(target = "interestRate", source = "interestRate")
	@Mapping(target = "terms", source = "terms")
	@Mapping(target = "loanStatus", source = "loanStatus")
	@Mapping(target = "dateApplied", source = "dateApplied")
	@Mapping(target = "dateApproved", source = "dateApproved")
	@Mapping(target = "dateReleased", source = "dateReleased")
	@Mapping(target = "dateFullyPaid", source = "dateFullyPaid")
	@Mapping(target = "totalAmount", source = "totalAmount")
	@Mapping(target = "dueDate", source = "dueDate")
	@Mapping(target = "amortization", source = "amortization")
	@Mapping(target = "loanContributors", ignore = true)
	LoanResponseDto toDto(Loan loan);

	@Named("toMemberResponseDto")
	default MemberResponseDto toMemberResponseDto(Member member) {
		if (member == null)
			return null;

		MemberResponseDto dto = new MemberResponseDto();
		dto.setMemberCode(member.getMemberCode());
		dto.setFirstname(member.getFirstname());
		dto.setLastname(member.getLastname());
		dto.setMobileNumber(member.getMobileNumber());
		dto.setEmail(member.getEmail());
		dto.setType(member.getMemberType() != null ? member.getMemberType().name() : null);
		dto.setStatus(member.getMemberStatus() != null ? member.getMemberStatus().name() : null);
		dto.setJoinedDate(member.getJoinDate());

		if (member.getAddress() != null) {
			dto.setStreet(member.getAddress().getStreet());
			dto.setBarangay(member.getAddress().getBarangay());
			dto.setCity(member.getAddress().getCity());
			dto.setProvince(member.getAddress().getProvince());
			dto.setZipcode(member.getAddress().getZipCode());
		}

		return dto;
	}
}