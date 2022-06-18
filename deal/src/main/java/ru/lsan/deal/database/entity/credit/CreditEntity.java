package ru.lsan.deal.database.entity.credit;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.enums.StatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credit")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter
@Getter
public class CreditEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "psk")
    private BigDecimal psk;

    @Column(name = "is_insurance_enabled")
    private Boolean isInsuranceEnabled;

    @Column(name = "is_salary_client")
    private Boolean isSalaryClient;

    @Column(name = "credit_status")
    private StatusEnum creditStatus;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<PaymentScheduleEntity> paymentScheduleList;

    @OneToOne(mappedBy = "credit")
    private ApplicationEntity application;

}
