package ru.lsan.deal.database.entity.credit;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment_schedule")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class PaymentScheduleEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    @Column(name = "interest_payment")
    private BigDecimal interestPayment;

    @Column(name = "debt_payment")
    private BigDecimal debtPayment;

    @Column(name = "remaining_debt")
    private BigDecimal remainingDebt;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    private CreditEntity credit;

}
