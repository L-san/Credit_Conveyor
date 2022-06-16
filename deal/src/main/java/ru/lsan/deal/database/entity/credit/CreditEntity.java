package ru.lsan.deal.database.entity.credit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.lsan.deal.enums.StatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credit")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class CreditEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private Double rate;

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

}
