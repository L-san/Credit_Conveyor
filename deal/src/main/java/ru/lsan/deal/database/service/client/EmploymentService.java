package ru.lsan.deal.database.service.client;

import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.entity.client.EmploymentEntity;
import ru.lsan.deal.dto.EmploymentDTO;

public interface EmploymentService {

    EmploymentEntity create(EmploymentDTO dto, ClientEntity client);

}
