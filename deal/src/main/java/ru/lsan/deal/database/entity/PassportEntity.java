package ru.lsan.deal.database.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passport")
@Getter
@Setter
@Builder
@NoArgsConstructor
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
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

}
