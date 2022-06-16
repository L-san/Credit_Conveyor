package ru.lsan.deal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController("/deal/admin/application")
@Api(value = "методы администратора")
public class AdminController {



    @GetMapping("/{applicationId}")
    @ApiOperation(value = "получить заявку по id")
    public void application(@PathVariable("applicationId") Integer applicationId) {

    }

    @PutMapping("/{applicationId}/status")
    @ApiOperation(value = "обновить статус заявки")
    public void status(@PathVariable("applicationId") Integer applicationId) {

    }

}
