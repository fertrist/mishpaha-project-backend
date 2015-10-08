package org.mishpaha.project.test;

import java.util.Calendar;
import java.util.Date;

/**
 * Class for utility methods for test purposes.
 */
public class TestUtil {

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

}
