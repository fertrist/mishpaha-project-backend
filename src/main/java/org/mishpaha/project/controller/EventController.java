package org.mishpaha.project.controller;

import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Handles requests related to event tracking.
 */
@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * Returns events for a given group.
     * @param id group id
     * @param start start of time range
     * @param end of time range
     */
    @RequestMapping("/events/group/{id}")
    public List<Event> getGroupEvents(@PathVariable int id, @RequestParam(required = false) GregorianCalendar start,
                                      @RequestParam(required = false) GregorianCalendar end) {
        if (start != null && end != null && !(start.compareTo(end) < 0)) {
            start = (GregorianCalendar) GregorianCalendar.getInstance();
            start.add(Calendar.MONTH, -1);
            end = (GregorianCalendar) GregorianCalendar.getInstance();
            end.add(Calendar.WEEK_OF_YEAR, 2);
        }
        if (start == null) {
            start = (GregorianCalendar) GregorianCalendar.getInstance();
            start.add(Calendar.MONTH, -1);
        }
        if (end == null) {
            end = (GregorianCalendar) GregorianCalendar.getInstance();
            end.add(Calendar.WEEK_OF_YEAR, 2);
        }
        List<Event> events = eventService.getGroupEvents(id, start.getTime(), end.getTime());
        return events;
    }

}
