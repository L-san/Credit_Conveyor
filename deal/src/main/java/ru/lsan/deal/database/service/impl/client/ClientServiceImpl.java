package ru.lsan.deal.database.service.impl.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.entity.client.PassportEntity;
import ru.lsan.deal.database.repository.client.ClientRepository;
import ru.lsan.deal.database.service.client.ClientService;
import ru.lsan.deal.database.service.client.PassportService;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final PassportService passportService;

    @Override
    public ClientEntity create(LoanApplicationRequestDTO dto) {
        PassportEntity passport = passportService.create(dto);
        ClientEntity client = ClientEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .passport(passport)
                .build();
        return clientRepository.save(client);
    }

    @Override
    public ClientEntity update(ClientEntity client) {
        return clientRepository.save(client);
    }

}
