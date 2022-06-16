package ru.lsan.deal.database.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lsan.deal.database.entity.application.StatusHistoryEntity;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistoryEntity, Long> {
}
