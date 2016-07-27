package org.mishpaha.project.service;

import org.apache.commons.lang.NotImplementedException;
import org.mishpaha.project.data.dao.jdbc.EventDaoImpl;
import org.mishpaha.project.data.dao.jdbc.Unit;
import org.mishpaha.project.data.dao.jdbc.Unit.Units;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.EventType;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    @Autowired
    private EventDaoImpl eventDao;
    @Autowired
    private SecurityService securityService;

    public Map<String, List<Event>> getEvents(UserDetails userDetails, LocalDate start, LocalDate end) {
        //get roles
        List<String> roles = securityService.processRoles(userDetails);
        List<Event> events = new ArrayList<>();
        //get all data
        for (String role : roles) {
            Units unitType = Util.getUnitFromRole(role);
            int id = Util.getUnitIdFromRole(role);
            Unit unit = new Group();
            if (unitType == Units.REGION) {
                unit = new Region();
            } else if (unitType == Units.TRIBE) {
                unit = new Tribe();
            }
            unit.setId(id);
            List<Event> roleEvents = eventDao.list(unit, start, end, null, null);
            events.addAll(roleEvents);
        }
        return mapEventsToPersonId(events);
    }

    private Map<String, List<Event>> mapEventsToPersonId(List<Event> events) {
        Map<String, List<Event>> map = new HashMap<>();
        events.stream().forEach(event -> {
            String key = "p_" + event.getPersonId();
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(event);
        });
        return map;
    }

    public List<Event> getGroupEvents(int groupId, LocalDate start, LocalDate end) {
        return ((EventDaoImpl) eventDao).listGroupEvents(groupId, start, end);
    }

    public List<Event> getRegionEvents(int region, LocalDate start, LocalDate end) {
        throw new NotImplementedException();
    }

    public Event save(Event event) {
        return eventDao.saveGetEvent(event);
    }

    public int delete(int id) {
        return eventDao.delete(id);
    }

    public int update(int id, String comment) {
        return eventDao.update(id, comment);
    }

    public List<EventType> getEventTypes() {
        return eventDao.listEventTypes();
    }
}
