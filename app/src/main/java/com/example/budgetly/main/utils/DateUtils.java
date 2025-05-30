package com.example.budgetly.main.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {
    public static final String displayableDateStringFormat = "yyyy-MM-dd HH:mm";

    public static LocalDateTime convertUnixToLocalDateTime(Long unixTimestamp) {
        // Convert Unix timestamp (in seconds) to Instant
        Instant instant = Instant.ofEpochSecond(unixTimestamp);

        // Convert Instant to LocalDateTime using the system's default time zone
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Long convertLocalDateTimeToUnix(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static LocalDateTime convertDisplayableDateToLocalDateTime(String input) {
        return LocalDateTime.parse(input, DateTimeFormatter.ofPattern(displayableDateStringFormat));
    }

    public static String convertLocalDateTimeToDisplayableDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(displayableDateStringFormat);
        return localDateTime.format(formatter);
    }

    public static String convertNumericYearMonthToDisplayableMonth(String input) {
        YearMonth yearMonth = YearMonth.parse(input);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        return yearMonth.format(formatter);
    }

    public static String convertDisplayableMonthToNumericYearMonth(String input) {
        DateTimeFormatter formatterToYearMonth = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        YearMonth yearMonth = YearMonth.parse(input, formatterToYearMonth);
        return yearMonth.toString();
    }

    public static String convertLocalDateTimeToNumericYearMonth(LocalDateTime input) {
        return input.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public static String convertLocalDateTimeToNumericYearMonthDay(LocalDateTime input) {
        return input.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static int getFirstMonthsDay() {
        return LocalDate.now().withDayOfMonth(1).getDayOfMonth();
    }

    public static int getCurrentDay() {
        return LocalDate.now().getDayOfMonth();
    }
}
