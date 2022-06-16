package ru.lsan.deal.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lsan.deal.database.entity.PassportEntity;

@Repository
public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
}
