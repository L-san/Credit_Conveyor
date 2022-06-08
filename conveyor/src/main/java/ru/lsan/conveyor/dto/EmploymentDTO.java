package ru.lsan.conveyor.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import ru.lsan.conveyor.dto.enums.EmploymentStatusEnum;
import ru.lsan.conveyor.dto.enums.PositionEnum;

@Data
@Builder
public class EmploymentDTO {

    private EmploymentStatusEnum employmentStatusEnum;

    private String employerINN;

    private BigDecimal salary;

    private PositionEnum position;

    private Integer workExperienceTotal;

    private Integer workExperienceCurrent;

}
