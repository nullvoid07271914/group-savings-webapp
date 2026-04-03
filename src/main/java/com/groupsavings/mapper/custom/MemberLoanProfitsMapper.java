package com.groupsavings.mapper.custom;

import com.groupsavings.model.dto.BorrowerLoanPaymentsDto;
import com.groupsavings.model.dto.MemberErningsFromLoansDto;
import com.groupsavings.model.dto.MemberLoanProfitsDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class MemberLoanProfitsMapper extends BaseMapper {

    private enum RowColumn implements BaseColumnIndex {
        MEMBER_CODE(0), FIRSTNAME(1), LASTNAME(2), JOIN_DATE(3), LOAN_CODE(4), BORROWER_CODE(5), BORROWER_FIRSTNAME(6), BORROWER_LASTNAME(7), LOAN_AMOUNT(8), TOTAL_AMOUNT(9), DATE_RELEASED(10), CONTRIBUTION_AMOUNT(11), CONTRIBUTION_PERCENTAGE(12), PAY_IN_TERM(13), AMOUNT_PAID(14), PAYMENT_DATE(15), LOAN_PROFIT(16);

        private final int index;

        RowColumn(int index) {
            this.index = index;
        }

        @Override
        public int getIndex() {
            return index;
        }
    }

    public MemberLoanProfitsDto toMemberProfitsDto(List<Object[]> rows) {
        if (rows == null || rows.isEmpty()) {
            return null;
        }

        MemberLoanProfitsDto memberDto = null;
        for (Object[] row : rows) {
            if (Objects.isNull(memberDto)) {
                memberDto = new MemberLoanProfitsDto();
                extractMember(row, memberDto);
            }

            MemberErningsFromLoansDto earnings = extractParticipatedLoan(row);
            BorrowerLoanPaymentsDto payment = extractLoanPayments(row);

            if (Objects.isNull(memberDto.getEarningsFromLoans())) {
                List<MemberErningsFromLoansDto> earningsList = new ArrayList<>();
                List<BorrowerLoanPaymentsDto> paymentList = new ArrayList<>();

                paymentList.add(payment);
                earnings.setBorrowerPayments(paymentList);

                earningsList.add(earnings);
                memberDto.setEarningsFromLoans(earningsList);
                
            } else {
                Optional<MemberErningsFromLoansDto> loanExist = memberDto.getEarningsFromLoans().stream()
                        .filter(item -> Objects.equals(item.getLoanCode(), earnings.getLoanCode()))
                        .findFirst();

                if (loanExist.isPresent()) {
                    MemberErningsFromLoansDto currentLoan = loanExist.get();
                    currentLoan.getBorrowerPayments().add(payment);
                } else {
                    List<BorrowerLoanPaymentsDto> paymentList = new ArrayList<>();
                    paymentList.add(payment);
                    earnings.setBorrowerPayments(paymentList);
                    memberDto.getEarningsFromLoans().add(earnings);
                }
            }
        }

        return memberDto;
    }

    private void extractMember(Object[] row, MemberLoanProfitsDto memberDto) {
        memberDto.setMemberCode(getString(row, RowColumn.MEMBER_CODE));
        memberDto.setFirstname(getString(row, RowColumn.FIRSTNAME));
        memberDto.setLastname(getString(row, RowColumn.LASTNAME));
        memberDto.setJoinedDate(getLocalDate(row, RowColumn.JOIN_DATE));
    }

    private BorrowerLoanPaymentsDto extractLoanPayments(Object[] row) {
        BorrowerLoanPaymentsDto payment = new BorrowerLoanPaymentsDto();
        payment.setPayInTerm(getInt(row, RowColumn.PAY_IN_TERM));
        payment.setPaymentDate(getLocalDate(row, RowColumn.PAYMENT_DATE));
        payment.setAmountPaid(getBigDecimal(row, RowColumn.AMOUNT_PAID));
        payment.setLoanProfitAmount(getBigDecimal(row, RowColumn.LOAN_PROFIT));
        return payment;
    }

    private MemberErningsFromLoansDto extractParticipatedLoan(Object[] row) {
        MemberErningsFromLoansDto earnings = new MemberErningsFromLoansDto();
        earnings.setBorrowerCode(getString(row, RowColumn.BORROWER_CODE));
        earnings.setBorrowerFirstname(getString(row, RowColumn.BORROWER_FIRSTNAME));
        earnings.setBorrowerLastname(getString(row, RowColumn.BORROWER_LASTNAME));
        earnings.setLoanCode(getString(row, RowColumn.LOAN_CODE));
        earnings.setLoanAmount(getBigDecimal(row, RowColumn.LOAN_AMOUNT));
        earnings.setTotalAmount(getBigDecimal(row, RowColumn.TOTAL_AMOUNT));
        earnings.setDateReleased(getLocalDate(row, RowColumn.DATE_RELEASED));
        earnings.setContributionAmount(getBigDecimal(row, RowColumn.CONTRIBUTION_AMOUNT));
        earnings.setContributionPercentage(toPercentageString(getBigDecimal(row, RowColumn.CONTRIBUTION_PERCENTAGE)));
        return earnings;
    }

    private String toPercentageString(BigDecimal value) {
        if (value == null) return null;

        BigDecimal result = value
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.DOWN);

        return result.toString();
    }
}
