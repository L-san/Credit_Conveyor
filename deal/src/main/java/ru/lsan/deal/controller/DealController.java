package ru.lsan.deal.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController("/deal")
public class DealController {

    @PostMapping("/application")
    @ApiOperation(value = "расчёт возможных условий кредита")
    public void application() {

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
