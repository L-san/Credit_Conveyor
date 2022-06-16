package ru.lsan.deal.database.service.client;

import ru.lsan.deal.database.entity.client.PassportEntity;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;

public interface PassportService {

    PassportEntity create(LoanApplicationRequestDTO dto);

    PassportEntity update(PassportEntity passport);

}
