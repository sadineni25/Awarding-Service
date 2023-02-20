package com.award.points.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static String getMonthAndYear(Date date) {

        LocalDate localdate =  date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        String monthAndDate = localdate.getMonth() + " " + localdate.getYear();
        logger.trace("Date {}, Month and year {}", date, monthAndDate);

        return monthAndDate;
    }
}
