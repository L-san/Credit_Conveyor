package ru.lsan.deal.database.entity.application;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.lsan.deal.enums.StatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

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

}
