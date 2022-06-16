package ru.lsan.deal.database.service.client;

import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;

public interface ClientService {

    ClientEntity create(LoanApplicationRequestDTO dto);

    ClientEntity update(ClientEntity client);

}
