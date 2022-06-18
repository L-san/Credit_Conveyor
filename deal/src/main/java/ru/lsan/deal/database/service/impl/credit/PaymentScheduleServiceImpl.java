package ru.lsan.deal.database.service.impl.credit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.entity.credit.CreditEntity;
import ru.lsan.deal.database.entity.credit.PaymentScheduleEntity;
import ru.lsan.deal.database.repository.credit.PaymentScheduleRepository;
import ru.lsan.deal.database.service.credit.PaymentScheduleService;
import ru.lsan.deal.dto.PaymentScheduleElementDto;

@Service
@RequiredArgsConstructor
public class PaymentScheduleServiceImpl implements PaymentScheduleService {

    private final PaymentScheduleRepository paymentScheduleRepository;

    @Override
    public PaymentScheduleEntity create(PaymentScheduleElementDto dto, CreditEntity credit) {
        PaymentScheduleEntity paymentScheduleEntity = PaymentScheduleEntity.builder()
                .number(dto.getNumber())
                .date(dto.getDate())
                .totalPayment(dto.getTotalPayment())
                .interestPayment(dto.getInterestPayment())
                .debtPayment(dto.getDebtPayment())
                .remainingDebt(dto.getRemainingDebt())
                .credit(credit)
                .build();
        return paymentScheduleRepository.save(paymentScheduleEntity);
    }

}
