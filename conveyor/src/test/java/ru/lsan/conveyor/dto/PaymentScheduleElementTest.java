package ru.lsan.conveyor.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentScheduleElementTest {

    private PaymentScheduleElement getElement() {
        return new PaymentScheduleElement(1,
                LocalDate.of(2000, 1, 1),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO);
    }

    @Test
    void getNumber() {
        PaymentScheduleElement element = getElement();
        assertEquals(1, element.getNumber());
    }

    @Test
    void getDate() {
        PaymentScheduleElement element = getElement();
        assertEquals(LocalDate.of(2000, 1, 1).getYear(), element.getDate().getYear());
    }

    @Test
    void getTotalPayment() {
        PaymentScheduleElement element = getElement();
        assertEquals(BigDecimal.ZERO, element.getTotalPayment());
    }

    @Test
    void getInterestPayment() {
        PaymentScheduleElement element = getElement();
        assertEquals(BigDecimal.ZERO, element.getInterestPayment());
    }

    @Test
    void getDebtPayment() {
        PaymentScheduleElement element = getElement();
        assertEquals(BigDecimal.ZERO, element.getDebtPayment());
    }

    @Test
    void getRemainingDebt() {
        PaymentScheduleElement element = getElement();
        assertEquals(BigDecimal.ZERO, element.getRemainingDebt());
    }

    @Test
    void setNumber() {
        PaymentScheduleElement element = getElement();
        element.setNumber(2);
        assertEquals(2, element.getNumber());
    }

    @Test
    void setDate() {
        PaymentScheduleElement element = getElement();
        element.setDate(LocalDate.of(2022, 1, 1));
        assertEquals(2022, element.getDate().getYear());
    }

    @Test
    void setTotalPayment() {
        PaymentScheduleElement element = getElement();
        element.setTotalPayment(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, element.getTotalPayment());
    }

    @Test
    void setInterestPayment() {
        PaymentScheduleElement element = getElement();
        element.setInterestPayment(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, element.getInterestPayment());
    }

    @Test
    void setDebtPayment() {
        PaymentScheduleElement element = getElement();
        element.setDebtPayment(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, element.getDebtPayment());
    }

    @Test
    void setRemainingDebt() {
        PaymentScheduleElement element = getElement();
        element.setRemainingDebt(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, element.getRemainingDebt());
    }
}