package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.EventDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Report;
import org.mishpaha.project.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    @Autowired
    private GenericDao<Event> eventDao;
    @Autowired
    private GenericDao<Person> personDao;

    public List<Event> getGroupEvents(int groupId, LocalDate start, LocalDate end) {
        return ((EventDaoImpl) eventDao).list(groupId, start, end);
    }

    public Event save(Event event) {
        return eventDao.save(event) == 1 ? event : null;
    }

    public int delete(int id) {
        return eventDao.delete(id);
    }

    /**
     * TODO : process all results and group by week.
     */
    public List<Map<String, Object>> getGroupReport(int groupId, LocalDate start, LocalDate end) {


        LocalDate currentStart = start.minusDays(0);
        LocalDate nearestEnd = DateUtil.getNearestWeekEnding(start);
        //need to group results by weeks
        while(currentStart.compareTo(end) < 0) {
            currentStart = currentStart.plusWeeks(1);
            nearestEnd = nearestEnd.plusWeeks(1);

        }

        Report report = new Report();
        report.setStartRange(start);
        report.setEndRange(end);
        report.setGroupId(groupId);
        return null;
    }
}
