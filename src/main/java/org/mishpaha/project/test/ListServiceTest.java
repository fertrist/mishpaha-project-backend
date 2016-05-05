package org.mishpaha.project.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * General tests for services.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfiguration.class)
@WebAppConfiguration
public class ListServiceTest extends BaseTestClass {

    @Autowired
    private ListService personService;

    @Test
    public void testPersonServiceGroup() {
        //get list of group with all info
        List<Person> actualGroupList;
        List<Person> expectedGroupList;
        for (Group group : getGroups()) {
            actualGroupList = personService.getGroupInfo(group.getId()).getPersons();
            expectedGroupList = getPersonsByGroup(group.getId());
            Assert.assertTrue(actualGroupList.equals(expectedGroupList));
        }

        //get summary group info

        //get region all info
        Region region = personService.getRegion(1);

        //get region summary info

        //get tribe all info
        Tribe tribe = personService.getTribe(1);

        //get tribe summary info

    }

//    @Test
//    public void testPersonServiceRegion() {
//
//    }
//
//    @Test
//    public void testPersonServiceTribe() {
//
//    }
}
