package ru.lsan.conveyor.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDTO {

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;

    private List<PaymentScheduleElement> paymentScheduleElementList;

    public CreditDTO(BigDecimal amount, Integer term, BigDecimal rate, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        this.amount = amount;
        this.term = term;
        this.rate = rate;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
    }

}
