package ru.lsan.conveyor.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lsan.conveyor.exception.custom_exceptions.IncorrectRequestParametersException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanApplicationRequestDTOTest {

    @Test
    void setAmount() {
        LoanApplicationRequestDTO dto =LoanApplicationRequestDTO.builder().build();
        dto.setAmount(BigDecimal.valueOf(11000));
        assertEquals(BigDecimal.valueOf(11000), dto.getAmount());
    }

    @Test
    void setTerm() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        dto.setTerm(6);
        assertEquals(6, dto.getTerm());
    }

    @Test
    void setFirstName() throws IncorrectRequestParametersException {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        dto.setFirstName("Michael");
        assertEquals("Michael", dto.getFirstName());
    }

    @Test
    void setLastName() throws IncorrectRequestParametersException {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        dto.setLastName("Michaelov");
        assertEquals("Michaelov", dto.getLastName());
    }

    @Test
    void setMiddleName() throws IncorrectRequestParametersException {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        dto.setMiddleName("Michaelov");
        assertEquals("Michaelov", dto.getMiddleName());
    }

    @Test
    void setEmail() throws IncorrectRequestParametersException {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        dto.setEmail("privet@google.com");
        assertEquals("privet@google.com", dto.getEmail());
    }

    @Test
    void setBirthdate() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        dto.setBirthdate(LocalDate.of(2021, 1, 1).plusYears(18));
        assertEquals(2039, dto.getBirthdate().getYear());
    }

    @Test
    void setPassportSeries() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        dto.setPassportSeries("1234");
        assertEquals("1234", dto.getPassportSeries());
    }

    @Test
    void setPassportNumber() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        dto.setPassportNumber("123456");
        assertEquals("123456", dto.getPassportNumber());
    }

}