package ru.lsan.deal.database.entity.client;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.lsan.deal.enums.EmploymentStatusEnum;
import ru.lsan.deal.enums.PositionEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employment")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class EmploymentEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "employment_status ")
    private EmploymentStatusEnum employmentStatus;

    @Column(name = "employer")
    private String employer;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "position")
    private PositionEnum position;

    @Column(name = "work_experience_total")
    private Integer workExperienceTotal;

    @Column(name = "work_experience_current")
    private Integer workExperienceCurrent;

    @OneToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

}
