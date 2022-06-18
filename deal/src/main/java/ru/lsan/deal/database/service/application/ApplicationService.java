package ru.lsan.deal.database.service.application;

import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.enums.StatusEnum;

public interface ApplicationService {

    ApplicationEntity create(ClientEntity client);

    ApplicationEntity update(ApplicationEntity application);

    void updateStatus(ApplicationEntity application, StatusEnum status);

    ApplicationEntity findById(Long id);

}
