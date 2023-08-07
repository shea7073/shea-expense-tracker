package com.expensetracker.sheaexpensetracker.logic;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Objects;

public class DateCalculator {

    public static LocalDate calculateStart(String timePeriod) {
        if (Objects.equals(timePeriod, "daily")) {
            return LocalDate.now();
        }
        else if (Objects.equals(timePeriod, "weekly")) {
            if (!Objects.equals(LocalDate.now().getDayOfWeek().toString(), "SUNDAY")) {
                return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
            }
            else {
                return LocalDate.now();
        }
        } else if (Objects.equals(timePeriod, "monthly")) {
            String stringDate = LocalDate.now().toString();
            String[] splitStringDate = stringDate.split("-", 3);
            splitStringDate[2] = "01";
            stringDate = String.join("-", splitStringDate);
            return LocalDate.parse(stringDate);
        }
        throw new IllegalArgumentException();
    }

    public static LocalDate calculateEnd(String timePeriod){
        if (Objects.equals(timePeriod, "daily")) {
            return LocalDate.now();
        }
        else if (Objects.equals(timePeriod, "weekly")) {
            if (!Objects.equals(LocalDate.now().getDayOfWeek().toString(), "SUNDAY")) {
                return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(6);
            }
            else {
                return LocalDate.now().plusDays(6);
            }
        }
        else if (Objects.equals(timePeriod, "monthly")) {
            Calendar cal = Calendar.getInstance();
            int endOfMonth = cal.getActualMaximum(Calendar.DATE);
            String stringDate = LocalDate.now().toString();
            String[] splitStringDate = stringDate.split("-", 3);
            splitStringDate[2] = Integer.toString(endOfMonth);
            stringDate = String.join("-", splitStringDate);
            return LocalDate.parse(stringDate);
        }
        throw new IllegalArgumentException();
    }

}
