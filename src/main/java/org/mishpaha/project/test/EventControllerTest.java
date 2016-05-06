package org.mishpaha.project.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.controller.EventController;
import org.mishpaha.project.data.dao.EventDaoImpl;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;
import static org.mishpaha.project.util.Util.assertSuccess;
import static org.mishpaha.project.util.Util.convertObjectToJson;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MvcConfiguration.class)
@WebAppConfiguration
public class EventControllerTest extends BaseTestClass{

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private EventController eventController;
    @Autowired
    private EventDaoImpl eventDao;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddRemoveEvent() throws Exception {
        ResultActions resultActions;
        Event event;
        Event fromResponse;
        Event fromDb;
        int lastId = getEvents().size();
        int n = 1000;
        long startTime = System.currentTimeMillis();
        Group group = getGroups().get(0);
        for (int i = 0; i < n; i++) {
            event = new Event(++lastId, group.getPersons().get(0).getId(), group.getId(), 1, LocalDate.of(2016, 5, 1));
            String body = convertObjectToJson(event);
            resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/events")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(body));
            resultActions.andExpect(status().isOk());
            fromResponse = getEventFromResponse(resultActions);
            Assert.assertEquals(event, fromResponse);
            fromDb = eventDao.get(lastId);
            Assert.assertEquals(event, fromDb);

            //remove event
            mockMvc.perform(MockMvcRequestBuilders.delete("/events/" + lastId)).andExpect(status().isOk());
            fromDb = eventDao.get(lastId);
            Assert.assertNull(fromDb);
        }
        System.out.printf("Handling of %d requests took %f seconds\n",
            n, (double)(System.currentTimeMillis() - startTime)/1000);
    }

    @Test
    public void testGroupEvents() throws Exception {
        int groupId = 1;
        LocalDate start = LocalDate.now().minusMonths(EventController.monthsPast).minusDays(1);
        LocalDate end = LocalDate.now().plusWeeks(EventController.weeksFuture).plusDays(1);

        //default time range
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/events/group/" + groupId));
        assertSuccess(resultActions);
        List<Event> events = getEventsFromResponse(resultActions);
        LocalDate happened;
        for (Event event : events) {
            Assert.assertEquals(groupId, event.getGroupId());
            happened = event.getHappened();
            Assert.assertTrue(happened.isAfter(start) && happened.isBefore(end));
        }

        //custom start end
        groupId = 2;
        start = start.minusMonths(2);
        end = end.minusWeeks(2);
        resultActions = mockMvc.perform(MockMvcRequestBuilders.get(
            format("/events/group/%d?start=%s&end=%s", groupId, start.toString(), end.toString())));
        start = start.minusDays(1);
        end = end.plusDays(1);
        assertSuccess(resultActions);
        events = getEventsFromResponse(resultActions);
        for (Event event : events) {
            Assert.assertEquals(groupId, event.getGroupId());
            happened = event.getHappened();
            Assert.assertTrue(happened.isAfter(start) && happened.isBefore(end));
        }
    }

    private static List<Event> getEventsFromResponse(ResultActions resultActions) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(resultActions.andReturn().getResponse().getContentAsString(),
            TypeFactory.defaultInstance().constructCollectionType(List.class, Event.class));
    }

    public static Event getEventFromResponse(ResultActions resultActions) throws IOException {
        String content = resultActions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(content, Event.class);
    }
}
