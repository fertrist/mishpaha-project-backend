package org.mishpaha.project.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to change data related to person.
 */
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/info/person/{id}")
    @JsonView(View.Summary.class)
    public Person getPerson(int id) {
        return personService.getPerson(id);
    }

    @RequestMapping(value = "/summary/person/{id}")
    @JsonView(View.Summary.class)
    public Person getPersonInfo(int id) {
        return personService.getPerson(id);
    }




}
