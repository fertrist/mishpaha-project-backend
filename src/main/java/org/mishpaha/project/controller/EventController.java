package org.mishpaha.project.controller;

import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.service.EventService;
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
    @RequestMapping(value = "/events/group/{id}", method = RequestMethod.GET)
    public List<Event> getGroupEvents(@PathVariable int id,
            @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate end) {
        end = setDefaultEnd(end);
        start = setDefaultStart(start, end);
        return eventService.getGroupEvents(id, start, end);
    }

    @RequestMapping(value = "/events/event", method = RequestMethod.POST)
    public Event saveEvent(@RequestBody Event event) {
        return eventService.save(event);
    }

    @RequestMapping(value = "/events/event/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable int id) {
        eventService.delete(id);
    }

    @RequestMapping(value = "/reports/group/{groupId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getGroupEventReport(@PathVariable int groupId,
             @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate start,
             @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate end) {
        end = setDefaultEnd(end);
        start = setDefaultStart(start, end);
        return eventService.getGroupReport(groupId, start, end);
    }

    private LocalDate setDefaultStart(LocalDate start, LocalDate end) {
        if (start == null || start.compareTo(end) >= 0) {
            start = end.minusMonths(monthsPast);
        }
        return start;
    }

    private LocalDate setDefaultEnd(LocalDate end) {
        if (end == null) {
            end = LocalDate.now().plusWeeks(weeksFuture);
        }
        return end;
    }
}
