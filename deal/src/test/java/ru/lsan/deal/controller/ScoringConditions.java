package ru.lsan.deal.controller;

import ru.lsan.deal.dto.ScoringDataDTO;
import ru.lsan.deal.enums.EmploymentStatusEnum;
import ru.lsan.deal.enums.GenderEnum;
import ru.lsan.deal.enums.MaritalStatusEnum;
import ru.lsan.deal.enums.PositionEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScoringConditions {
    private static final int UPPER_AGE_LIMIT_FEMALE = 60;
    private static final int UPPER_AGE_LIMIT_MALE = 55;
    private static final int LOWER_AGE_LIMIT_FEMALE = 35;
    private static final int LOWER_AGE_LIMIT_MALE = 30;
    private static final int UPPER_AGE_LIMIT = 60;
    private static final int LOWER_AGE_LIMIT = 20;
    private static final int LOWER_CURRENT_WORK_EXPERIENCE = 3;
    private static final int LOWER_TOTAL_WORK_EXPERIENCE = 12;

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
        return dto.getGender().equals(GenderEnum.FEMALE) && (currentUserAge.getYear() >= LOWER_AGE_LIMIT_FEMALE && currentUserAge.getYear() <= UPPER_AGE_LIMIT_FEMALE);
    }

    public static boolean isMaleFrom30to55(ScoringDataDTO dto) {
        LocalDate currentDate = LocalDate.now();
        LocalDate userBirthdate = dto.getBirthdate();
        LocalDate currentUserAge = currentDate.minusYears(userBirthdate.getYear()).minusDays(userBirthdate.getDayOfYear());
        return dto.getGender().equals(GenderEnum.MALE) && (currentUserAge.getYear() >= LOWER_AGE_LIMIT_MALE && currentUserAge.getYear() <= UPPER_AGE_LIMIT_MALE);
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
        return currentUserAge.getYear() < LOWER_AGE_LIMIT || currentUserAge.getYear() > UPPER_AGE_LIMIT;
    }

    private static boolean isWorkExperienceValid(ScoringDataDTO dto) {
        return dto.getEmployment().getWorkExperienceCurrent() < LOWER_CURRENT_WORK_EXPERIENCE || dto.getEmployment().getWorkExperienceTotal() < LOWER_TOTAL_WORK_EXPERIENCE;
    }
}
