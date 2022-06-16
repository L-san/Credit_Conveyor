package ru.lsan.deal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.service.application.ApplicationService;
import ru.lsan.deal.database.service.client.ClientService;
import ru.lsan.deal.dto.LoanApplicationRequestDTO;
import ru.lsan.deal.dto.LoanOfferDTO;
import ru.lsan.deal.feign.ConveyorClient;

import java.util.List;

@RestController("/deal")
@RequiredArgsConstructor
@Api(value = "методы микросервиса")
@Log4j2
public class DealController {

    private final ConveyorClient conveyorClient;

    private final ClientService clientService;

    private final ApplicationService applicationService;

    @PostMapping("/application")
    @ApiOperation(value = "расчёт возможных условий кредита")
    public ResponseEntity<List<LoanOfferDTO>> application(@RequestBody LoanApplicationRequestDTO dto) {
        ClientEntity client = clientService.create(dto);
        ApplicationEntity application = applicationService.create(client);
        List<LoanOfferDTO> loanOfferDTOList = conveyorClient.offers(dto).getBody().get();
        Long id = application.getId();
        for (LoanOfferDTO loan : loanOfferDTOList) {
            loan.setApplicationId(id);
        }
        log.info("/deal/application resolved");
        return ResponseEntity.ok().body(loanOfferDTOList);
    }

    @PutMapping("/offer")
    @ApiOperation(value = "выбор одного из предложений")
    public void offer() {

    }

    @PutMapping("/calculate/{applicationId}")
    @ApiOperation(value = "полный расчет параметров кредита")
    public void calculate(@PathVariable("applicationId") Integer applicationId) {

    }

}
