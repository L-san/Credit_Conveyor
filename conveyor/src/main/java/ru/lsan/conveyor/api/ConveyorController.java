package ru.lsan.conveyor.api;

import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lsan.conveyor.dto.CreditDTO;
import ru.lsan.conveyor.dto.LoanApplicationRequestDTO;
import ru.lsan.conveyor.dto.ScoringDataDTO;
import ru.lsan.conveyor.exception.custom_exceptions.IncorrectRequestParametersException;
import ru.lsan.conveyor.exception.custom_exceptions.LoanDenialException;
import ru.lsan.conveyor.service.PrescoringService;
import ru.lsan.conveyor.service.ScoringService;
import ru.lsan.conveyor.tools.ClassFieldsValidator;

@RestController
@RequestMapping("/conveyor")
@Log4j2
@Api(value = "Микросервис: кредитный конвеер")
public class ConveyorController {

    @Autowired
    private PrescoringService prescoringService;

    @Autowired
    private ScoringService scoringService;

    @PostMapping("/offers")
    @ApiOperation(value = "валидация присланных данных + расчёт возможных условий кредита")
    public ResponseEntity offers(@RequestBody LoanApplicationRequestDTO dto) {
        ClassFieldsValidator.validateClassFields(dto);
        log.info("/conveyor/offers: accepted dto");
        return ResponseEntity.of(Optional.of(prescoringService.prescoreLoan(dto)));
    }

    @PostMapping("/calculation")
    @ApiOperation(value = "скоринг данных + полный расчет параметров кредита")
    public ResponseEntity calculation(@RequestBody ScoringDataDTO dto) {
        CreditDTO creditDTO;
        ClassFieldsValidator.validateClassFields(dto);
        creditDTO = scoringService.scoreLoan(dto);
        log.info("/conveyor/calculation: accepted dto");
        return ResponseEntity.of(Optional.of(creditDTO));
    }

}
