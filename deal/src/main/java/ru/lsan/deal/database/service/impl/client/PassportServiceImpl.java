package ru.lsan.deal.database.service.impl.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lsan.deal.database.entity.client.PassportEntity;
import ru.lsan.deal.database.repository.client.PassportRepository;
import ru.lsan.deal.database.service.client.PassportService;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;

@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    @Override
    public PassportEntity create(LoanApplicationRequestDTO dto) {
        PassportEntity passport = PassportEntity.builder()
                .passportSeries(dto.getPassportSeries())
                .passportNumber(dto.getPassportNumber())
                .build();
        return passportRepository.save(passport);
    }

    @Override
    public PassportEntity update(PassportEntity passport) {
        return passportRepository.save(passport);
    }

}
