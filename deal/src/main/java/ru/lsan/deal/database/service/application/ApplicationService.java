package ru.lsan.deal.database.service.application;

import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;

public interface ApplicationService {

    ApplicationEntity create(ClientEntity client);

    ApplicationEntity update(ApplicationEntity application);

}
