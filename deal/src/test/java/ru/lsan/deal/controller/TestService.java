package ru.lsan.deal.controller;

import org.springframework.http.ResponseEntity;
import ru.lsan.deal.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TestService {

    private static final BigDecimal baseLoanRate = BigDecimal.TEN;
    private static final BigDecimal baseInsuranceCost = BigDecimal.valueOf(100000);
    private static final int insuranceRate = 1;
    private static final int salaryRate = 1;

    public static ResponseEntity<Optional<List<LoanOfferDTO>>> prescoreLoan(LoanApplicationRequestDTO dto) {
        List<LoanOfferDTO> loanOfferDTOList = new ArrayList<>(4);

        LoanOfferDTO insuranceEnabledSalaryClientDto = prescoreOfferDto(dto, true, true);
        loanOfferDTOList.add(insuranceEnabledSalaryClientDto);

        LoanOfferDTO insuranceEnabledNotSalaryClientDto = prescoreOfferDto(dto, true, false);
        loanOfferDTOList.add(insuranceEnabledNotSalaryClientDto);

        LoanOfferDTO notInsuranceEnabledSalaryClientDto = prescoreOfferDto(dto, false, true);
        loanOfferDTOList.add(notInsuranceEnabledSalaryClientDto);

        LoanOfferDTO notInsuranceEnabledNotSalaryClientDto = prescoreOfferDto(dto, false, false);
        loanOfferDTOList.add(notInsuranceEnabledNotSalaryClientDto);

        loanOfferDTOList.sort(Comparator.comparing(LoanOfferDTO::getRate));
        Optional<List<LoanOfferDTO>> op = Optional.of(loanOfferDTOList);
        return ResponseEntity.of(Optional.of(op));
    }

    private static LoanOfferDTO prescoreOfferDto(LoanApplicationRequestDTO dto, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO(dto, isInsuranceEnabled, isSalaryClient);
        loanOfferDTO.setRate(calculateRate(isInsuranceEnabled, isSalaryClient));
        loanOfferDTO.setTotalAmount(calculateTotalAmount(isInsuranceEnabled, dto));
        return loanOfferDTO;
    }

    private static  BigDecimal calculateTotalAmount(Boolean isInsuranceEnabled, LoanApplicationRequestDTO dto) {
        BigDecimal amount = dto.getAmount();
        if (isInsuranceEnabled) {
            return amount.add(baseInsuranceCost);
        } else {
            return amount;
        }
    }

    private static BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal finalRate = baseLoanRate;
        int rate = getInsuranceRate(isInsuranceEnabled) + getSalaryRate(isSalaryClient);
        BigDecimal rateBD = BigDecimal.valueOf(Math.abs(rate));
        if (rate < 0) {
            finalRate = finalRate.subtract(rateBD);
        } else {
            finalRate = finalRate.add(rateBD);
        }
        return finalRate;
    }

    private static int getInsuranceRate(Boolean b) {
        return b ? -insuranceRate : insuranceRate;
    }

    private static int getSalaryRate(Boolean b) {
        return b ? -salaryRate : salaryRate;
    }

    public static CreditDTO scoreLoan(ScoringDataDTO dto) {
        return calculateCredit(dto);
    }

    private static BigDecimal calculateRate(ScoringDataDTO dto) {
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
    private static CreditDTO calculateCredit(ScoringDataDTO dto) {
        BigDecimal rate = calculateRate(dto);
        CreditDTO creditDTO = CreditDTO.builder()
                .amount(dto.getAmount())
                .term(dto.getTerm())
                .rate(rate)
                .isInsuranceEnabled(dto.getIsInsuranceEnabled())
                .isSalaryClient(dto.getIsSalaryClient())
                .build();

        BigDecimal monthlyRate = creditDTO.getRate().divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
        BigDecimal amount = creditDTO.getAmount();
        BigDecimal monthlyPayment;
        Integer period = creditDTO.getTerm();

        BigDecimal sub1 = BigDecimal.ONE.add(monthlyRate).pow(period);
        BigDecimal sub = BigDecimal.ONE.divide(sub1, 10, RoundingMode.HALF_UP);

        BigDecimal divisor = BigDecimal.ONE.subtract(sub);
        monthlyPayment = amount.multiply(monthlyRate).divide(divisor, 10, RoundingMode.HALF_UP);
        creditDTO.setMonthlyPayment(monthlyPayment);

        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(period));
        creditDTO.setPsk(psk);

        List<PaymentScheduleElementDto> paymentScheduleElementList = new ArrayList<>(period);
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < period; i++) {
            PaymentScheduleElementDto e = PaymentScheduleElementDto.builder().build();
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

        creditDTO.setPaymentScheduleElementDtoList(paymentScheduleElementList);
        return creditDTO;
    }

}
