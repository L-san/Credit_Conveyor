package ru.lsan.deal.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lsan.deal.database.entity.EmploymentEntity;

@Repository
public interface EmploymentRepository extends JpaRepository<EmploymentEntity, Long> {
}
