package ru.lsan.deal.database.entity.application;

import ch.qos.logback.core.net.server.Client;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.entity.credit.CreditEntity;
import ru.lsan.deal.database.entity.credit.PaymentScheduleEntity;
import ru.lsan.deal.enums.StatusEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "application")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ApplicationEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @Column(name = "ses_code")
    private String sesCode;

    @OneToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @OneToOne
    @JoinColumn(name = "credit_id")
    private CreditEntity credit;

    //applied offer??? todo

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<StatusHistoryEntity> statusHistoryList;

}
