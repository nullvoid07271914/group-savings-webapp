package com.groupsavings.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.groupsavings.model.dto.LoanContributorDto;
import com.groupsavings.model.dto.LoanPaymentDto;
import com.groupsavings.model.dto.LoanResponseDto;
import com.groupsavings.model.dto.MemberResponseDto;
import com.groupsavings.model.entity.Loan;
import com.groupsavings.model.entity.LoanMemberAllocation;
import com.groupsavings.model.entity.Member;
import com.groupsavings.model.entity.Payment;

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
	@Mapping(target = "loanContributors", source = "loanMembersAllocation", qualifiedByName = "toLoanContributors")
	LoanResponseDto toDto(Loan loan);

	@Named("toLoanContributors")
	default List<LoanContributorDto> toLoanContributors(List<LoanMemberAllocation> contributors) {
		return contributors.stream().map(item -> {
			LoanContributorDto dto = new LoanContributorDto();
			dto.setFullname(item.getMember().getFirstname() + " " + item.getMember().getLastname());
			dto.setAmount(item.getContributionAmount());
			dto.setMemberCode(item.getMember().getMemberCode());
			dto.setPercentage(item.getContributionPercentage());
			return dto;
		}).toList();
	}

	@Mapping(target = "loanCode", expression = "java(fetchLoanCode(payment))")
	@Mapping(target = "amount", expression = "java(fetchAmortization(payment))")
	@Mapping(target = "payInTerm", source = "payInTerm")
	@Mapping(target = "paymentMethod", source = "paymentMethod")
	@Mapping(target = "referenceNumber", source = "referenceNumber")
	@Mapping(target = "paymentDate", source = "paymentDate")
	LoanPaymentDto toLoanPaymentDto(Payment payment);

	default String fetchLoanCode(Payment payment) {
		return payment.getLoan().getLoanCode();
	}

	default BigDecimal fetchAmortization(Payment payment) {
		return payment.getLoan().getAmortization();
	}

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