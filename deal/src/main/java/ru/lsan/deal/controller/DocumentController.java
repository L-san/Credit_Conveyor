package ru.lsan.deal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/deal/document")
@Api(value = "методы, связанные с документами")
public class DocumentController {

    @PostMapping("/{applicationId}/send")
    @ApiOperation(value = "запрос на отправку документов")
    public void send(@PathVariable("applicationId") Integer applicationId){

    }

    @PostMapping("/{applicationId}/sign")
    @ApiOperation(value = "запрос на подписание документов")
    public void sign(@PathVariable("applicationId") Integer applicationId){

    }

    @PostMapping("/{applicationId}/code")
    @ApiOperation(value = "подписание документов")
    public void code(@PathVariable("applicationId") Integer applicationId){

    }

}
