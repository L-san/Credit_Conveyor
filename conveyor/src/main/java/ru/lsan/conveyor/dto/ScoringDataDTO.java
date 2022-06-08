package ru.lsan.conveyor.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.lsan.conveyor.dto.enums.GenderEnum;
import ru.lsan.conveyor.dto.enums.MaritalStatusEnum;

@Data
@Builder
public class ScoringDataDTO {

    private BigDecimal amount;

    private Integer term;

    private String firstName;

    private String lastName;

    private String middleName;

    private GenderEnum gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

    private String passportSeries;

    private String passportNumber;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate passportIssueDate;

    private String passportIssueBranch;

    private MaritalStatusEnum maritalStatus;

    private Integer dependentAmount;

    private EmploymentDTO employment;

    private String account;

    private Boolean isInsuranceEnabled;

    private Boolean isSalaryClient;

}
