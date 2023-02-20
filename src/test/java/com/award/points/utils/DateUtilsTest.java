package com.award.points.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DateUtilsTest {

    @Autowired
    private DateUtils dateUtils;


    @Test
    public void testMonthAndYear() {
        Calendar c = Calendar.getInstance();
        c.set(2000, Calendar.JANUARY, 3);
        assertEquals("JANUARY 2000", DateUtils.getMonthAndYear(c.getTime()));
        c.set(2023, Calendar.DECEMBER, 3);
        assertEquals("DECEMBER 2023", DateUtils.getMonthAndYear(c.getTime()));
    }
}