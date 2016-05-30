package org.mishpaha.project.controller;

import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.Report;
import org.mishpaha.project.exception.DaoMistakeException;
import org.mishpaha.project.service.EventService;
import org.mishpaha.project.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Handles requests related to event tracking.
 */
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * Returns events for a given group.
     * @param id group id
     * @param start start of time range
     * @param end of time range
     */
    @RequestMapping(value = "/group/{id}", method = RequestMethod.GET)
    public List<Event> getGroupEvents(@PathVariable int id,
            @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate end) {
        end = DateUtil.setDefaultEnd(end, this.getClass());
        start = DateUtil.setDefaultStart(start, end, this.getClass());
        return eventService.getGroupEvents(id, start, end);
    }

    /**
     * Should be correspondent to list region persons functionality.
     */
    @RequestMapping(value = "/region/{id}", method = RequestMethod.GET)
    public List<Event> getRegionEvents(@PathVariable int id,
                                      @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate start,
                                      @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate end) {
        end = DateUtil.setDefaultEnd(end, this.getClass());
        start = DateUtil.setDefaultStart(start, end, this.getClass());
        return eventService.getRegionEvents(id, start, end);
    }

    @RequestMapping(value = "/event", method = RequestMethod.POST)
    public Event saveEvent(@RequestBody Event event) {
        return eventService.save(event);
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable int id) {
        eventService.delete(id);
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.PUT)
    public void updateEvent(@PathVariable int id, @RequestBody String comment) {
        eventService.update(id, comment);
    }

}
