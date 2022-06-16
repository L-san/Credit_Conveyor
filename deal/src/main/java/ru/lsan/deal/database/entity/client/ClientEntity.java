package ru.lsan.deal.database.entity.client;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.lsan.deal.enums.GenderEnum;
import ru.lsan.deal.enums.MaritalStatusEnum;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "client")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ClientEntity {

    @Id
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


}
