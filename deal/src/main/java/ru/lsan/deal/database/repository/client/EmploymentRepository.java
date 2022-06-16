package ru.lsan.deal.database.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lsan.deal.database.entity.client.EmploymentEntity;

@Repository
public interface EmploymentRepository extends JpaRepository<EmploymentEntity, Long> {
}
