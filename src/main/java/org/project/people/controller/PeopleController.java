package org.project.people.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.project.people.data.entity.Person;
import org.project.people.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class PeopleController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/people", method = GET)
    List<Person> getAllPeople() {
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            persons.add(new Person(i, "PersonName-" + i, "PersonMidname-" + i, "PersonLastname-" + i,
                (i % 2 == 0), new Date(), (i %2 == 0), (i %2 == 0), i % 4, "Comment-" + i));
        }
        return persons;
    }

    @RequestMapping(value = "/people/all", method = GET)
    List<Person> getPeopleList() {
        return personService.listPeople();
    }

    @RequestMapping(value = "/people/{id}/info", method = GET)
    @JsonView(View.Info.class)
    Person getPersonInfo(@PathVariable int id) {
        return personService.get(id);
    }

    @RequestMapping(value = "/people/{id}", method = GET)
    @JsonView(View.Summary.class)
    Person getPersonSummary(@PathVariable int id) {
        return personService.get(id);
    }

    @RequestMapping(value = "/people", method = POST)
    @ResponseStatus(HttpStatus.OK)
    void save(@RequestBody Person newPerson) {
        personService.save(newPerson);
    }

    @RequestMapping(value = "/people/{id}", method = DELETE)
    @ResponseStatus(HttpStatus.OK)
    void deletePerson(@PathVariable int id) {
        personService.delete(id);
    }

}
