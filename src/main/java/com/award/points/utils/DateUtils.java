package com.award.points.utils;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String getMonthAndYear(Date date) {
        LocalDate localdate =  date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return localdate.getMonth() + " " + localdate.getYear();
    }
}
