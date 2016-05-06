package org.mishpaha.project.controller;

import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Handles requests related to event tracking.
 */
@RestController
public class EventController {

    public static final int monthsPast = 1;
    public static final int weeksFuture = 2;


    @Autowired
    private EventService eventService;

    /**
     * Returns events for a given group.
     * @param id group id
     * @param start start of time range
     * @param end of time range
     */
    @RequestMapping("/events/group/{id}")
    public List<Event> getGroupEvents(@PathVariable int id,
        @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate start,
        @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate end) {
        LocalDate startDate = null;
        if (start != null && end != null && !(start.compareTo(end) < 0)) {
            start = LocalDate.now().minusMonths(monthsPast);
            end = LocalDate.now().plusWeeks(weeksFuture);
        }
        if (start == null) {
            start = LocalDate.now().minusMonths(monthsPast);
        }
        if (end == null) {
            end = LocalDate.now().plusWeeks(weeksFuture);
        }
        return eventService.getGroupEvents(id, start, end);
    }

}
