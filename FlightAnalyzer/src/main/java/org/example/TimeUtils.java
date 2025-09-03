package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");

    public static Duration getFlightDuration(String depDate, String depTime, String arrDate, String arrTime) {
        LocalDateTime departure = LocalDateTime.parse(depDate + " " + depTime, formatter);
        LocalDateTime arrival = LocalDateTime.parse(arrDate + " " + arrTime, formatter);
        return Duration.between(departure, arrival);
    }

    public static String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%dч %02dм", hours, minutes);
    }
}