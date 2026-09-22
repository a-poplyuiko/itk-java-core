package com.itk.schedule;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WorkScheduleDemo {

    enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    static class WorkSheet {
        String name;
        List<DayOfWeek> workDays;

        WorkSheet(String name, List<DayOfWeek> workDays) {
            this.name = name;
            this.workDays = workDays;
        }

        String getName() {
            return name;
        }

        List<DayOfWeek> getWorkDays() {
            return workDays;
        }
    }

    public static void main(String[] args) {
        var denSheet = new WorkSheet("Денис", List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY));
        var benSheet = new WorkSheet("Бен", List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY));
        var lisSheet = new WorkSheet("Лиза", List.of(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));

        var sheets = List.of(denSheet, benSheet, lisSheet);

        // разворачиваем "человек -> дни" в "день -> сколько человек"
        Map<DayOfWeek, Long> workLoad = sheets.stream()
            .flatMap(sheet -> sheet.getWorkDays().stream())
            .collect(Collectors.groupingBy(day -> day, Collectors.counting()));

        // выводим дни, где работает больше одного человека, в порядке объявления enum
        workLoad.entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Enum::ordinal)))
            .forEach(entry -> System.out.println(entry.getKey() + " (" + entry.getValue() + ")"));
    }
}