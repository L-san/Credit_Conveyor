package ru.lsan.conveyor.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanOfferDTO {

    public LoanOfferDTO(LoanApplicationRequestDTO dto, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        this.requestedAmount = dto.getAmount();
        this.term = dto.getTerm();
        this.isSalaryClient = isSalaryClient;
        this.isInsuranceEnabled = isInsuranceEnabled;
    }

    private Long applicationId;

    private BigDecimal requestedAmount;

    private BigDecimal totalAmount;

    private Integer term;

    private BigDecimal monthlyPayment;

    private BigDecimal rate;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;

}
