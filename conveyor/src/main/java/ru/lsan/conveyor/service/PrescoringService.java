package ru.lsan.conveyor.service;

import java.math.BigDecimal;
import java.util.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lsan.conveyor.dto.LoanApplicationRequestDTO;
import ru.lsan.conveyor.dto.LoanOfferDTO;

@Log4j2
@Service
public class PrescoringService {

    @Value("${baseLoanRate}")
    private BigDecimal baseLoanRate;

    @Value("${baseInsuranceCost}")
    private BigDecimal baseInsuranceCost;

    @Value("${insuranceRate}")
    private int insuranceRate;

    @Value("${salaryRate}")
    private int salaryRate;

    public List<LoanOfferDTO> prescoreLoan(LoanApplicationRequestDTO dto) {
        List<LoanOfferDTO> loanOfferDTOList = new ArrayList<>(4);

        LoanOfferDTO insuranceEnabledSalaryClientDto =  prescoreOfferDto(dto, true, true);
        loanOfferDTOList.add(insuranceEnabledSalaryClientDto);

        LoanOfferDTO insuranceEnabledNotSalaryClientDto =  prescoreOfferDto(dto, true, false);
        loanOfferDTOList.add(insuranceEnabledNotSalaryClientDto);

        LoanOfferDTO notInsuranceEnabledSalaryClientDto =  prescoreOfferDto(dto, false, true);
        loanOfferDTOList.add(notInsuranceEnabledSalaryClientDto);

        LoanOfferDTO notInsuranceEnabledNotSalaryClientDto =  prescoreOfferDto(dto, false, false);
        loanOfferDTOList.add(notInsuranceEnabledNotSalaryClientDto);

        loanOfferDTOList.sort(Comparator.comparing(LoanOfferDTO::getRate));
        log.info("loan prescored");
        return loanOfferDTOList;
    }

    private LoanOfferDTO prescoreOfferDto(LoanApplicationRequestDTO dto, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO(dto, isInsuranceEnabled, isSalaryClient);
        loanOfferDTO.setRate(calculateRate(isInsuranceEnabled,isSalaryClient));
        loanOfferDTO.setTotalAmount(calculateTotalAmount(isInsuranceEnabled,dto));
        return loanOfferDTO;
    }

    private BigDecimal calculateTotalAmount(Boolean isInsuranceEnabled, LoanApplicationRequestDTO dto) {
        BigDecimal amount = dto.getAmount();
        if (isInsuranceEnabled) {
            return amount.add(baseInsuranceCost);
        } else {
            return amount;
        }
    }

    private BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal finalRate = baseLoanRate;
        int rate = getInsuranceRate(isInsuranceEnabled)+ getSalaryRate(isSalaryClient);
        BigDecimal rateBD = BigDecimal.valueOf(Math.abs(rate));
        if (rate < 0) {
            finalRate = finalRate.subtract(rateBD);
        } else {
            finalRate = finalRate.add(rateBD);
        }
        return finalRate;
    }

    private int getInsuranceRate(Boolean b) {
        return b ? -insuranceRate : insuranceRate;
    }

    private int getSalaryRate(Boolean b) {
        return b ? -salaryRate : salaryRate;
    }

}
