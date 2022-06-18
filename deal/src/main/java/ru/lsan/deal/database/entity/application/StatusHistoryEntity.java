package ru.lsan.deal.database.entity.application;

import lombok.*;
import ru.lsan.deal.enums.StatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "status_history")
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class StatusHistoryEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status")
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private ApplicationEntity application;

}
