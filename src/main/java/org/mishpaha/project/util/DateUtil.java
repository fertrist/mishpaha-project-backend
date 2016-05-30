package org.mishpaha.project.util;

import org.junit.Test;
import org.mishpaha.project.config.Constants;
import org.mishpaha.project.controller.EventController;
import org.mishpaha.project.controller.ReportController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateUtil {

    private static final DayOfWeek FIRST_DAY_OF_WEEK = DayOfWeek.MONDAY;
    private static final DayOfWeek LAST_DAY_OF_WEEK = DayOfWeek.SUNDAY;

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        month--;
        cal.set(year, month, day);
        //trim extra data
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getDate(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateAsString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    public static String getDateAsQuotedString(LocalDate date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = simpleDateFormat.format(date);
        return String.format("'%s'", strDate);
    }

    public static LocalDate fromDate(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    public static LocalDate getNearestWeekBeginning(LocalDate current) {
        boolean desc = current.getDayOfWeek().compareTo(DayOfWeek.THURSDAY) <= 0;
        while (current.getDayOfWeek() != FIRST_DAY_OF_WEEK) {
            current = desc ? current.minusDays(1) : current.plusDays(1);
        }
        return current;
    }

    public static LocalDate getNearestWeekEnding(LocalDate current) {
        boolean asc = current.getDayOfWeek().compareTo(DayOfWeek.THURSDAY) > 0;
        while (current.getDayOfWeek() != LAST_DAY_OF_WEEK) {
            current = asc ? current.plusDays(1) : current.minusDays(1);
        }
        return current;
    }

    public static LocalDate setDefaultStart(LocalDate start, LocalDate end, Class c) {
        if (start == null || start.compareTo(end) >= 0) {
            int past = Constants.EVENTS_DEFAULT_MONTH_PAST;
            if (c.equals(ReportController.class)) {
                past = Constants.REPORT_DEFAULT_MONTH_PAST;
            }
            start = end.minusMonths(past);
        }
        start = DateUtil.getNearestWeekBeginning(start);
        return start;
    }

    public static LocalDate setDefaultEnd(LocalDate end, Class c) {
        if (end == null) {
            end = LocalDate.now();
            if (c.equals(EventController.class)) {
                end = end.plusWeeks(Constants.EVENTS_DEFAULT_WEEKS_FUTURE);
            }
        }
        end = DateUtil.getNearestWeekEnding(end);
        return end;
    }

    @Test
    public void testDefaultDates() {
        Class eventClass = EventController.class;
        Class reportClass = ReportController.class;

        //custom dates events
        LocalDate start = LocalDate.of(2016, 4, 29);
        LocalDate end = LocalDate.of(2016, 5, 24);
        end = setDefaultEnd(end, eventClass);
        start = setDefaultStart(start, end, eventClass);
        assertEquals(LocalDate.of(2016, 5, 2), start);
        assertEquals(LocalDate.of(2016, 5, 22), end);

        //custom dates report
        start = LocalDate.of(2016, 4, 29);
        end = LocalDate.of(2016, 5, 24);
        end = setDefaultEnd(end, reportClass);
        start = setDefaultStart(start, end, reportClass);
        System.out.println();
        assertEquals(LocalDate.of(2016, 5, 2), start);
        assertEquals(LocalDate.of(2016, 5, 22), end);

        //default dates events
        start = null; end = null;
        end = setDefaultEnd(end, eventClass);
        start = setDefaultStart(start, end, eventClass);
        assertEquals(DayOfWeek.MONDAY, start.getDayOfWeek());
        assertEquals(DayOfWeek.SUNDAY, end.getDayOfWeek());
        assertTrue(start.plusMonths(1).isBefore(end));

        //default dates report
        start = null; end = null;
        end = setDefaultEnd(end, reportClass);
        start = setDefaultStart(start, end, reportClass);
        assertEquals(DayOfWeek.MONDAY, start.getDayOfWeek());
        assertEquals(DayOfWeek.SUNDAY, end.getDayOfWeek());
        assertTrue(start.plusMonths(2).plusWeeks(2).isBefore(end));

        //start after end events
        start = LocalDate.of(2016, 5, 29);
        end = LocalDate.of(2016, 4, 29);
        end = setDefaultEnd(end, eventClass);
        start = setDefaultStart(start, end, eventClass);
        assertEquals(DayOfWeek.MONDAY, start.getDayOfWeek());
        assertEquals(DayOfWeek.SUNDAY, end.getDayOfWeek());
        assertEquals(LocalDate.of(2016, 4, 4), start);
        assertEquals(LocalDate.of(2016, 5, 1), end);

        //start after end report
        start = LocalDate.of(2016, 5, 29);
        end = LocalDate.of(2016, 4, 29);
        end = setDefaultEnd(end, reportClass);
        start = setDefaultStart(start, end, reportClass);
        assertEquals(DayOfWeek.MONDAY, start.getDayOfWeek());
        assertEquals(DayOfWeek.SUNDAY, end.getDayOfWeek());
        assertEquals(LocalDate.of(2016, 2, 1), start);
        assertEquals(LocalDate.of(2016, 5, 1), end);

    }
}
