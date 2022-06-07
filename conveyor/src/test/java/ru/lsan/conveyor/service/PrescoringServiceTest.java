package ru.lsan.conveyor.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lsan.conveyor.dto.LoanApplicationRequestDTO;
import ru.lsan.conveyor.dto.LoanOfferDTO;
import ru.lsan.conveyor.exception.custom_exceptions.IncorrectRequestParametersException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PrescoringServiceTest {

    @Autowired
    private PrescoringService prescoringService;

    @Test
    void prescoreLoan() throws IncorrectRequestParametersException {
        LoanApplicationRequestDTO dto = new LoanApplicationRequestDTO();
        dto.setAmount(BigDecimal.valueOf(100000));
        dto.setTerm(12);
        dto.setFirstName("Petr");
        dto.setLastName("Petrov");
        dto.setMiddleName("Petrovich");
        dto.setEmail("petr1987@gmail.com");
        dto.setBirthdate(LocalDate.of(1987, 5, 27));
        dto.setPassportSeries("0000");
        dto.setPassportNumber("000000");

        List<LoanOfferDTO> loanOfferDTOList = prescoringService.prescoreLoan(dto);

        assertEquals(BigDecimal.valueOf(7), loanOfferDTOList.get(0).getRate());
        assertEquals(BigDecimal.valueOf(8), loanOfferDTOList.get(1).getRate());
        assertEquals(BigDecimal.valueOf(9), loanOfferDTOList.get(2).getRate());
        assertEquals(BigDecimal.valueOf(13), loanOfferDTOList.get(3).getRate());

        assertEquals(BigDecimal.valueOf(200000), loanOfferDTOList.get(0).getTotalAmount());
        assertEquals(BigDecimal.valueOf(200000), loanOfferDTOList.get(1).getTotalAmount());
        assertEquals(BigDecimal.valueOf(200000), loanOfferDTOList.get(2).getTotalAmount());
        assertEquals(BigDecimal.valueOf(100000), loanOfferDTOList.get(3).getTotalAmount());
    }

}