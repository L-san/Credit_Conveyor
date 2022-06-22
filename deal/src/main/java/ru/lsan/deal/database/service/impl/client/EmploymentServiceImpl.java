package ru.lsan.deal.database.service.impl.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.entity.client.EmploymentEntity;
import ru.lsan.deal.database.repository.client.EmploymentRepository;
import ru.lsan.deal.database.service.client.EmploymentService;
import ru.lsan.deal.dto.EmploymentDTO;

@Service
@RequiredArgsConstructor
public class EmploymentServiceImpl implements EmploymentService {

    private final EmploymentRepository employmentRepository;

    @Override
    public EmploymentEntity create(EmploymentDTO dto, ClientEntity client) {
        EmploymentEntity employment = EmploymentEntity.builder()
                .employmentStatus(dto.getEmploymentStatus())
                .salary(dto.getSalary())
                .position(dto.getPosition())
                .workExperienceTotal(dto.getWorkExperienceTotal())
                .workExperienceCurrent(dto.getWorkExperienceCurrent())
                .build();
        return employmentRepository.save(employment);
    }

}
