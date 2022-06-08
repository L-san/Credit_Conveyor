package ru.lsan.conveyor.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanOfferDTOTest {

    private LoanOfferDTO getDto() {
        return LoanOfferDTO.builder()
                .applicationId(123L)
                .requestedAmount(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)
                .term(0)
                .monthlyPayment(BigDecimal.ZERO)
                .rate(BigDecimal.ZERO)
                .isInsuranceEnabled(Boolean.FALSE)
                .isSalaryClient(Boolean.FALSE).
                build();
    }

    @Test
    void getApplicationId() {
        LoanOfferDTO dto = getDto();
        assertEquals(123L, dto.getApplicationId());
    }

    @Test
    void getRequestedAmount() {
        LoanOfferDTO dto = getDto();
        assertEquals(BigDecimal.ZERO, dto.getRequestedAmount());
    }

    @Test
    void getTotalAmount() {
        LoanOfferDTO dto = getDto();
        assertEquals(BigDecimal.ZERO, dto.getTotalAmount());
    }

    @Test
    void getTerm() {
        LoanOfferDTO dto = getDto();
        assertEquals(0, dto.getTerm());
    }

    @Test
    void getMonthlyPayment() {
        LoanOfferDTO dto = getDto();
        assertEquals(BigDecimal.ZERO, dto.getMonthlyPayment());
    }

    @Test
    void getRate() {
        LoanOfferDTO dto = getDto();
        assertEquals(BigDecimal.ZERO, dto.getRate());
    }

    @Test
    void getIsInsuranceEnabled() {
        LoanOfferDTO dto = getDto();
        assertFalse(dto.getIsInsuranceEnabled());
    }

    @Test
    void getIsSalaryClient() {
        LoanOfferDTO dto = getDto();
        assertFalse(dto.getIsSalaryClient());
    }

    @Test
    void setApplicationId() {
        LoanOfferDTO dto = getDto();
        dto.setApplicationId(567L);
        assertEquals(567L, dto.getApplicationId());
    }

    @Test
    void setRequestedAmount() {
        LoanOfferDTO dto = getDto();
        dto.setRequestedAmount(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, dto.getRequestedAmount());
    }

    @Test
    void setTotalAmount() {
        LoanOfferDTO dto = getDto();
        dto.setTotalAmount(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, dto.getTotalAmount());
    }

    @Test
    void setTerm() {
        LoanOfferDTO dto = getDto();
        dto.setTerm(12);
        assertEquals(12, dto.getTerm());
    }

    @Test
    void setMonthlyPayment() {
        LoanOfferDTO dto = getDto();
        dto.setMonthlyPayment(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, dto.getMonthlyPayment());
    }

    @Test
    void setRate() {
        LoanOfferDTO dto = getDto();
        dto.setRate(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, dto.getRate());
    }

    @Test
    void setIsInsuranceEnabled() {
        LoanOfferDTO dto = getDto();
        dto.setIsInsuranceEnabled(true);
        assertTrue(dto.getIsInsuranceEnabled());
    }

    @Test
    void setIsSalaryClient() {
        LoanOfferDTO dto = getDto();
        dto.setIsSalaryClient(true);
        assertTrue(dto.getIsSalaryClient());
    }

}