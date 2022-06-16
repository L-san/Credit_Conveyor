package ru.lsan.deal.database.entity.application;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.lsan.deal.enums.StatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "status_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class StatusHistoryEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status")
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;

}
