package org.mishpaha.project.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.service.PeopleService;
import org.mishpaha.project.util.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests related to listing persons.
 */
@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @RequestMapping(value = "/info/person/{id}")
    @JsonView(View.Summary.class)
    public Person getPerson(int id) {
        return peopleService.getPerson(id);
    }

    @RequestMapping(value = "/summary/person/{id}")
    @JsonView(View.Summary.class)
    public Person getPersonInfo(int id) {
        return peopleService.getPerson(id);
    }

    @RequestMapping(value = "/summary/group/{id}", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Group getGroup(@PathVariable int id) {
        return peopleService.getGroupSummary(id);
    }

    @RequestMapping(value = "/info/group/{id}")
    @JsonView(View.Info.class)
    public Group getGroupInfo(@PathVariable int id) {
        return peopleService.getGroupInfo(id);
    }


    @RequestMapping(value = "/summary/region/{id}", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Region getRegion(@PathVariable int id) {
        return peopleService.getRegion(id);
    }

    @RequestMapping(value = "/info/region/{id}")
    @JsonView(View.Info.class)
    public Region getRegionInfo(@PathVariable int id) {
        return peopleService.getRegion(id);
    }


    @RequestMapping(value = "/summary/tribe/{id}", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Tribe getTribe(@PathVariable int id) {
        return peopleService.getTribe(id);
    }

    @RequestMapping(value = "/info/tribe/{id}")
    @JsonView(View.Info.class)
    public Tribe getTribeInfo(@PathVariable int id) {
        return peopleService.getTribe(id);
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public int savePerson(@RequestBody Person person) {
        return peopleService.savePerson(person);
    }

    @RequestMapping(value = "/person", method = RequestMethod.PUT)
    public int updatePerson(@RequestBody Person person) {
        return peopleService.savePerson(person);
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public int deletePerson(@PathVariable int id, @RequestBody Person person) {
        return peopleService.deletePerson(id);
    }
}
