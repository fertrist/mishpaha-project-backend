package org.mishpaha.project.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.mishpaha.project.data.dao.jdbc.Unit.Units;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for utility methods for test purposes.
 */
public class Util {

    public enum View{
        SUMMARY, INFO
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

    public static UserDetails getPrincipal(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static int getUnitIdFromRole(String role) {
        String [] split = role.split("_");
        return Integer.valueOf(split[split.length-1]);
    }

    public static Units getUnitFromRole(String role) {
        if (role.contains(Units.TRIBE.name())) {
            return Units.TRIBE;
        } else if (role.contains(Units.REGION.name())) {
            return Units.REGION;
        } else if (role.contains(Units.GROUP.name())) {
            return Units.GROUP;
        }
        return Units.GROUP;
    }
}
