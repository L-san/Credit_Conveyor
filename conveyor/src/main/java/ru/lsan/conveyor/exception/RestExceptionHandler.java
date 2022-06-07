package ru.lsan.conveyor.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.lsan.conveyor.exception.custom_exceptions.IncorrectRequestParametersException;
import ru.lsan.conveyor.exception.custom_exceptions.LoanDenialException;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({LoanDenialException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleLoanDenialException(LoanDenialException loanDenialException){
        log.error(loanDenialException.getMessage());
        return loanDenialException.getMessage();
    }

    @ExceptionHandler({IncorrectRequestParametersException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleIncorrectRequestParametersException(IncorrectRequestParametersException incorrectRequestParametersException){
        log.error(incorrectRequestParametersException.getMessage());
        return incorrectRequestParametersException.getMessage();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException){
        log.error(httpMessageNotReadableException.getMessage());
        return httpMessageNotReadableException.getMessage();
    }
    
}
