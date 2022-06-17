package ru.lsan.deal.database.service.application;

import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.application.StatusHistoryEntity;
import ru.lsan.deal.enums.StatusEnum;

public interface StatusHistoryService {

    StatusHistoryEntity create(StatusEnum statusEnum, ApplicationEntity applicationEntity);

}
