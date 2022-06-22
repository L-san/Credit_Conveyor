package ru.lsan.deal.database.service.application;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.service.client.ClientService;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;
import ru.lsan.deal.enums.StatusEnum;
import springfox.documentation.spi.service.DefaultsProviderPlugin;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class DatabaseServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private StatusHistoryService statusHistoryService;

    private LoanApplicationRequestDTO getLoanApplicationRequestDTO() {
        return new LoanApplicationRequestDTO(
                BigDecimal.valueOf(100000),
                12,
                "Ivan",
                "Ivanovich",
                "Ivanov",
                "me@gmail.com",
                LocalDate.of(1986, 1, 1),
                "1234",
                "123456"
        );
    }

    @Test
    void test1() {
        LoanApplicationRequestDTO dto = getLoanApplicationRequestDTO();
        ClientEntity client = clientService.create(dto);
        ApplicationEntity application = applicationService.create(client);
        assertNotNull(application);
        assertNotNull(client);
        String reference = "55";
        client.setAccount(reference);
        application.setSesCode(reference);

       // client = clientService.update(client);
        application = applicationService.update(application);

        //assertEquals(reference,client.getAccount());
        assertEquals(reference,application.getSesCode());

        applicationService.updateStatus(application,StatusEnum.CC_DENIED);
        assertEquals(StatusEnum.CC_DENIED,applicationService.findById(application.getId()).getStatus());

        assertNotNull(statusHistoryService.create(StatusEnum.PREAPPROVAL,application));
    }

    @Test
    void update() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void findById() {
    }
}