package com.example.budgetly.main.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String convertUnixToStringLocalDateTime(Long unixTimestamp) {
        // Convert Unix timestamp (in seconds) to Instant
        Instant instant = Instant.ofEpochSecond(unixTimestamp);

        // Convert Instant to LocalDateTime using the system's default time zone
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // Format LocalDateTime to a string (you can customize the pattern)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
