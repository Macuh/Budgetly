package com.example.budgetly.main.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
    public static LocalDateTime convertUnixToLocalDateTime(Long unixTimestamp) {
        // Convert Unix timestamp (in seconds) to Instant
        Instant instant = Instant.ofEpochSecond(unixTimestamp);

        // Convert Instant to LocalDateTime using the system's default time zone
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static String convertLocalDateTimeToDisplayableDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static String convertNumericYearMonthToDisplayableMonth(String input) {
        YearMonth yearMonth = YearMonth.parse(input);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        return yearMonth.format(formatter);
    }

    public static String convertDisplayableMonthToNumericYearMonth(String input) {
        DateTimeFormatter formatterToYearMonth = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        YearMonth yearMonth2 = YearMonth.parse(input, formatterToYearMonth);
        return yearMonth2.toString();
    }

    public static int getFirstMonthsDay() {
        return LocalDate.now().withDayOfMonth(1).getDayOfMonth();
    }

    public static int getCurrentDay() {
        return LocalDate.now().getDayOfMonth();
    }
}
