package ru.lsan.conveyor.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lsan.conveyor.dto.enums.GenderEnum;
import ru.lsan.conveyor.dto.enums.MaritalStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoringDataDTOTest {

    private ScoringDataDTO getDto() {
        return new ScoringDataDTO(BigDecimal.ZERO,
                0,
                "a",
                "a",
                "a",
                GenderEnum.MALE,
                LocalDate.of(2000, 01, 01),
                "1234",
                "123456",
                LocalDate.of(2000, 01, 01),
                "a",
                MaritalStatusEnum.SINGLE,
                0,
                EmploymentDTO.builder().build(),
                "a",
                Boolean.FALSE,
                Boolean.FALSE);
    }

    @Test
    void getAmount() {
        ScoringDataDTO dto = getDto();
        assertEquals(BigDecimal.ZERO, dto.getAmount());
    }

    @Test
    void getTerm() {
        ScoringDataDTO dto = getDto();
        assertEquals(0, dto.getTerm());
    }

    @Test
    void getFirstName() {
        ScoringDataDTO dto = getDto();
        assertEquals("a", dto.getFirstName());
    }

    @Test
    void getLastName() {
        ScoringDataDTO dto = getDto();
        assertEquals("a", dto.getLastName());
    }

    @Test
    void getMiddleName() {
        ScoringDataDTO dto = getDto();
        assertEquals("a", dto.getMiddleName());
    }

    @Test
    void getGender() {
        ScoringDataDTO dto = getDto();
        assertEquals(GenderEnum.MALE, dto.getGender());
    }

    @Test
    void getBirthdate() {
        ScoringDataDTO dto = getDto();
        assertEquals(2000, dto.getBirthdate().getYear());
    }

    @Test
    void getPassportSeries() {
        ScoringDataDTO dto = getDto();
        assertEquals("1234", dto.getPassportSeries());
    }

    @Test
    void getPassportNumber() {
        ScoringDataDTO dto = getDto();
        assertEquals("123456", dto.getPassportNumber());
    }

    @Test
    void getPassportIssueDate() {
        ScoringDataDTO dto = getDto();
        assertEquals(2000, dto.getPassportIssueDate().getYear());
    }

    @Test
    void getPassportIssueBranch() {
        ScoringDataDTO dto = getDto();
        assertEquals("a", dto.getPassportIssueBranch());
    }

    @Test
    void getMaritalStatus() {
        ScoringDataDTO dto = getDto();
        assertEquals(MaritalStatusEnum.SINGLE, dto.getMaritalStatus());
    }

    @Test
    void getDependentAmount() {
        ScoringDataDTO dto = getDto();
        assertEquals(0, dto.getDependentAmount());
    }

    @Test
    void getEmployment() {
        ScoringDataDTO dto = getDto();
        assertNotNull(dto.getEmployment());
    }

    @Test
    void getAccount() {
        ScoringDataDTO dto = getDto();
        assertEquals("a", dto.getAccount());
    }

    @Test
    void getIsInsuranceEnabled() {
        ScoringDataDTO dto = getDto();
        assertFalse(dto.getIsInsuranceEnabled());
    }

    @Test
    void getIsSalaryClient() {
        ScoringDataDTO dto = getDto();
        assertFalse(dto.getIsSalaryClient());
    }

    @Test
    void setAmount() {
        ScoringDataDTO dto = getDto();
        dto.setAmount(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, dto.getAmount());
    }

    @Test
    void setTerm() {
        ScoringDataDTO dto = getDto();
        dto.setTerm(12);
        assertEquals(12, dto.getTerm());
    }

    @Test
    void setFirstName() {
        ScoringDataDTO dto = getDto();
        dto.setFirstName("asdf");
        assertEquals("asdf", dto.getFirstName());
    }

    @Test
    void setLastName() {
        ScoringDataDTO dto = getDto();
        dto.setLastName("asdf");
        assertEquals("asdf", dto.getLastName());
    }

    @Test
    void setMiddleName() {
        ScoringDataDTO dto = getDto();
        dto.setMiddleName("asdf");
        assertEquals("asdf", dto.getMiddleName());
    }

    @Test
    void setGender() {
        ScoringDataDTO dto = getDto();
        dto.setGender(GenderEnum.FEMALE);
        assertEquals(GenderEnum.FEMALE, dto.getGender());
    }

    @Test
    void setBirthdate() {
        ScoringDataDTO dto = getDto();
        dto.setBirthdate(LocalDate.of(2000, 1, 1));
        assertEquals(2000, getDto().getBirthdate().getYear());
    }

    @Test
    void setPassportSeries() {
        ScoringDataDTO dto = getDto();
        dto.setPassportSeries("0000");
        assertEquals("0000", dto.getPassportSeries());
    }

    @Test
    void setPassportNumber() {
        ScoringDataDTO dto = getDto();
        dto.setPassportNumber("000000");
        assertEquals("000000", dto.getPassportNumber());
    }

    @Test
    void setPassportIssueDate() {
        ScoringDataDTO dto = getDto();
        dto.setPassportIssueDate(LocalDate.of(2000, 1, 1));
        assertEquals(2000, dto.getPassportIssueDate().getYear());
    }

    @Test
    void setPassportIssueBranch() {
        ScoringDataDTO dto = getDto();
        dto.setPassportIssueBranch("br");
        assertEquals("br", dto.getPassportIssueBranch());
    }

    @Test
    void setMaritalStatus() {
        ScoringDataDTO dto = getDto();
        dto.setMaritalStatus(MaritalStatusEnum.WIDOWED);
        assertEquals(MaritalStatusEnum.WIDOWED, dto.getMaritalStatus());
    }

    @Test
    void setDependentAmount() {
        ScoringDataDTO dto = getDto();
        dto.setDependentAmount(12);
        assertEquals(12, dto.getDependentAmount());
    }

    @Test
    void setEmployment() {
        ScoringDataDTO dto = getDto();
        dto.setEmployment(EmploymentDTO.builder().build());
        assertNotNull(dto.getEmployment());
    }

    @Test
    void setAccount() {
        ScoringDataDTO dto = getDto();
        dto.setAccount("abcd");
        assertEquals("abcd", dto.getAccount());
    }

    @Test
    void setIsInsuranceEnabled() {
        ScoringDataDTO dto = getDto();
        dto.setIsInsuranceEnabled(true);
        assertTrue(dto.getIsInsuranceEnabled());
    }

    @Test
    void setIsSalaryClient() {
        ScoringDataDTO dto = getDto();
        dto.setIsSalaryClient(true);
        assertTrue(dto.getIsSalaryClient());
    }

}