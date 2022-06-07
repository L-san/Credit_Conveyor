package ru.lsan.conveyor.service;

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

@Service
public class ScoringService {

    @Value("${baseLoanRate}")
    private BigDecimal baseLoanRate;

    private final int scale = 10;

    public CreditDTO scoreLoan(ScoringDataDTO dto) throws LoanDenialException {
        if (isLoanShouldBeDenied(dto)) throw new LoanDenialException("Loan is denied");
        BigDecimal rate = calculateRate(dto);
        CreditDTO creditDTO = new CreditDTO(dto.getAmount(), dto.getTerm(), rate, dto.getIsInsuranceEnabled(), dto.getIsSalaryClient());
        calculateCredit(creditDTO);
        return creditDTO;
    }

    public boolean isLoanShouldBeDenied(ScoringDataDTO dto) {
        BigDecimal twentySalaries = dto.getEmployment().getSalary().multiply(BigDecimal.valueOf(20));

        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthdate = dto.getBirthdate();
        LocalDate currentUserAge = currentDate.minusYears(userBirthdate.getYear()).minusDays(userBirthdate.getDayOfYear());

        if (dto.getEmployment().getEmploymentStatusEnum().equals(EmploymentStatusEnum.UNEMPLOYED)) {
            return true;
        } else if (dto.getAmount().compareTo(twentySalaries) > 0) {
            return true;
        } else if (currentUserAge.getYear() < 20 || currentUserAge.getYear() > 60) {
            return true;
        } else if (dto.getEmployment().getWorkExperienceTotal() < 12) {
            return true;
        } else if (dto.getEmployment().getWorkExperienceCurrent() < 3) {
            return true;
        } else {
            return false;
        }
    }

    public BigDecimal calculateRate(ScoringDataDTO dto) {
        BigDecimal rate = baseLoanRate;

        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthdate = dto.getBirthdate();
        LocalDate currentUserAge = currentDate.minusYears(userBirthdate.getYear()).minusDays(userBirthdate.getDayOfYear());

        if (dto.getEmployment().getEmploymentStatusEnum().equals(EmploymentStatusEnum.SELF_EMPLOYED)) {
            rate = rate.add(BigDecimal.valueOf(1));
        } else if (dto.getEmployment().getEmploymentStatusEnum().equals(EmploymentStatusEnum.BUSINESSMAN)) {
            rate = rate.subtract(BigDecimal.valueOf(2));
        } else if (dto.getEmployment().getPosition().equals(PositionEnum.TOP_MANAGER)) {
            rate = rate.subtract(BigDecimal.valueOf(4));
        } else if (dto.getMaritalStatus().equals(MaritalStatusEnum.MARRIED)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (dto.getMaritalStatus().equals(MaritalStatusEnum.DIVORCED)) {
            rate = rate.add(BigDecimal.valueOf(1));
        } else if (dto.getGender().equals(GenderEnum.FEMALE) && (currentUserAge.getYear() >= 35 && currentUserAge.getYear() <= 60)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (dto.getGender().equals(GenderEnum.MALE) && (currentUserAge.getYear() >= 30 && currentUserAge.getYear() <= 55)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (dto.getGender().equals(GenderEnum.NON_BINARY)) {
            rate = rate.add(BigDecimal.valueOf(3));
        }

        return rate;
    }

    //аннуитетные платежи
    public void calculateCredit(CreditDTO creditDTO) {
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
            PaymentScheduleElement e = new PaymentScheduleElement();
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
    }

}
