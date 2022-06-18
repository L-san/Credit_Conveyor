package ru.lsan.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import ru.lsan.deal.database.entity.client.PassportEntity;
import ru.lsan.deal.database.service.application.ApplicationService;
import ru.lsan.deal.database.service.client.ClientService;
import ru.lsan.deal.database.service.client.PassportService;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;
import ru.lsan.deal.dto.LoanOfferDTO;
import ru.lsan.deal.feign.ConveyorClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class DealControllerTest {

    private final BigDecimal baseLoanRate = BigDecimal.TEN;
    private final BigDecimal baseInsuranceCost = BigDecimal.valueOf(100000);
    private final int insuranceRate = 1;
    private final int salaryRate = 1;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ConveyorClient conveyorClient;

    @Test
    void application() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO(
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
        String response = "oops";
        ResponseEntity<Optional<List<LoanOfferDTO>>> pLoan = prescoreLoan(loanApplicationRequestDTO);
        Mockito.when(conveyorClient.offers(loanApplicationRequestDTO)).thenReturn(pLoan);
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.post("/deal/application")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(loanApplicationRequestDTO)))
                    .andReturn().getResponse().getContentAsString();
            assertEquals(asJsonString(pLoan.getBody().get()),response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertNotEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    void offer() {
    }

    @Test
    void calculate() {
    }

    public static <T> String asJsonString(T t) {
        try {
            return new ObjectMapper().writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<Optional<List<LoanOfferDTO>>> prescoreLoan(LoanApplicationRequestDTO dto) {
        List<LoanOfferDTO> loanOfferDTOList = new ArrayList<>(4);

        LoanOfferDTO insuranceEnabledSalaryClientDto =  prescoreOfferDto(dto, true, true);
        loanOfferDTOList.add(insuranceEnabledSalaryClientDto);

        LoanOfferDTO insuranceEnabledNotSalaryClientDto =  prescoreOfferDto(dto, true, false);
        loanOfferDTOList.add(insuranceEnabledNotSalaryClientDto);

        LoanOfferDTO notInsuranceEnabledSalaryClientDto =  prescoreOfferDto(dto, false, true);
        loanOfferDTOList.add(notInsuranceEnabledSalaryClientDto);

        LoanOfferDTO notInsuranceEnabledNotSalaryClientDto =  prescoreOfferDto(dto, false, false);
        loanOfferDTOList.add(notInsuranceEnabledNotSalaryClientDto);

        loanOfferDTOList.sort(Comparator.comparing(LoanOfferDTO::getRate));
        Optional<List<LoanOfferDTO>> op = Optional.of(loanOfferDTOList);
        return ResponseEntity.of(Optional.of(op));
    }

    private LoanOfferDTO prescoreOfferDto(LoanApplicationRequestDTO dto, Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO(dto, isInsuranceEnabled, isSalaryClient);
        loanOfferDTO.setRate(calculateRate(isInsuranceEnabled,isSalaryClient));
        loanOfferDTO.setTotalAmount(calculateTotalAmount(isInsuranceEnabled,dto));
        return loanOfferDTO;
    }

    private BigDecimal calculateTotalAmount(Boolean isInsuranceEnabled, LoanApplicationRequestDTO dto) {
        BigDecimal amount = dto.getAmount();
        if (isInsuranceEnabled) {
            return amount.add(baseInsuranceCost);
        } else {
            return amount;
        }
    }

    private BigDecimal calculateRate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal finalRate = baseLoanRate;
        int rate = getInsuranceRate(isInsuranceEnabled)+ getSalaryRate(isSalaryClient);
        BigDecimal rateBD = BigDecimal.valueOf(Math.abs(rate));
        if (rate < 0) {
            finalRate = finalRate.subtract(rateBD);
        } else {
            finalRate = finalRate.add(rateBD);
        }
        return finalRate;
    }

    private int getInsuranceRate(Boolean b) {
        return b ? -insuranceRate : insuranceRate;
    }

    private int getSalaryRate(Boolean b) {
        return b ? -salaryRate : salaryRate;
    }


}