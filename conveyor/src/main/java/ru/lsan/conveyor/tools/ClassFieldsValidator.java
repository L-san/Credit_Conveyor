package ru.lsan.conveyor.tools;

import lombok.extern.log4j.Log4j2;
import ru.lsan.conveyor.dto.LoanApplicationRequestDTO;
import ru.lsan.conveyor.dto.ScoringDataDTO;
import ru.lsan.conveyor.exception.custom_exceptions.IncorrectRequestParametersException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Log4j2
public class ClassFieldsValidator {

    public static void validateClassFields(LoanApplicationRequestDTO dto) throws IncorrectRequestParametersException {
        validateAmount(dto.getAmount());
        validateTerm(dto.getTerm());
        validateFirstName(dto.getFirstName());
        validateLastName(dto.getLastName());
        validateMiddleName(dto.getMiddleName());
        validateEmail(dto.getEmail());
        validateBirthdate(dto.getBirthdate());
        validatePassportNumber(dto.getPassportNumber());
        validatePassportSeries(dto.getPassportSeries());
    }

    public static void validateClassFields(ScoringDataDTO dto) throws IncorrectRequestParametersException {
        validateAmount(dto.getAmount());
        validateTerm(dto.getTerm());
        validateFirstName(dto.getFirstName());
        validateLastName(dto.getLastName());
        validateMiddleName(dto.getMiddleName());
        validateBirthdate(dto.getBirthdate());
        validatePassportNumber(dto.getPassportNumber());
        validatePassportSeries(dto.getPassportSeries());
    }

    public static void validateAmount(BigDecimal amount) throws IncorrectRequestParametersException {
        if (amount.compareTo(BigDecimal.valueOf(10000)) < 0) {
            throw new IncorrectRequestParametersException("Amount can't be less than 10000");
        }
    }

    public static void validateTerm(Integer term) throws IncorrectRequestParametersException {
        if (term < 6) {
            throw new IncorrectRequestParametersException("Term can't be less than 6 months");
        }
    }

    public static void validateFirstName(String name) throws IncorrectRequestParametersException {
        isNameCorrect(name);
    }

    public static void validateLastName(String name) throws IncorrectRequestParametersException {
        isNameCorrect(name);
    }

    public static void validateMiddleName(String name) throws IncorrectRequestParametersException {
        isNameCorrect(name);
    }

    public static void validateEmail(String email) throws IncorrectRequestParametersException {
        if (!email.matches("[\\w.]{2,50}@[\\w.]{2,20}")) {
            throw new IncorrectRequestParametersException("Not an email format!");
        }
    }

    public static void validateBirthdate(LocalDate date) throws IncorrectRequestParametersException {
        LocalDate currentDate = LocalDate.now();
        LocalDate currentUserAge = currentDate.minusYears(date.getYear()).minusDays(date.getDayOfYear());
        if (currentUserAge.getYear() < 18) {
            throw new IncorrectRequestParametersException("Age can't be less than 18");
        }
    }

    public static void validatePassportSeries(String series) throws IncorrectRequestParametersException {
        if (series.length() != 4 || !series.matches("[0-9]+")) {
            throw new IncorrectRequestParametersException("Wrong passport series format");
        }
    }

    public static void validatePassportNumber(String number) throws IncorrectRequestParametersException {
        if (number.length() != 6 || !number.matches("[0-9]+")) {
            throw new IncorrectRequestParametersException("Wrong passport number format");
        }
    }

    private static boolean isNameCorrect(String name) throws IncorrectRequestParametersException {
        if (name.length() >= 2 && name.length() <= 30 && name.matches("[A-Za-z]+")) {
            return true;
        } else {
            throw new IncorrectRequestParametersException("Firstname, Lastname or Middlename is incorrect");
        }
    }

}
