package ru.lsan.conveyor.service;

import ru.lsan.conveyor.dto.ScoringDataDTO;
import ru.lsan.conveyor.dto.enums.EmploymentStatusEnum;
import ru.lsan.conveyor.dto.enums.GenderEnum;
import ru.lsan.conveyor.dto.enums.MaritalStatusEnum;
import ru.lsan.conveyor.dto.enums.PositionEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

class ScoringConditions {

    public static boolean isLoanShouldBeDenied(ScoringDataDTO dto) {
        return isUnemployed(dto) || isAmountLargerThan20Salaries(dto) || isUserAgeInBounds(dto) || isWorkExperienceValid(dto);
    }

    public static boolean isSelfEmployed(ScoringDataDTO dto) {
        return dto.getEmployment().getEmploymentStatusEnum().equals(EmploymentStatusEnum.SELF_EMPLOYED);
    }

    public static boolean isBusinessMan(ScoringDataDTO dto) {
        return dto.getEmployment().getEmploymentStatusEnum().equals(EmploymentStatusEnum.BUSINESSMAN);
    }

    public static boolean isTopManager(ScoringDataDTO dto) {
        return dto.getEmployment().getPosition().equals(PositionEnum.TOP_MANAGER);
    }

    public static boolean isMarried(ScoringDataDTO dto) {
        return dto.getMaritalStatus().equals(MaritalStatusEnum.MARRIED);
    }

    public static boolean isDivorced(ScoringDataDTO dto) {
        return dto.getMaritalStatus().equals(MaritalStatusEnum.DIVORCED);
    }

    public static boolean isFemaleFrom35to60(ScoringDataDTO dto) {
        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthdate = dto.getBirthdate();
        LocalDate currentUserAge = currentDate.minusYears(userBirthdate.getYear()).minusDays(userBirthdate.getDayOfYear());
        return dto.getGender().equals(GenderEnum.FEMALE) && (currentUserAge.getYear() >= 35 && currentUserAge.getYear() <= 60);
    }

    public static boolean isMaleFrom30to55(ScoringDataDTO dto) {
        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthdate = dto.getBirthdate();
        LocalDate currentUserAge = currentDate.minusYears(userBirthdate.getYear()).minusDays(userBirthdate.getDayOfYear());
        return dto.getGender().equals(GenderEnum.MALE) && (currentUserAge.getYear() >= 30 && currentUserAge.getYear() <= 55);
    }

    public static boolean isGenderNonBinary(ScoringDataDTO dto) {
        return dto.getGender().equals(GenderEnum.NON_BINARY);
    }

    private static boolean isUnemployed(ScoringDataDTO dto) {
        return dto.getEmployment().getEmploymentStatusEnum().equals(EmploymentStatusEnum.UNEMPLOYED);
    }

    private static boolean isAmountLargerThan20Salaries(ScoringDataDTO dto) {
        BigDecimal twentySalaries = dto.getEmployment().getSalary().multiply(BigDecimal.valueOf(20));
        return dto.getAmount().compareTo(twentySalaries) > 0;
    }

    private static boolean isUserAgeInBounds(ScoringDataDTO dto) {
        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthdate = dto.getBirthdate();
        LocalDate currentUserAge = currentDate.minusYears(userBirthdate.getYear()).minusDays(userBirthdate.getDayOfYear());
        return currentUserAge.getYear() < 20 || currentUserAge.getYear() > 60;
    }

    private static boolean isWorkExperienceValid(ScoringDataDTO dto) {
        return dto.getEmployment().getWorkExperienceCurrent() < 3 || dto.getEmployment().getWorkExperienceTotal() < 12;
    }

}
