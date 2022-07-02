package ru.lsan.deal.database.service.impl.credit;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lsan.deal.database.entity.credit.CreditEntity;
import ru.lsan.deal.database.entity.credit.PaymentScheduleEntity;
import ru.lsan.deal.database.service.credit.PaymentScheduleService;
import ru.lsan.deal.dto.CreditDTO;
import ru.lsan.deal.dto.PaymentScheduleElementDto;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RequiredArgsConstructor
class PaymentScheduleServiceImplTest {

    private final PaymentScheduleService paymentScheduleService;

    private final CreditEntity credit = CreditEntity.builder()
            .amount(BigDecimal.ONE)
            .term(12)
            .monthlyPayment(BigDecimal.ONE)
            .rate(BigDecimal.ONE)
            .psk(BigDecimal.ONE)
            .isInsuranceEnabled(true)
            .isSalaryClient(true)
            .build();

    PaymentScheduleElementDto dto = PaymentScheduleElementDto.builder()
            .number(1)
            .date(LocalDate.now())
            .totalPayment(BigDecimal.ONE)
            .interestPayment(BigDecimal.ONE)
            .debtPayment(BigDecimal.ONE)
            .remainingDebt(BigDecimal.ONE)
            .build();

    @Test
    void create() {
        PaymentScheduleEntity paymentSchedule = paymentScheduleService.create(dto,credit);
        assertNotNull(paymentSchedule);
    }
}