package org.mishpaha.project.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for utility methods for test purposes.
 */
public class TestUtil {

    public enum View{
        SUMMARY, INFO
    }

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

    public static String getDateAsQuotedString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = simpleDateFormat.format(date);
        return String.format("'%s'", strDate);
    }

    public static String getQuotedString(String str) {
        return String.format("'%s'", str);
    }


    /**
     * Transforms boolean to string according to mySql rules.
     */
    public static String getBool(boolean value) {
        String DB = "mysql";
        if (DB.equals("mysql")) {
            return String.valueOf(value ? 1 : 0);
        }
        return String.valueOf(value);
    }

    public static Group getGroupFromResponse(ResultActions resultActions) throws IOException {
        String content = resultActions.andReturn().getResponse().getContentAsString();
        return convertJsonToGroup(content);
    }

    public static void assertSuccess(ResultActions resultActions) throws Exception {
        resultActions
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    public static void assertPersonListsEqual(List<Person> actual, List<Person> expected, View view) {
        assertEquals("Sizes are different.", actual.size(), expected.size());
        for (int i = 0; i < actual.size(); i++) {
            assertPersonsEqual(actual.get(i), expected.get(i), view);
        }
    }

    public static void assertPersonsEqual(Person actual, Person expected, View view) {
        assertEquals("Person id's are different.", actual.getId(), expected.getId());
        assertEquals("First names are different.", actual.getFirstName(), expected.getFirstName());
        assertEquals("Middle names are different.", actual.getMidName(), expected.getMidName());
        assertEquals("Last names are different.", actual.getLastName(), expected.getLastName());
        assertEquals("Categories are different.", actual.getCategoryId(), expected.getCategoryId());

        boolean v = view == View.INFO;
        assertEquals("Sex is different.", actual.getSex(), v ? expected.getSex() : null);
        assertEquals("Addresses are different.", actual.getAddress(), v ? expected.getAddress() : null);
        assertEquals("Birthdays are different.", actual.getBirthDay(), v ? expected.getBirthDay() : null);
        assertEquals("Phones are different.", actual.getPhones(), v ? expected.getPhones() : null);
        assertEquals("Emails are different.", actual.getEmails(), v ? expected.getEmails() : null);
    }

    public static void assertGroupsEqual(Group actual, Group expected, View view) {
        Assert.assertEquals("Id's are different.", actual.getId(), expected.getId());
        Assert.assertEquals("Leader id's are different.", actual.getLeaderId(), expected.getLeaderId());
        Assert.assertEquals("Region id's are different.", actual.getRegionId(), expected.getRegionId());
        Assert.assertEquals("Leaders are different.", actual.getLeader(), expected.getLeader());
        assertPersonListsEqual(actual.getPersons(), expected.getPersons(), view);
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String str = mapper.writeValueAsString(object);
        System.out.println("Converted object to string : " + str);
        return str;
    }

    public static Group convertJsonToGroup(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Group.class);
    }
}
