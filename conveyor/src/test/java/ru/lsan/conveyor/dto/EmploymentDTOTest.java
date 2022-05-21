package ru.lsan.conveyor.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.lsan.conveyor.dto.enums.EmploymentStatusEnum;
import ru.lsan.conveyor.dto.enums.PositionEnum;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmploymentDTOTest {

    private EmploymentDTO getDto() {
        return new EmploymentDTO(EmploymentStatusEnum.SELF_EMPLOYED,
                "10000",
                BigDecimal.TEN,
                PositionEnum.TOP_MANAGER,
                12,
                12);
    }

    @Test
    void getEmploymentStatusEnum() {
        EmploymentDTO employmentDTO = getDto();
        assertEquals(EmploymentStatusEnum.SELF_EMPLOYED, employmentDTO.getEmploymentStatusEnum());
    }

    @Test
    void getEmployerINN() {
        EmploymentDTO employmentDTO = getDto();
        assertEquals("10000", employmentDTO.getEmployerINN());
    }

    @Test
    void getSalary() {
        EmploymentDTO employmentDTO = getDto();
        assertEquals(BigDecimal.TEN, employmentDTO.getSalary());
    }

    @Test
    void getPosition() {
        EmploymentDTO employmentDTO = getDto();
        assertEquals(PositionEnum.TOP_MANAGER, employmentDTO.getPosition());
    }

    @Test
    void getWorkExperienceTotal() {
        EmploymentDTO employmentDTO = getDto();
        assertEquals(12, employmentDTO.getWorkExperienceTotal());
    }

    @Test
    void getWorkExperienceCurrent() {
        EmploymentDTO employmentDTO = getDto();
        assertEquals(12, employmentDTO.getWorkExperienceCurrent());
    }

    @Test
    void setEmploymentStatusEnum() {
        EmploymentDTO employmentDTO = getDto();
        employmentDTO.setEmploymentStatusEnum(EmploymentStatusEnum.EMPLOYED);
        assertEquals(EmploymentStatusEnum.EMPLOYED, employmentDTO.getEmploymentStatusEnum());
    }

    @Test
    void setEmployerINN() {
        EmploymentDTO employmentDTO = getDto();
        employmentDTO.setEmployerINN("1234");
        assertEquals("1234", employmentDTO.getEmployerINN());
    }

    @Test
    void setSalary() {
        EmploymentDTO employmentDTO = getDto();
        employmentDTO.setSalary(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, employmentDTO.getSalary());
    }

    @Test
    void setPosition() {
        EmploymentDTO employmentDTO = getDto();
        employmentDTO.setPosition(PositionEnum.CEO);
        assertEquals(PositionEnum.CEO, employmentDTO.getPosition());
    }

    @Test
    void setWorkExperienceTotal() {
        EmploymentDTO employmentDTO = getDto();
        employmentDTO.setWorkExperienceTotal(1);
        assertEquals(1, employmentDTO.getWorkExperienceTotal());
    }

    @Test
    void setWorkExperienceCurrent() {
        EmploymentDTO employmentDTO = getDto();
        employmentDTO.setWorkExperienceCurrent(1);
        assertEquals(1, employmentDTO.getWorkExperienceCurrent());
    }
}