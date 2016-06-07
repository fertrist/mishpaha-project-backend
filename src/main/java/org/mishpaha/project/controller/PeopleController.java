package org.mishpaha.project.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.service.PeopleService;
import org.mishpaha.project.util.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.mishpaha.project.config.Constants.*;

/**
 * Handles requests related to listing persons.
 */
@RestController
@RequestMapping(PEOPLE_BASE)
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    /**
     * Get all lists that are allowed for a user.
     */
    @RequestMapping(value = STAFF_INFO, method = RequestMethod.GET)
    @JsonView(View.Info.class)
    public List<Tribe> getStaffInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return peopleService.getStaff(userDetails);
    }

    /**
     * Get all lists that are allowed for a user.
     */
    @RequestMapping(value = STAFF_SUMMARY, method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public List<Tribe> getStaffSummary(@AuthenticationPrincipal UserDetails userDetails) {
        return peopleService.getStaff(userDetails);
    }

    @RequestMapping(value = GROUP_SUMMARY, method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Group getGroup(@PathVariable Integer id) {
        return peopleService.getGroupSummary(id);
    }

    @RequestMapping(value = GROUP_INFO)
    @JsonView(View.Info.class)
    public Group getGroupInfo(@PathVariable Integer id) {
        return peopleService.getGroup(id);
    }


    @RequestMapping(value = REGION_SUMMARY, method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Region getRegion(@PathVariable Integer id) {
        return peopleService.getRegion(id);
    }

    @RequestMapping(value = REGION_INFO)
    @JsonView(View.Info.class)
    public Region getRegionInfo(@PathVariable Integer id) {
        return peopleService.getRegion(id);
    }


    @RequestMapping(value = TRIBE_SUMMARY, method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Tribe getTribe(@PathVariable Integer id) {
        return peopleService.getTribe(id);
    }

    @RequestMapping(value = TRIBE_INFO)
    @JsonView(View.Info.class)
    public Tribe getTribeInfo(@PathVariable Integer id) {
        return peopleService.getTribe(id);
    }

    @RequestMapping(value = PERSON, method = RequestMethod.POST)
    public int savePerson(@RequestBody Person person) {
        return peopleService.savePerson(person);
    }

    @RequestMapping(value = PERSON, method = RequestMethod.PUT)
    public int updatePerson(@RequestBody Person person) {
        return peopleService.savePerson(person);
    }

    @RequestMapping(value = PERSON_ID, method = RequestMethod.DELETE)
    public int deletePerson(@PathVariable Integer id, @RequestBody Person person) {
        return peopleService.deletePerson(id);
    }

}
