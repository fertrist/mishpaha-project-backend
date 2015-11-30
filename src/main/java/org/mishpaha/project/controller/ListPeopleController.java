package org.mishpaha.project.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.service.ListPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fertrist on 29.10.15.
 */
@RestController
public class ListPeopleController {

    @Autowired
    private ListPeopleService listPeopleService;

    @RequestMapping(value = "/group/{id}/summary", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Group getGroup(@PathVariable int id) {
        return listPeopleService.getGroup(id);
    }

    @RequestMapping(value = "/group/{id}/info")
    @JsonView(View.Info.class)
    public Group getGroupInfo(@PathVariable int id) {
        return listPeopleService.getGroup(id);
    }

    @RequestMapping(value = "/person/{id}/info")
    @JsonView(View.Summary.class)
    public Person getPerson(int id) {
        return listPeopleService.getPerson(id);
    }

    @RequestMapping(value = "/person/{id}/summary")
    @JsonView(View.Summary.class)
    public Person getPersonInfo(int id) {
        return listPeopleService.getPerson(id);
    }

    @RequestMapping(value = "/region/{id}/summary", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Region getRegion(@PathVariable int id) {
        return listPeopleService.getRegion(id);
    }

    @RequestMapping(value = "/region/{id}/info")
    @JsonView(View.Info.class)
    public Region getRegionInfo(@PathVariable int id) {
        return listPeopleService.getRegion(id);
    }

    @RequestMapping(value = "/tribe/{id}/summary", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Group getTribe(@PathVariable int id) {
        return listPeopleService.getGroup(id);
    }

    @RequestMapping(value = "/tribe/{id}/info")
    @JsonView(View.Info.class)
    public Group getTribeInfo(@PathVariable int id) {
        return listPeopleService.getGroup(id);
    }
}
