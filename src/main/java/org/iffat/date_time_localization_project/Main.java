package org.iffat.date_time_localization_project;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private record Employee(String name, Locale locale, ZoneId zone) {

        public Employee(String name, String locale, String zone) {
            this(name, Locale.forLanguageTag(locale), ZoneId.of(zone));
        }

        public Employee(String name, Locale locale, String zone) {
            this(name, locale, ZoneId.of(zone));
        }

        String getDateInfo(ZonedDateTime zdt, DateTimeFormatter dtf) {
            return "%s [%s] : %s".formatted(name, zone, zdt.format(dtf.localizedBy(locale)));
        }
    }

    public static void main(String[] args) {

        Employee jane = new Employee("Jane", Locale.US, "America/New_York");
        Employee joe = new Employee("Joe", "en-AU", "Asia/Jakarta");

        ZoneRules joeRules = joe.zone.getRules();
        ZoneRules janeRules = jane.zone.getRules();
        System.out.println(jane + " " + janeRules);
        System.out.println(joe + " " + joeRules);

        ZonedDateTime janeNow = ZonedDateTime.now(jane.zone);
        ZonedDateTime joeNow = ZonedDateTime.of(janeNow.toLocalDateTime(), joe.zone);
        long hoursBetween = Duration.between(joeNow, janeNow).toHours();
        long minutesBetween = Duration.between(joeNow, janeNow).toMinutesPart();
        System.out.println("Joe is " + Math.abs(hoursBetween) + " hours " +
                Math.abs(minutesBetween) + " minutes " +
                ((hoursBetween < 0) ? "behind" : "ahead"));

        System.out.println("Joe in daylight savings? " +
                joeRules.isDaylightSavings(joeNow.toInstant()) + " " +
                joeRules.getDaylightSavings(joeNow.toInstant()) + ": " +
                joeNow.format(DateTimeFormatter.ofPattern("zzzz z")));

        System.out.println("Jane in daylight savings? " +
                janeRules.isDaylightSavings(janeNow.toInstant()) + " " +
                janeRules.getDaylightSavings(janeNow.toInstant()) + ": " +
                janeNow.format(DateTimeFormatter.ofPattern("zzzz z")));

        int days = 10;
        var map = schedule(joe, jane, days);
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT);

        for (LocalDate localDate : map.keySet()) {
            System.out.println(localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
            for (ZonedDateTime zonedDateTime : map.get(localDate)) {
                System.out.println("\t" +
                        jane.getDateInfo(zonedDateTime, dtf) + " <----> " +
                        joe.getDateInfo(zonedDateTime.withZoneSameInstant(joe.zone), dtf));
            }
        }
    }

    private static Map<LocalDate, List<ZonedDateTime>> schedule(Employee first, Employee second, int days) {

        Predicate<ZonedDateTime> rules = zonedDateTime ->
                zonedDateTime.getDayOfWeek() != DayOfWeek.SATURDAY
                        && zonedDateTime.getDayOfWeek() != DayOfWeek.SUNDAY
                        && zonedDateTime.getHour() >= 7
                        && zonedDateTime.getHour() < 20;

        LocalDate startingDate = LocalDate.now().plusDays(2);

        return startingDate.datesUntil(startingDate.plusDays(days + 1))
                .map(dt -> dt.atStartOfDay(first.zone))
                .flatMap(dt -> IntStream.range(0, 24).mapToObj(dt::withHour))
                .filter(rules)
                .map(dtz -> dtz.withZoneSameInstant(second.zone))
                .filter(rules)
                .collect(
                        Collectors.groupingBy(ZonedDateTime::toLocalDate,
                                TreeMap::new, Collectors.toList())
                );
    }
}
