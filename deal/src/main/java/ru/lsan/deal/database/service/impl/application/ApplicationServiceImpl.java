package ru.lsan.deal.database.service.impl.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.repository.application.ApplicationRepository;
import ru.lsan.deal.database.service.application.ApplicationService;
import ru.lsan.deal.database.service.application.StatusHistoryService;
import ru.lsan.deal.enums.StatusEnum;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StatusHistoryService statusHistoryService;

    @Override
    public ApplicationEntity create(ClientEntity client) {
        ApplicationEntity application = ApplicationEntity.builder()
                .client(client)
                .build();
        return applicationRepository.save(application);
    }

    @Override
    public ApplicationEntity update(ApplicationEntity application) {
        return applicationRepository.save(application);
    }

    @Override
    public void updateStatus(ApplicationEntity application, StatusEnum status) {
        statusHistoryService.create(application.getStatus(),application);
        application.setStatus(status);
        update(application);
    }

    @Override
    public ApplicationEntity findById(Long id) {
        return applicationRepository.findByAppId(id);
    }

}
