package org.mishpaha.project.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fertrist on 29.10.15.
 */
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/summary/group/{id}", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public List<Person> getHomeGroup(@PathVariable int id) {
        return personService.getHomeGroupList(id);
    }

    @RequestMapping(value = "/summary/group/{id}")
    @JsonView(View.ExtendedSummary.class)
    public List<Person> getHomeGroupList(@PathVariable int id) {
        return personService.getHomeGroupList(id);
    }

    @RequestMapping(value = "/info/group/{id}")
    @JsonView(View.FullInfo.class)
    public List<Person> getHomeGroupInfoList(@PathVariable int id) {
        return personService.getHomeGroupList(id);
    }

    @JsonView(View.ExtendedSummary.class)
    public Person getPersonInfo(int id) {
        return personService.getPerson(id);
    }
}
