package ru.lsan.conveyor.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditDTOTest {

    private CreditDTO getDto() {
        return new CreditDTO(BigDecimal.TEN,
                0,
                BigDecimal.ZERO,
                BigDecimal.TEN,
                BigDecimal.ONE,
                Boolean.FALSE,
                Boolean.FALSE,
                new ArrayList<>());
    }

    @Test
    void getAmount() {
        CreditDTO dto = getDto();
        assertEquals(BigDecimal.TEN, dto.getAmount());
    }

    @Test
    void getTerm() {
        CreditDTO dto = getDto();
        assertEquals(0, dto.getTerm());
    }

    @Test
    void getMonthlyPayment() {
        CreditDTO dto = getDto();
        assertEquals(BigDecimal.ZERO, dto.getMonthlyPayment());
    }

    @Test
    void getRate() {
        CreditDTO dto = getDto();
        assertEquals(BigDecimal.TEN, dto.getRate());
    }

    @Test
    void getPsk() {
        CreditDTO dto = getDto();
        assertEquals(BigDecimal.ONE, dto.getPsk());
    }

    @Test
    void getIsInsuranceEnabled() {
        CreditDTO dto = getDto();
        assertEquals(Boolean.FALSE, dto.getIsInsuranceEnabled());
    }

    @Test
    void getIsSalaryClient() {
        CreditDTO dto = getDto();
        assertEquals(Boolean.FALSE, dto.getIsSalaryClient());
    }

    @Test
    void getPaymentScheduleElementList() {
        CreditDTO dto = getDto();
        assertNotNull(dto.getPaymentScheduleElementList());
    }

    @Test
    void setAmount() {
        CreditDTO dto = getDto();
        dto.setAmount(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, dto.getAmount());
    }

    @Test
    void setTerm() {
        CreditDTO dto = getDto();
        dto.setTerm(1);
        assertEquals(1, dto.getTerm());
    }

    @Test
    void setMonthlyPayment() {
        CreditDTO dto = getDto();
        dto.setMonthlyPayment(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, dto.getMonthlyPayment());
    }

    @Test
    void setRate() {
        CreditDTO dto = getDto();
        dto.setRate(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, dto.getRate());
    }

    @Test
    void setPsk() {
        CreditDTO dto = getDto();
        dto.setPsk(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, dto.getPsk());
    }

    @Test
    void setIsInsuranceEnabled() {
        CreditDTO dto = getDto();
        dto.setIsInsuranceEnabled(Boolean.TRUE);
        assertEquals(Boolean.TRUE, dto.getIsInsuranceEnabled());
    }

    @Test
    void setIsSalaryClient() {
        CreditDTO dto = getDto();
        dto.setIsSalaryClient(Boolean.TRUE);
        assertEquals(Boolean.TRUE, dto.getIsSalaryClient());
    }

    @Test
    void setPaymentScheduleElementList() {
        CreditDTO dto = getDto();
        dto.setPaymentScheduleElementList(new ArrayList<>());
        assertNotNull(dto.getPaymentScheduleElementList());
    }
}