package ru.lsan.conveyor.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lsan.conveyor.dto.CreditDTO;
import ru.lsan.conveyor.dto.PaymentScheduleElement;
import ru.lsan.conveyor.dto.ScoringDataDTO;
import ru.lsan.conveyor.dto.enums.EmploymentStatusEnum;
import ru.lsan.conveyor.dto.enums.GenderEnum;
import ru.lsan.conveyor.dto.enums.MaritalStatusEnum;
import ru.lsan.conveyor.dto.enums.PositionEnum;
import ru.lsan.conveyor.exception.custom_exceptions.LoanDenialException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ScoringService {

    @Value("${baseLoanRate}")
    private BigDecimal baseLoanRate;

    private final int scale = 10;

    public CreditDTO scoreLoan(ScoringDataDTO dto) {
        if (ScoringConditions.isLoanShouldBeDenied(dto)) throw new LoanDenialException(""+dto.getLastName()+" loan is denied");
        return calculateCredit(dto);
    }

    private BigDecimal calculateRate(ScoringDataDTO dto) {
        BigDecimal rate = baseLoanRate;
        if (ScoringConditions.isSelfEmployed(dto)) {
            rate = rate.add(BigDecimal.valueOf(1));
        }
        if (ScoringConditions.isBusinessMan(dto)) {
            rate = rate.subtract(BigDecimal.valueOf(2));
        }
        if (ScoringConditions.isTopManager(dto)) {
            rate = rate.subtract(BigDecimal.valueOf(4));
        }
        if (ScoringConditions.isMarried(dto)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        }
        if (ScoringConditions.isDivorced(dto)) {
            rate = rate.add(BigDecimal.valueOf(1));
        }
        if (ScoringConditions.isFemaleFrom35to60(dto)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        }
        if (ScoringConditions.isMaleFrom30to55(dto)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        }
        if (ScoringConditions.isGenderNonBinary(dto)) {
            rate = rate.add(BigDecimal.valueOf(3));
        }
        return rate;
    }

    //аннуитетные платежи
    private CreditDTO calculateCredit(ScoringDataDTO dto) {
        BigDecimal rate = calculateRate(dto);
        CreditDTO creditDTO = CreditDTO.builder()
                .amount(dto.getAmount())
                .term(dto.getTerm())
                .rate(rate)
                .isInsuranceEnabled(dto.getIsInsuranceEnabled())
                .isSalaryClient(dto.getIsSalaryClient())
                .build();

        BigDecimal monthlyRate = creditDTO.getRate().divide(BigDecimal.valueOf(1200), scale, RoundingMode.HALF_UP);
        BigDecimal amount = creditDTO.getAmount();
        BigDecimal monthlyPayment;
        Integer period = creditDTO.getTerm();

        BigDecimal sub1 = BigDecimal.ONE.add(monthlyRate).pow(period);
        BigDecimal sub = BigDecimal.ONE.divide(sub1, scale, RoundingMode.HALF_UP);

        BigDecimal divisor = BigDecimal.ONE.subtract(sub);
        monthlyPayment = amount.multiply(monthlyRate).divide(divisor, scale, RoundingMode.HALF_UP);
        creditDTO.setMonthlyPayment(monthlyPayment);

        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(period));
        creditDTO.setPsk(psk);

        List<PaymentScheduleElement> paymentScheduleElementList = new ArrayList<>(period);
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < period; i++) {
            PaymentScheduleElement e = PaymentScheduleElement.builder().build();
            BigDecimal percentCreditPayment = psk.multiply(monthlyRate);
            BigDecimal bodyCreditPayment = monthlyPayment.subtract(percentCreditPayment);

            e.setDate(currentDate);
            currentDate = currentDate.plusMonths(1);

            e.setTotalPayment(monthlyPayment);

            e.setInterestPayment(bodyCreditPayment);
            e.setDebtPayment(percentCreditPayment);

            psk = psk.subtract(monthlyPayment);
            e.setRemainingDebt(psk);
            paymentScheduleElementList.add(e);
        }

        creditDTO.setPaymentScheduleElementList(paymentScheduleElementList);
        return creditDTO;
    }

}
