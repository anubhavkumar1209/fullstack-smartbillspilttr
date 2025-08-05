package com.smartbillsplittr.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_FORMATTER) : null;
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_FORMATTER) : null;
    }

    public static LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DEFAULT_FORMATTER);
    }

    public static LocalDateTime startOfDay(LocalDateTime dateTime) {
        return dateTime.truncatedTo(ChronoUnit.DAYS);
    }

    public static LocalDateTime endOfDay(LocalDateTime dateTime) {
        return dateTime.truncatedTo(ChronoUnit.DAYS).plusDays(1).minusNanos(1);
    }

    public static boolean isToday(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        return dateTime.toLocalDate().equals(now.toLocalDate());
    }

    public static boolean isThisWeek(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekStart = now.minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDateTime weekEnd = weekStart.plusDays(6);

        return !dateTime.isBefore(startOfDay(weekStart)) &&
                !dateTime.isAfter(endOfDay(weekEnd));
    }

    public static boolean isThisMonth(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        return dateTime.getMonth() == now.getMonth() &&
                dateTime.getYear() == now.getYear();
    }

    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
    }

    public static String getRelativeTime(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        long hours = ChronoUnit.HOURS.between(dateTime, now);
        long days = ChronoUnit.DAYS.between(dateTime, now);

        if (hours < 1) {
            long minutes = ChronoUnit.MINUTES.between(dateTime, now);
            return minutes <= 1 ? "just now" : minutes + " minutes ago";
        } else if (hours < 24) {
            return hours == 1 ? "1 hour ago" : hours + " hours ago";
        } else if (days < 7) {
            return days == 1 ? "1 day ago" : days + " days ago";
        } else {
            return formatDate(dateTime);
        }
    }
}
