package ru.lsan.deal.database.service.impl.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.application.StatusHistoryEntity;
import ru.lsan.deal.database.repository.application.StatusHistoryRepository;
import ru.lsan.deal.database.service.application.StatusHistoryService;
import ru.lsan.deal.enums.StatusEnum;

@Service
@RequiredArgsConstructor
public class StatusHistoryServiceImpl implements StatusHistoryService {

    private final StatusHistoryRepository statusHistoryRepository;

    @Override
    public StatusHistoryEntity create(StatusEnum statusEnum, ApplicationEntity applicationEntity) {
        StatusHistoryEntity statusHistory = StatusHistoryEntity.builder()
                .status(statusEnum)
                .application(applicationEntity)
                .build();
        return statusHistoryRepository.save(statusHistory);
    }
}
