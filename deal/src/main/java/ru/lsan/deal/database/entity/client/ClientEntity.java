package ru.lsan.deal.database.entity.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.enums.GenderEnum;
import ru.lsan.deal.enums.MaritalStatusEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "client")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class ClientEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "email")
    private String email;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Column(name = "gender")
    private GenderEnum gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "martial_status")
    private MaritalStatusEnum maritalStatus;

    @Column(name = "account")
    private String account;

    @OneToOne(mappedBy = "client")
    private PassportEntity passport;

    @OneToOne(mappedBy = "client")
    private EmploymentEntity employment;

    @OneToOne(mappedBy = "client")
    private ApplicationEntity application;
}
