package ru.lsan.conveyor.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.lsan.conveyor.dto.EmploymentDTO;
import ru.lsan.conveyor.dto.LoanApplicationRequestDTO;
import ru.lsan.conveyor.dto.ScoringDataDTO;
import ru.lsan.conveyor.dto.enums.EmploymentStatusEnum;
import ru.lsan.conveyor.dto.enums.GenderEnum;
import ru.lsan.conveyor.dto.enums.MaritalStatusEnum;
import ru.lsan.conveyor.dto.enums.PositionEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ConveyorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void offers(){
        LoanApplicationRequestDTO loanApplicationRequestDTO =
                LoanApplicationRequestDTO.builder()
                        .amount(BigDecimal.valueOf(100000))
                        .term(12)
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .middleName("Ivanovich")
                        .email("me@gmail.com")
                        .birthdate(LocalDate.of(1986, 1, 1))
                        .passportSeries("1234")
                        .passportNumber("123456")
                        .build();

        String expectedResponse1 = "{\"applicationId\":null,\"requestedAmount\":100000,\"totalAmount\":200000,\"term\":12,\"monthlyPayment\":null,\"rate\":7,\"isInsuranceEnabled\":true,\"isSalaryClient\":true}";
        String expectedResponse2 = "{\"applicationId\":null,\"requestedAmount\":100000,\"totalAmount\":200000,\"term\":12,\"monthlyPayment\":null,\"rate\":8,\"isInsuranceEnabled\":false,\"isSalaryClient\":false}";
        String expectedResponse3 = "{\"applicationId\":null,\"requestedAmount\":100000,\"totalAmount\":200000,\"term\":12,\"monthlyPayment\":null,\"rate\":9,\"isInsuranceEnabled\":true,\"isSalaryClient\":false}";
        String expectedResponse4 = "{\"applicationId\":null,\"requestedAmount\":100000,\"totalAmount\":100000,\"term\":12,\"monthlyPayment\":null,\"rate\":13,\"isInsuranceEnabled\":false,\"isSalaryClient\":true}";

        String response;

        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/offers")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(loanApplicationRequestDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
            assertTrue(response.contains(expectedResponse1));
            assertTrue(response.contains(expectedResponse2));
            assertTrue(response.contains(expectedResponse3));
            assertTrue(response.contains(expectedResponse4));
        } catch (Exception e) {
            assertEquals(Exception.class, e.getClass());
        }
    }

    @Test
    void calculation() {

        ScoringDataDTO dto = ScoringDataDTO.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(12)
                .firstName("Ivan")
                .lastName("Ivanovich")
                .middleName("Ivanov")
                .gender(GenderEnum.MALE)
                .birthdate(LocalDate.of(1986, 1, 1))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.of(1986, 1, 1))
                .passportIssueBranch("branch")
                .maritalStatus(MaritalStatusEnum.SINGLE)
                .dependentAmount(0)
                .employment(EmploymentDTO.builder()
                                .employmentStatusEnum(EmploymentStatusEnum.EMPLOYED)
                                .employerINN("132414")
                                .position(PositionEnum.CEO)
                                .salary(BigDecimal.valueOf(200000))
                                .workExperienceCurrent(12)
                                .workExperienceTotal(12)
                                .build())
                .account("acc")
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();
        String compare = "{\"amount\":100000,\"term\":12,\"monthlyPayment\":8652.6746054690,\"rate\":7,\"psk\":103832.0952656280,\"isInsuranceEnabled\":false,\"isSalaryClient\":true";
        String response;
        try {
            response = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/calculation")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(dto)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
            assertTrue(response.contains(compare));
        } catch (Exception e) {
            assertEquals(Exception.class, e.getClass());
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