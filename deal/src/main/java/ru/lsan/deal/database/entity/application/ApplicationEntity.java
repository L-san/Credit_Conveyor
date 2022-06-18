package ru.lsan.deal.database.entity.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;
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
@TypeDef(name = "json", typeClass = JsonType.class)
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status")
    private StatusEnum status;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
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

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private LoanOfferDTO appliedOffer;

    @OneToMany(mappedBy = "application", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<StatusHistoryEntity> statusHistoryList;

}
