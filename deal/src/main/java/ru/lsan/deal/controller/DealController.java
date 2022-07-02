package ru.lsan.deal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lsan.deal.Tools;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.service.application.ApplicationService;
import ru.lsan.deal.database.service.client.ClientService;
import ru.lsan.deal.database.service.credit.CreditService;
import ru.lsan.deal.dto.*;
import ru.lsan.deal.enums.StatusEnum;
import ru.lsan.deal.feign.ConveyorClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
@Api(value = "методы микросервиса deal")
@Log4j2
public class DealController {

    private final ConveyorClient conveyorClient;
    private final ClientService clientService;
    private final ApplicationService applicationService;
    private final CreditService creditService;

    @PostMapping("/application")
    @ApiOperation(value = "расчёт возможных условий кредита")
    public ResponseEntity<List<LoanOfferDTO>> application(@RequestBody LoanApplicationRequestDTO dto) {
        log.info("/deal/application accepted: "+ Tools.asJsonString(dto));
        ClientEntity client = clientService.create(dto);
        ApplicationEntity application = applicationService.create(client);
        ResponseEntity<Optional<List<LoanOfferDTO>>> resp = conveyorClient.offers(dto);
        if (resp.getStatusCode().is4xxClientError()) {
            applicationService.updateStatus(application,StatusEnum.CC_DENIED);
            log.error("/deal/application CC_DENIED "+resp.getBody());
            return ResponseEntity.badRequest().body(new ArrayList<>());
        } else {
            List<LoanOfferDTO> loanOfferDTOList = resp.getBody().get();
            Long id = application.getId();
            for (LoanOfferDTO loan : loanOfferDTOList) {
                loan.setApplicationId(id);
            }
            applicationService.updateStatus(application,StatusEnum.PREAPPROVAL);
            log.info("/deal/application resolved: "+Tools.asJsonString(application));
            return ResponseEntity.ok().body(loanOfferDTOList);
        }
    }

    @PutMapping("/offer")
    @ApiOperation(value = "выбор одного из предложений")
    public void offer(@RequestBody LoanOfferDTO dto) {
        log.info("/deal/offer accepted: "+ Tools.asJsonString(dto));
        ApplicationEntity application = applicationService.findById(dto.getApplicationId());
        application.setAppliedOffer(dto);
        application = applicationService.update(application);
        applicationService.updateStatus(application,StatusEnum.APPROVED);
        log.info("/deal/offer resolved :"+Tools.asJsonString(application));
    }

    @PutMapping("/calculate/{applicationId}")
    @ApiOperation(value = "полный расчет параметров кредита")
    public void calculate(@PathVariable("applicationId") Long applicationId, @RequestBody FinishRegistrationRequestDTO dto) {
        ApplicationEntity application = applicationService.findById(applicationId);
        ClientEntity client = application.getClient();
        client = clientService.update(client,dto);
        ScoringDataDTO scoringDataDTO = ScoringDataDTO.from(application,client);
        ResponseEntity<Optional<CreditDTO>> resp = conveyorClient.calculation(scoringDataDTO);
        if (resp.getStatusCode().is4xxClientError()) {
            application.setStatus(StatusEnum.CC_DENIED);
            log.error("/calculate/{" + application.getId().toString() + "} CC_DENIED "+resp.getBody());
        } else {
            CreditDTO credit = resp.getBody().get();
            creditService.create(credit);
            application.setStatus(StatusEnum.CC_APPROVED);
            applicationService.update(application);
            log.info("/calculate/{" + application.getId().toString() + "} resolved: "+Tools.asJsonString(application));
            log.info("/calculate/{" + application.getId().toString() + "} resolved: "+Tools.asJsonString(credit));
        }
    }

}
