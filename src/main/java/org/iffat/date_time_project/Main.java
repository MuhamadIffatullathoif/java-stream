package org.iffat.date_time_project;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Main {

    public static void main(String[] args) {

        LocalDate today = LocalDate.now();
        System.out.println(today);

        LocalDate five5 = LocalDate.of(2022, 5, 5);
        System.out.println(five5);

        LocalDate may5th = LocalDate.of(2022, Month.MAY, 5);
        System.out.println(may5th);

        LocalDate day125 = LocalDate.ofYearDay(2022, 125);
        System.out.println(day125);

        LocalDate may5 = LocalDate.parse("2022-05-05");
        System.out.println(may5);

        System.out.println(may5.getYear());
        System.out.println(may5.getMonth());

        System.out.println(may5.getMonthValue());

        System.out.println(may5.getDayOfMonth());
        System.out.println(may5.getDayOfWeek());
        System.out.println(may5.getDayOfYear());

        System.out.println(may5.get(ChronoField.YEAR));
        System.out.println(may5.get(ChronoField.MONTH_OF_YEAR));
        System.out.println(may5.get(ChronoField.DAY_OF_MONTH));
        System.out.println(may5.get(ChronoField.DAY_OF_YEAR));

        System.out.println(may5.withYear(2000));
        System.out.println(may5.withMonth(3));
        System.out.println(may5.withDayOfMonth(4));
        System.out.println(may5.withDayOfYear(126));
        System.out.println(may5.with(ChronoField.DAY_OF_YEAR, 126));
        System.out.println(may5.plusYears(1));
        System.out.println(may5.plusMonths(12));
        System.out.println(may5.plusDays(365));
        System.out.println(may5.plusWeeks(52));
        System.out.println(may5.plus(365, ChronoUnit.DAYS));

        System.out.println("May5 > today? " + may5.isAfter(today));
        System.out.println("today > May5? " + may5.isBefore(today));
        System.out.println("May5 > today? " + may5.compareTo(today));
        System.out.println("today > May5? " + today.compareTo(may5));

        System.out.println("today = now ? " + today.compareTo(LocalDate.now()));
        System.out.println("today = now ? " + today.equals(LocalDate.now()));

        System.out.println(today.isLeapYear());
        System.out.println(may5.minusYears(2).isLeapYear());
    }
}
