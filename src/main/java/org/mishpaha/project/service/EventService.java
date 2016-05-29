package org.mishpaha.project.service;

import org.apache.commons.lang.NotImplementedException;
import org.mishpaha.project.data.dao.EventDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventDaoImpl eventDao;

    public List<Event> getGroupEvents(int groupId, LocalDate start, LocalDate end) {
        return ((EventDaoImpl) eventDao).list(groupId, start, end);
    }

    public List<Event> getRegionEvents(int region, LocalDate start, LocalDate end) {
        throw new NotImplementedException();
    }

    public Event save(Event event) {
        return eventDao.save(event) == 1 ? event : null;
    }

    public int delete(int id) {
        return eventDao.delete(id);
    }

    public int update(int id, String comment) {
        return eventDao.update(id, comment);
    }
}
