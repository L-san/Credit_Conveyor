package ru.lsan.deal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.enums.GenderEnum;
import ru.lsan.deal.enums.MaritalStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    public static ScoringDataDTO from(ApplicationEntity application, ClientEntity client) {
        EmploymentDTO employmentDTO = EmploymentDTO.builder()
                .employmentStatusEnum(client.getEmployment().getEmploymentStatus())
                .salary(client.getEmployment().getSalary())
                .position(client.getEmployment().getPosition())
                .workExperienceTotal(client.getEmployment().getWorkExperienceTotal())
                .workExperienceCurrent(client.getEmployment().getWorkExperienceCurrent())
                .build();
        return ScoringDataDTO.builder()
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .gender(client.getGender())
                .birthdate(client.getBirthdate())
                .passportSeries(client.getPassport().getPassportSeries())
                .passportNumber(client.getPassport().getPassportNumber())
                .passportIssueDate(client.getPassport().getPassportIssueDate())
                .passportIssueBranch(client.getPassport().getPassportIssueBranch())
                .maritalStatus(client.getMaritalStatus())
                .dependentAmount(client.getDependentAmount())
                .employment(employmentDTO)
                .account(client.getAccount())
                .isInsuranceEnabled(application.getCredit().getIsInsuranceEnabled())
                .isSalaryClient(application.getCredit().getIsSalaryClient())
                .build();
    }

}
