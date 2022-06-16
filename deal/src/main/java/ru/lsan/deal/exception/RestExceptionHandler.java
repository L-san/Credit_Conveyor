package ru.lsan.deal.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
