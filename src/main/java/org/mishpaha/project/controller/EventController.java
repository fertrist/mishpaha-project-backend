package org.mishpaha.project.controller;

import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.EventType;
import org.mishpaha.project.service.EventService;
import org.mishpaha.project.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mishpaha.project.config.Constants.*;

/**
 * Handles requests related to event tracking.
 */
@RestController
@RequestMapping(EVENTS_BASE)
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, List<Event>> getEvents(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate start,
                                  @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate end) {
        end = DateUtil.setDefaultEnd(end, this.getClass());
        start = DateUtil.setDefaultStart(start, end, this.getClass());
        return eventService.getEvents(userDetails, start, end);
    }
    /**
     * Returns events for a given group.
     * @param id group id
     * @param start start of time range
     * @param end of time range
     */
    @RequestMapping(value = GROUP_ID, method = RequestMethod.GET)
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
    @RequestMapping(value = REGION_ID, method = RequestMethod.GET)
    public List<Event> getRegionEvents(@PathVariable int id,
                                      @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate start,
                                      @RequestParam(required = false) @DateTimeFormat(iso= ISO.DATE) LocalDate end) {
        end = DateUtil.setDefaultEnd(end, this.getClass());
        start = DateUtil.setDefaultStart(start, end, this.getClass());
        return eventService.getRegionEvents(id, start, end);
    }

    @RequestMapping(value = EVENT, method = RequestMethod.POST)
    public Event saveEvent(@RequestBody Event event) {
        return eventService.save(event);
    }

    @RequestMapping(value = EVENT_ID, method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable int id) {
        eventService.delete(id);
    }

    @RequestMapping(value = EVENT_ID, method = RequestMethod.PUT)
    public void updateEvent(@PathVariable int id, @RequestBody String comment) {
        eventService.update(id, comment);
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public List<EventType> getEventTypes() {
        return eventService.getEventTypes();
    }

}
