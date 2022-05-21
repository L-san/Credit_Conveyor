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
    void offers() throws Exception {
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

        String expectedResponse1 = "{\"applicationId\":null,\"requestedAmount\":100000,\"totalAmount\":200000,\"term\":12,\"monthlyPayment\":null,\"rate\":7,\"isInsuranceEnabled\":true,\"isSalaryClient\":true}";
        String expectedResponse2 = "{\"applicationId\":null,\"requestedAmount\":100000,\"totalAmount\":200000,\"term\":12,\"monthlyPayment\":null,\"rate\":8,\"isInsuranceEnabled\":false,\"isSalaryClient\":false}";
        String expectedResponse3 = "{\"applicationId\":null,\"requestedAmount\":100000,\"totalAmount\":200000,\"term\":12,\"monthlyPayment\":null,\"rate\":9,\"isInsuranceEnabled\":true,\"isSalaryClient\":false}";
        String expectedResponse4 = "{\"applicationId\":null,\"requestedAmount\":100000,\"totalAmount\":100000,\"term\":12,\"monthlyPayment\":null,\"rate\":13,\"isInsuranceEnabled\":false,\"isSalaryClient\":true}";

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/conveyor/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(loanApplicationRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();

        assertTrue(response.contains(expectedResponse1));
        assertTrue(response.contains(expectedResponse2));
        assertTrue(response.contains(expectedResponse3));
        assertTrue(response.contains(expectedResponse4));

    }

    @Test
    void calculation() throws Exception {
        ScoringDataDTO dto = new ScoringDataDTO(
                BigDecimal.valueOf(100000),
                12,
                "Ivan",
                "Ivanov",
                "Ivanovich",
                GenderEnum.MALE,
                LocalDate.of(1986, 1, 1),
                "1234",
                "123456",
                LocalDate.of(1986, 1, 1),
                "branch",
                MaritalStatusEnum.SINGLE,
                0,
                new EmploymentDTO(EmploymentStatusEnum.EMPLOYED, "132414", BigDecimal.valueOf(200000), PositionEnum.CEO, 12, 12),
                "acc",
                false,
                true
        );

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.post("/conveyor/calculation")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(dto)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);

        String compare = "{\"amount\":100000,\"term\":12,\"monthlyPayment\":8652.6746054690,\"rate\":7,\"psk\":103832.0952656280,\"isInsuranceEnabled\":false,\"isSalaryClient\":true";
        assertTrue(response.contains(compare));
    }

    public static <T> String asJsonString(T t) {
        try {
            return new ObjectMapper().writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}