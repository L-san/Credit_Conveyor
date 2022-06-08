package ru.lsan.conveyor.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lsan.conveyor.dto.LoanApplicationRequestDTO;
import ru.lsan.conveyor.exception.custom_exceptions.IncorrectRequestParametersException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassFieldsValidatorTest {

    @Test
    void validateAmount() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setAmount(BigDecimal.valueOf(9999));
            ClassFieldsValidator.validateAmount(dto.getAmount());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setAmount(BigDecimal.valueOf(-1));
            ClassFieldsValidator.validateAmount(dto.getAmount());
        });
    }

    @Test
    void validateTerm() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setTerm(3);
            ClassFieldsValidator.validateTerm(dto.getTerm());
        });
    }

    @Test
    void validateFirstName() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setFirstName("a");
            ClassFieldsValidator.validateFirstName(dto.getFirstName());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            ClassFieldsValidator.validateFirstName(dto.getFirstName());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setFirstName("352325_");
            ClassFieldsValidator.validateFirstName(dto.getFirstName());
        });
    }

    @Test
    void validateLastName() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setLastName("a");
            ClassFieldsValidator.validateLastName(dto.getLastName());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setLastName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            ClassFieldsValidator.validateLastName(dto.getLastName());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setLastName("352325_");
            ClassFieldsValidator.validateLastName(dto.getLastName());
        });
    }

    @Test
    void validateMiddleName() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setMiddleName("a");
            ClassFieldsValidator.validateMiddleName(dto.getMiddleName());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setMiddleName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            ClassFieldsValidator.validateMiddleName(dto.getMiddleName());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setMiddleName("352325_");
            ClassFieldsValidator.validateMiddleName(dto.getMiddleName());
        });
    }

    @Test
    void validateEmail() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setEmail("abracadabra@");
            ClassFieldsValidator.validateEmail(dto.getEmail());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setEmail("@@@@.com");
            ClassFieldsValidator.validateEmail(dto.getEmail());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setEmail("@me.com");
            ClassFieldsValidator.validateEmail(dto.getEmail());
        });
    }

    @Test
    void validateBirthdate() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setBirthdate(LocalDate.now().plusYears(10));
            ClassFieldsValidator.validateBirthdate(dto.getBirthdate());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setBirthdate(LocalDate.now().plusYears(17).plusDays(364));
            ClassFieldsValidator.validateBirthdate(dto.getBirthdate());
        });
    }

    @Test
    void validatePassportSeries() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setPassportSeries("12455");
            ClassFieldsValidator.validatePassportSeries(dto.getPassportSeries());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setPassportSeries("k24o");
            ClassFieldsValidator.validatePassportSeries(dto.getPassportSeries());
        });
    }

    @Test
    void validatePassportNumber() {
        LoanApplicationRequestDTO dto = LoanApplicationRequestDTO.builder().build();
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setPassportSeries("12455555");
            ClassFieldsValidator.validatePassportSeries(dto.getPassportSeries());
        });
        assertThrows(IncorrectRequestParametersException.class, () -> {
            dto.setPassportSeries("k24oлд");
            ClassFieldsValidator.validatePassportSeries(dto.getPassportSeries());
        });
    }
}