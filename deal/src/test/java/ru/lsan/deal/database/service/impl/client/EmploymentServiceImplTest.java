package ru.lsan.deal.database.service.impl.client;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.entity.client.EmploymentEntity;
import ru.lsan.deal.database.service.client.ClientService;
import ru.lsan.deal.database.service.client.EmploymentService;
import ru.lsan.deal.dto.EmploymentDTO;
import ru.lsan.deal.dto.FinishRegistrationRequestDTO;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;
import ru.lsan.deal.enums.EmploymentStatusEnum;
import ru.lsan.deal.enums.GenderEnum;
import ru.lsan.deal.enums.MaritalStatusEnum;
import ru.lsan.deal.enums.PositionEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@RequiredArgsConstructor
class EmploymentServiceImplTest {

    private final EmploymentService employmentService;
    private final ClientService clientService;

    EmploymentDTO employment = EmploymentDTO.builder()
            .employmentStatus(EmploymentStatusEnum.EMPLOYED)
            .salary(BigDecimal.ONE)
            .position(PositionEnum.CEO)
            .workExperienceTotal(12)
            .workExperienceCurrent(12)
            .build();

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

    private FinishRegistrationRequestDTO finishRegistrationRequestDTO = FinishRegistrationRequestDTO.builder()
            .gender(GenderEnum.MALE)
            .maritalStatus(MaritalStatusEnum.DIVORCED)
            .dependentAmount(2)
            .passportIssueBranch("ooo")
            .passportIssueDate(LocalDate.now())
            .employment(employment)
            .account("132323")
            .build();

    @Test
    void test1() {
        LoanApplicationRequestDTO dto = getLoanApplicationRequestDTO();
        ClientEntity clientEntity = clientService.create(dto);
        EmploymentEntity employmentEntity = employmentService.create(employment, clientEntity);
        ClientEntity clientEntity1 = clientService.update(clientEntity,finishRegistrationRequestDTO);
        assertNotNull(clientEntity);
        assertNotNull(clientEntity1);
        assertNotNull(employmentEntity);
    }
}