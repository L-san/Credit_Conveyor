package ru.lsan.deal.database.service.impl.credit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lsan.deal.database.entity.credit.CreditEntity;
import ru.lsan.deal.database.entity.credit.PaymentScheduleEntity;
import ru.lsan.deal.database.repository.credit.CreditRepository;
import ru.lsan.deal.database.service.credit.CreditService;
import ru.lsan.deal.database.service.credit.PaymentScheduleService;
import ru.lsan.deal.dto.CreditDTO;
import ru.lsan.deal.dto.PaymentScheduleElementDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;
    private final PaymentScheduleService paymentScheduleService;

    @Override
    public CreditEntity create(CreditDTO dto) {
        List<PaymentScheduleEntity> paymentScheduleEntityList = new ArrayList<>();

               CreditEntity credit = CreditEntity.builder()
                .amount(dto.getAmount())
                .term(dto.getTerm())
                .monthlyPayment(dto.getMonthlyPayment())
                .rate(dto.getRate())
                .psk(dto.getPsk())
                .isInsuranceEnabled(dto.getIsInsuranceEnabled())
                .isSalaryClient(dto.getIsSalaryClient())
                .build();

        CreditEntity creditEntity = creditRepository.save(credit);

        for(PaymentScheduleElementDto elem : dto.getPaymentScheduleElementDtoList()){
            paymentScheduleEntityList.add(paymentScheduleService.create(elem, creditEntity));
        }
        creditEntity.setPaymentScheduleList(paymentScheduleEntityList);
        return update(credit);
    }

    @Override
    public CreditEntity update(CreditEntity credit) {
        return creditRepository.save(credit);
    }

}
