package org.mishpaha.project.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests related to listing persons.
 */
@RestController
public class ListPeopleController {

    @Autowired
    private ListService listService;


    @RequestMapping(value = "/summary/group/{id}", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Group getGroup(@PathVariable int id) {
        return listService.getGroupSummary(id);
    }

    @RequestMapping(value = "/info/group/{id}")
    @JsonView(View.Info.class)
    public Group getGroupInfo(@PathVariable int id) {
        return listService.getGroupInfo(id);
    }


    @RequestMapping(value = "/summary/region/{id}", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Region getRegion(@PathVariable int id) {
        return listService.getRegion(id);
    }

    @RequestMapping(value = "/info/region/{id}")
    @JsonView(View.Info.class)
    public Region getRegionInfo(@PathVariable int id) {
        return listService.getRegion(id);
    }


    @RequestMapping(value = "/summary/tribe/{id}", method = RequestMethod.GET)
    @JsonView(View.Summary.class)
    public Group getTribe(@PathVariable int id) {
        return listService.getGroupSummary(id);
    }

    @RequestMapping(value = "/info/tribe/{id}")
    @JsonView(View.Info.class)
    public Group getTribeInfo(@PathVariable int id) {
        return listService.getGroupSummary(id);
    }
}
