package ru.lsan.deal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lsan.deal.database.entity.application.ApplicationEntity;
import ru.lsan.deal.database.entity.application.StatusHistoryEntity;
import ru.lsan.deal.database.entity.client.ClientEntity;
import ru.lsan.deal.database.service.application.ApplicationService;
import ru.lsan.deal.database.service.application.StatusHistoryService;
import ru.lsan.deal.database.service.client.ClientService;
import ru.lsan.deal.dto.*;
import ru.lsan.deal.enums.StatusEnum;
import ru.lsan.deal.feign.ConveyorClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController("/deal")
@RequiredArgsConstructor
@Api(value = "методы микросервиса")
@Log4j2
public class DealController {

    private final ConveyorClient conveyorClient;
    private final ClientService clientService;
    private final ApplicationService applicationService;
    private final StatusHistoryService statusHistoryService;

    @PostMapping("/application")
    @ApiOperation(value = "расчёт возможных условий кредита")
    public ResponseEntity<List<LoanOfferDTO>> application(@RequestBody LoanApplicationRequestDTO dto) {
        ClientEntity client = clientService.create(dto);
        ApplicationEntity application = applicationService.create(client);
        ResponseEntity<Optional<List<LoanOfferDTO>>> resp = conveyorClient.offers(dto);
        if (resp.getStatusCode().is4xxClientError()) {
            application.setStatus(StatusEnum.CC_DENIED);
            return ResponseEntity.badRequest().body(new ArrayList<>());
        } else {
            List<LoanOfferDTO> loanOfferDTOList = resp.getBody().get();
            Long id = application.getId();
            for (LoanOfferDTO loan : loanOfferDTOList) {
                loan.setApplicationId(id);
            }
            log.info("/deal/application resolved");
            return ResponseEntity.ok().body(loanOfferDTOList);
        }
    }

    @PutMapping("/offer")
    @ApiOperation(value = "выбор одного из предложений")
    public void offer(@RequestBody LoanOfferDTO dto) {
        ApplicationEntity application = applicationService.findById(dto.getApplicationId());
        StatusHistoryEntity statusHistoryEntity = statusHistoryService.create(StatusEnum.PREAPPROVAL, application);
        application.setStatus(StatusEnum.PREAPPROVAL);
        application.setAppliedOffer(dto);
        applicationService.update(application);
        log.info("/deal/offer resolved");
        //email to user todo
    }

    @PutMapping("/calculate/{applicationId}")
    @ApiOperation(value = "полный расчет параметров кредита")
    public void calculate(@PathVariable("applicationId") Long applicationId, @RequestBody FinishRegistrationRequestDTO dto) {
        ApplicationEntity application = applicationService.findById(applicationId);
        ClientEntity client = application.getClient();
        EmploymentDTO employmentDTO = EmploymentDTO.builder()
                .employmentStatusEnum(client.getEmployment().getEmploymentStatus())
                .salary(client.getEmployment().getSalary())
                .position(client.getEmployment().getPosition())
                .workExperienceTotal(client.getEmployment().getWorkExperienceTotal())
                .workExperienceCurrent(client.getEmployment().getWorkExperienceCurrent())
                .build();
        ScoringDataDTO scoringDataDTO = ScoringDataDTO.builder()
                .amount(application.getCredit().getAmount())
                .term(application.getCredit().getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .gender(client.getGender())
                .birthdate(client.getBirthdate())
                .passportSeries(client.getPassport().getPassportSeries())
                .passportNumber(client.getPassport().getPassportNumber())
                .passportIssueDate(client.getPassport().getPassportIssueDate())
                .passportIssueBranch(client.getPassport().getPassportIssueBranch())
                .maritalStatus(client.getMaritalStatus())
                .dependentAmount(client.getDependentAmount())
                .employment(employmentDTO)
                .account(client.getAccount())
                .isInsuranceEnabled(application.getCredit().getIsInsuranceEnabled())
                .isSalaryClient(application.getCredit().getIsSalaryClient())
                .build();
        ResponseEntity<Optional<CreditDTO>> resp = conveyorClient.calculation(scoringDataDTO);//todo
        if (resp.getStatusCode().is4xxClientError()) {
            application.setStatus(StatusEnum.CC_DENIED);
        } else {
            CreditDTO credit = resp.getBody().get();
        }
        log.info("/calculate/{" + applicationId + "} resolved");

    }


}
