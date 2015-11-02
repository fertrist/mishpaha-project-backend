package org.mishpaha.project.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fertrist on 29.10.15.
 */
@RestController("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @JsonView(View.Summary.class)
    public List<Person> getHomeGroup(int groupId) {
        return personService.getHomeGroupList(groupId);
    }

    @JsonView(View.ExtendedSummary.class)
    public List<Person> getHomeGroupList(int groupId) {
        return personService.getHomeGroupList(groupId);
    }

    @JsonView(View.FullInfo.class)
    public List<Person> getHomeGroupInfoList(int groupId) {
        return personService.getHomeGroupList(groupId);
    }

    @JsonView(View.ExtendedSummary.class)
    public Person getPersonInfo(int id) {
        return personService.getPerson(id);
    }
}
