package ru.lsan.deal.database.repository.credit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lsan.deal.database.entity.credit.PaymentScheduleEntity;

@Repository
public interface PaymentScheduleRepository extends JpaRepository<PaymentScheduleEntity, Long> {
}
