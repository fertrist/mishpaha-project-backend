package org.mishpaha.project.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * To simplify testing.
 */
public class TestUtil {

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
