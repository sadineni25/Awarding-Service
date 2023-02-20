package com.award.points.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * DateUtils class provide utility logic on java date objects.
 *
 */
@Component
public class DateUtils {

    /**
     * Logger to log messages to console and log file.
     */
    static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * Get month and year for given date.
     *
     * @param  date   given date
     * @return        month and year of given date in string format.
     */
    public static String getMonthAndYear(Date date) {

        LocalDate localdate =  date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        String monthAndDate = localdate.getMonth() + " " + localdate.getYear();
        logger.trace("Date {}, Month and year {}", date, monthAndDate);

        return monthAndDate;
    }
}
