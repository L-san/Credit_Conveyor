package ru.lsan.conveyor.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.lsan.conveyor.exception.custom_exceptions.IncorrectRequestParametersException;
import ru.lsan.conveyor.exception.custom_exceptions.LoanDenialException;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({LoanDenialException.class})
    public ResponseEntity<String> handleLoanDenialException(LoanDenialException loanDenialException) {
        log.error(loanDenialException.getMessage());
        return ResponseEntity.badRequest().body(loanDenialException.getMessage());
    }

    @ExceptionHandler({IncorrectRequestParametersException.class})
    public ResponseEntity<String> handleIncorrectRequestParametersException(IncorrectRequestParametersException incorrectRequestParametersException) {
        log.error(incorrectRequestParametersException.getMessage());
        return ResponseEntity.badRequest().body(incorrectRequestParametersException.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        log.error(httpMessageNotReadableException.getMessage());
        return ResponseEntity.badRequest().body(httpMessageNotReadableException.getMessage());
    }

}
