package ru.lsan.conveyor.exception.custom_exceptions;

public class LoanDenialException extends RuntimeException {

    public LoanDenialException(String message) {
        super(message);
    }

}
