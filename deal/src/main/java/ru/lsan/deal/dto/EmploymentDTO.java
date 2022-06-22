package ru.lsan.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lsan.deal.enums.EmploymentStatusEnum;
import ru.lsan.deal.enums.PositionEnum;

import java.math.BigDecimal;

@Data
@Builder
public class EmploymentDTO {

    private EmploymentStatusEnum employmentStatus;

    private String employerINN;

    private BigDecimal salary;

    private PositionEnum position;

    private Integer workExperienceTotal;

    private Integer workExperienceCurrent;

}
