package ru.lsan.deal.database.service.impl.credit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lsan.deal.database.entity.credit.CreditEntity;
import ru.lsan.deal.database.service.credit.CreditService;
import ru.lsan.deal.dto.CreditDTO;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RequiredArgsConstructor
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CreditServiceImplTest {

    private final CreditService creditService;

    private final CreditDTO credit = CreditDTO.builder()
            .amount(BigDecimal.ONE)
            .term(12)
            .monthlyPayment(BigDecimal.ONE)
            .rate(BigDecimal.ONE)
            .psk(BigDecimal.ONE)
            .isInsuranceEnabled(true)
            .isSalaryClient(true)
            .build();

    @Test
    void test1() {
        CreditEntity credit1 = creditService.create(credit);
        CreditEntity credit2 = creditService.update(credit1);
        assertNotNull(credit1);
        assertEquals(credit1,credit2);
    }

    public static <T> String asJsonString(T t) {
        try {
            return new ObjectMapper().writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}