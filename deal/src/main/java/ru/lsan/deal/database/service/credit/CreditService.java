package ru.lsan.deal.database.service.credit;

import ru.lsan.deal.database.entity.credit.CreditEntity;
import ru.lsan.deal.dto.CreditDTO;
import ru.lsan.deal.dto.FinishRegistrationRequestDTO;

public interface CreditService {

    CreditEntity create(CreditDTO dto);

    CreditEntity update(CreditEntity credit);

}
