package ru.lsan.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.service.application.ApplicationService;
import ru.lsan.deal.dto.*;
import ru.lsan.deal.enums.*;
import ru.lsan.deal.feign.ConveyorClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class DealControllerTest {



    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConveyorClient conveyorClient;

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

    private ResponseEntity<Optional<List<LoanOfferDTO>>> performApplication() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = getLoanApplicationRequestDTO();
        ResponseEntity<Optional<List<LoanOfferDTO>>> pLoan = TestService.prescoreLoan(loanApplicationRequestDTO);
        Mockito.when(conveyorClient.offers(loanApplicationRequestDTO)).thenReturn(pLoan);
        try {
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/deal/application")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(loanApplicationRequestDTO)));
        } catch (Exception ignored) {
        }
        return pLoan;
    }

    @Test
    void test1() {
        ResponseEntity<Optional<List<LoanOfferDTO>>> pLoan = performApplication();
        LoanOfferDTO loanOffer = pLoan.getBody().get().get(0);
        Long id = loanOffer.getApplicationId();
        ApplicationEntity application = applicationService.findById(id);
        assertEquals(StatusEnum.PREAPPROVAL, application.getStatus());
    }

    @Test
    void test2() {
        LoanOfferDTO loanOffer;
        try {
            ResponseEntity<Optional<List<LoanOfferDTO>>> pLoan = performApplication();
            loanOffer = pLoan.getBody().get().get(0);
            Long id = loanOffer.getApplicationId();
            mockMvc.perform(
                            MockMvcRequestBuilders.put("/deal/offer")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(loanOffer)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
            assertEquals(StatusEnum.APPROVED, applicationService.findById(id).getStatus());
            assertEquals(asJsonString(loanOffer), asJsonString(applicationService.findById(id).getAppliedOffer()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertNotEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    void test3() {
        LoanOfferDTO loanOffer;
        String response = "";
        FinishRegistrationRequestDTO dto = FinishRegistrationRequestDTO.builder()
                .gender(GenderEnum.MALE)
                .maritalStatus(MaritalStatusEnum.DIVORCED)
                .passportIssueDate(LocalDate.now())
                .passportIssueBranch("this")
                .employment(EmploymentDTO.builder()
                        .employmentStatus(EmploymentStatusEnum.EMPLOYED)
                        .employerINN("132414")
                        .position(PositionEnum.CEO)
                        .salary(BigDecimal.valueOf(200000))
                        .workExperienceCurrent(12)
                        .workExperienceTotal(12)
                        .build())
                .account("133435")
                .build();


        try {
            ResponseEntity<Optional<List<LoanOfferDTO>>> pLoan = performApplication();
            loanOffer = pLoan.getBody().get().get(0);
            Long id = loanOffer.getApplicationId();

            ApplicationEntity application = applicationService.findById(id);
            ClientEntity client = application.getClient();
            ScoringDataDTO scoringDataDTO = ScoringDataDTO.from(application,client);
            ResponseEntity<Optional<CreditDTO>> pCredit = ResponseEntity.of(Optional.of(Optional.of(TestService.scoreLoan(scoringDataDTO))));
            Mockito.when(conveyorClient.calculation(scoringDataDTO)).thenReturn(pCredit);

            mockMvc.perform(
                            MockMvcRequestBuilders.put("/deal/calculate/"+id)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(dto)))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            assertEquals(StatusEnum.CC_APPROVED, applicationService.findById(id).getStatus());
            assertTrue(asJsonString(applicationService.findById(id).getCredit()).contains(asJsonString(pCredit.getBody().get())));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertNotEquals(RuntimeException.class, e.getClass());
        }
    }

    public static <T> String asJsonString(T t) {
        try {
            return new ObjectMapper().writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}