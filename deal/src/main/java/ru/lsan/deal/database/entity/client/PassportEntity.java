package ru.lsan.deal.database.entity.client;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passport")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class PassportEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "passport_issue_branch")
    private String passportIssueBranch;

    @Column(name = "passport_issue_date")
    private LocalDate passportIssueDate;

    @OneToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

}
