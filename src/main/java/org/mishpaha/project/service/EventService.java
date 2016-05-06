package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.EventDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private GenericDao<Event> eventDao;

    public List<Event> getGroupEvents(int groupId, LocalDate start, LocalDate end) {
        return ((EventDaoImpl) eventDao).list(groupId, start, end);
    }

}
