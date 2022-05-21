package ru.lsan.conveyor.service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.lsan.conveyor.dto.LoanApplicationRequestDTO;
import ru.lsan.conveyor.dto.LoanOfferDTO;

@Service
public class PrescoringService {

    @Value("${baseLoanRate}")
    private BigDecimal baseLoanRate;

    @Value("${baseInsuranceCost}")
    private BigDecimal baseInsuranceCost;

    public List<LoanOfferDTO> prescoreLoan(LoanApplicationRequestDTO dto) {
        List<LoanOfferDTO> loanOfferDTOList = new ArrayList<>(4);

        LoanOfferDTO insuranceEnabledSalaryClientDto = new LoanOfferDTO(dto, true, true);
        insuranceEnabledSalaryClientDto.setRate(baseLoanRate.subtract(BigDecimal.valueOf(3)));
        insuranceEnabledSalaryClientDto.setTotalAmount(dto.getAmount().add(baseInsuranceCost));
        loanOfferDTOList.add(insuranceEnabledSalaryClientDto);

        LoanOfferDTO insuranceEnabledNotSalaryClientDto = new LoanOfferDTO(dto, true, false);
        insuranceEnabledNotSalaryClientDto.setRate(baseLoanRate.subtract(BigDecimal.valueOf(1)));
        insuranceEnabledNotSalaryClientDto.setTotalAmount(dto.getAmount().add(baseInsuranceCost));
        loanOfferDTOList.add(insuranceEnabledNotSalaryClientDto);

        LoanOfferDTO notInsuranceEnabledSalaryClientDto = new LoanOfferDTO(dto, false, true);
        notInsuranceEnabledSalaryClientDto.setRate(baseLoanRate.add(BigDecimal.valueOf(3)));
        notInsuranceEnabledSalaryClientDto.setTotalAmount(dto.getAmount());
        loanOfferDTOList.add(notInsuranceEnabledSalaryClientDto);

        LoanOfferDTO notInsuranceEnabledNotSalaryClientDto = new LoanOfferDTO(dto, false, false);
        notInsuranceEnabledNotSalaryClientDto.setRate(baseLoanRate.subtract(BigDecimal.valueOf(2)));
        notInsuranceEnabledNotSalaryClientDto.setTotalAmount(dto.getAmount().add(baseInsuranceCost));
        loanOfferDTOList.add(notInsuranceEnabledNotSalaryClientDto);

        loanOfferDTOList.sort(Comparator.comparing(LoanOfferDTO::getRate));

        return loanOfferDTOList;
    }

}
