package ru.lsan.deal.database.service.credit;

import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.entity.credit.CreditEntity;
import ru.lsan.deal.database.entity.credit.PaymentScheduleEntity;
import ru.lsan.deal.dto.PaymentScheduleElementDto;

public interface PaymentScheduleService {

    PaymentScheduleEntity create(PaymentScheduleElementDto dto, CreditEntity credit);

}
