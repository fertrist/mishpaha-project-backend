package org.mishpaha.project.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.controller.ListPeopleController;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mishpaha.project.util.Util.assertGroupsEqual;
import static org.mishpaha.project.util.Util.assertSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MvcConfiguration.class)
@WebAppConfiguration
public class ListControllerTest extends BaseTestClass {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private ListPeopleController personController;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void listGroupSummaryTest() throws Exception {
        ResultActions resultActions;
        for (Group group : getGroups()) {
            resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/summary/group/" + group.getId()));
            assertSuccess(resultActions);
            Group result = getGroupFromResponse(resultActions);
            assertGroupsEqual(result, group, Util.View.SUMMARY);
        }
    }

    @Test
    public void listGroupInfoTest() throws Exception {
        ResultActions resultActions;
        for (Group group : getGroups()) {
            resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/info/group/" + group.getId()));
            assertSuccess(resultActions);
            Group result = getGroupFromResponse(resultActions);
            assertGroupsEqual(result, group, Util.View.INFO);
        }
    }


    @Test
    public void listGroupRegionTribeInfo() throws Exception {
        //get summary group info
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/summary/group/1"))
            .andExpect(jsonPath("$.persons", hasSize(personsPerGroup)));

        assertSuccess(resultActions);

        List<Person> personList = getPersonsByGroup(1);

        for (int i = 0; i < personList.size(); i++) {
            //check that summary contains only few fields
            resultActions.andExpect(jsonPath(format("$.persons.[%d].*", i), hasSize(8)))
                .andExpect(jsonPath(format("$.persons.[%d].id", i), is(personList.get(i).getId())))
                .andExpect(jsonPath(format("$.persons.[%d].firstName", i), is(personList.get(i).getFirstName())))
                .andExpect(jsonPath(format("$.persons.[%d].lastName", i), is(personList.get(i).getLastName())))
                .andExpect(jsonPath(format("$.persons.[%d].midName", i), is(personList.get(i).getMidName())))
                .andExpect(jsonPath(format("$.persons.[%d].categoryId", i), is(personList.get(i).getCategoryId())))
                .andExpect(jsonPath(format("$.persons.[%d].isJew", i), is(personList.get(i).isJew())))
                .andExpect(jsonPath(format("$.persons.[%d].givesTithe", i), is(personList.get(i).givesTithe())))
                .andExpect(jsonPath(format("$.persons.[%d].comment", i), is(personList.get(i).getComment())));
        }

        resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/info/group/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.persons", hasSize(personsPerGroup)));

        //get region all info

        //get region summary info

        //get tribe all info

        //get tribe summary info
    }

    private Group getGroupFromResponse(ResultActions resultActions) throws IOException {
        return Util.convertJsonToGroup(resultActions.andReturn().getResponse().getContentAsString());
    }


}
