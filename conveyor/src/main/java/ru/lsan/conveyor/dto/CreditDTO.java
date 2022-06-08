package ru.lsan.conveyor.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditDTO {

    private BigDecimal amount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private BigDecimal psk;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;

    private List<PaymentScheduleElement> paymentScheduleElementList;

}
