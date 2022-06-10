package ru.lsan.conveyor.exception.custom_exceptions;

public class IncorrectRequestParametersException extends RuntimeException {

    public IncorrectRequestParametersException(String message) {
        super(message);
    }

}
