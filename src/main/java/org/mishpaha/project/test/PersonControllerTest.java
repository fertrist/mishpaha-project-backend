package org.mishpaha.project.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.controller.PersonController;
import org.mishpaha.project.data.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MvcConfiguration.class)
@WebAppConfiguration
public class PersonControllerTest extends BaseDaoTestClass {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private PersonController personController;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void listGroupRegionTribe() throws Exception {
        //get summary group info
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/summary/group/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)));

        List<Person> personList = getPersonsByGroup(1);

        for (int i = 0; i < personList.size(); i++) {
            //check that summary contains only few fields
            resultActions.andExpect(jsonPath(format("$[%d].*", i), hasSize(4)))
                .andExpect(jsonPath(format("$[%d].firstName", i), is(personList.get(i).getFirstName())))
                .andExpect(jsonPath(format("$[%d].lastName", i), is(personList.get(i).getLastName())))
                .andExpect(jsonPath(format("$[%d].midName", i), is(personList.get(i).getMidName())))
                .andExpect(jsonPath(format("$[%d].categoryId", i), is(personList.get(i).getCategoryId())));
        }



        //get region all info

        //get region summary info

        //get tribe all info

        //get tribe summary info
    }

}
