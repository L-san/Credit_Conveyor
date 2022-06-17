package ru.lsan.deal.database.entity.application;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.entity.credit.CreditEntity;
import ru.lsan.deal.dto.LoanOfferDTO;
import ru.lsan.deal.enums.StatusEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "application")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter
@Getter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
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

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private LoanOfferDTO appliedOffer;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<StatusHistoryEntity> statusHistoryList;

}
