package ru.lsan.conveyor.exception;

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
import ru.lsan.conveyor.exception.custom_exceptions.IncorrectRequestParametersException;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    private ScoringDataDTO getDto() {
        ScoringDataDTO dto = new ScoringDataDTO();
        dto.getNewDto(
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
                EmploymentDTO.builder()
                        .employmentStatusEnum(EmploymentStatusEnum.EMPLOYED)
                        .employerINN("132414")
                        .position(PositionEnum.CEO)
                        .salary(BigDecimal.valueOf(200000))
                        .workExperienceCurrent(12)
                        .workExperienceTotal(12)
                        .build(),
                "acc",
                false,
                true
        );
        return dto;
    }

    @Test
    void handleLoanDenialException() {
        ScoringDataDTO dto1 = getDto();
        dto1.setEmployment(EmploymentDTO.builder().employmentStatusEnum(EmploymentStatusEnum.UNEMPLOYED).build());
        ScoringDataDTO dto2 = getDto();
        dto2.setAmount(BigDecimal.valueOf(10000000));
        ScoringDataDTO dto3 = getDto();
        dto3.setBirthdate(LocalDate.of(1900, 01, 01));
        ScoringDataDTO dto4 = getDto();
        dto4.getEmployment().setWorkExperienceCurrent(2);
        dto4.getEmployment().setWorkExperienceTotal(2);

        String fault = "Loan is denied";
        String response1, response2, response3, response4;
        try {
            response1 = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/calculation")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(dto1)))
                    .andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getResponse().getContentAsString();
            response2 = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/calculation")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(dto2)))
                    .andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getResponse().getContentAsString();
            response3 = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/calculation")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(dto3)))
                    .andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getResponse().getContentAsString();
            response4 = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/calculation")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(dto4)))
                    .andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getResponse().getContentAsString();
            assertTrue(response1.contains(fault));
            assertTrue(response2.contains(fault));
            assertTrue(response3.contains(fault));
            assertTrue(response4.contains(fault));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertNotEquals(RuntimeException.class, e.getClass());
        }

    }

    @Test
    void handleIncorrectRequestParametersException() {
        LoanApplicationRequestDTO loanApplicationRequestDTO = new LoanApplicationRequestDTO(
                BigDecimal.valueOf(100000),
                12,
                "Ivan",
                "Ivanovich",
                "Ivanov",
                "gmail.com",
                LocalDate.of(1986, 1, 1),
                "1234",
                "123456"
        );
        String fault1 = "Not an email format!";


        ScoringDataDTO dto = getDto();
        dto.setPassportNumber("1234");
        String fault2 = "Wrong passport number format";

        String response1, response2;

        try {
            response1 = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/offers")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(loanApplicationRequestDTO)))
                    .andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getResponse().getContentAsString();
            response2 = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/calculation")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(asJsonString(dto)))
                    .andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getResponse().getContentAsString();
            assertTrue(response1.contains(fault1));
            assertTrue(response2.contains(fault2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertNotEquals(RuntimeException.class, e.getClass());
        }
    }

    @Test
    void handleHttpMessageNotReadableException() {
        String request = "{\n" +
                "  \"amount\": 100000,\n" +
                "  \"birthdate\": \"string\",\n" +
                "  \"email\": \"me@gmail.com\",\n" +
                "  \"firstName\": \"string\",\n" +
                "  \"lastName\": \"string\",\n" +
                "  \"middleName\": \"string\",\n" +
                "  \"passportNumber\": \"123456\",\n" +
                "  \"passportSeries\": \"1234\",\n" +
                "  \"term\": 12\n" +
                "}";

        String response1, response2;

        try {
            response1 = mockMvc.perform(
                            MockMvcRequestBuilders.post("/conveyor/offers")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(request))
                    .andExpect(MockMvcResultMatchers.status().is(400)).andReturn().getResponse().getContentAsString();
            System.out.println(response1);
            //assertTrue(response1.contains(fault1));
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