package org.mishpaha.project.service;

import org.apache.commons.lang.NotImplementedException;
import org.mishpaha.project.data.dao.jdbc.EventDaoImpl;
import org.mishpaha.project.data.dao.jdbc.EventDaoImpl.EventTypes;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.jdbc.PersonDaoImpl;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Report;
import org.mishpaha.project.exception.DaoMistakeException;
import org.mishpaha.project.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mishpaha.project.data.dao.jdbc.CategoryDaoImpl.Categories.blue;
import static org.mishpaha.project.data.dao.jdbc.CategoryDaoImpl.Categories.brown;
import static org.mishpaha.project.data.dao.jdbc.CategoryDaoImpl.Categories.green;
import static org.mishpaha.project.data.dao.jdbc.CategoryDaoImpl.Categories.guest;
import static org.mishpaha.project.data.dao.jdbc.CategoryDaoImpl.Categories.white;
import static org.mishpaha.project.data.dao.jdbc.EventDaoImpl.ReportFields.count;
import static org.mishpaha.project.data.dao.jdbc.EventDaoImpl.ReportFields.happened;
import static org.mishpaha.project.data.dao.jdbc.EventDaoImpl.ReportFields.listed;
import static org.mishpaha.project.data.dao.jdbc.EventDaoImpl.ReportFields.type;

@Service
public class ReportService {

    @Autowired
    private EventDaoImpl eventDao;
    @Autowired
    private GenericDao<Person> personDao;
    @Autowired
    private SecurityService securityService;

    public List<Report> getReport(UserDetails userDetails, LocalDate start, LocalDate end) {
        Set<String> roles = new HashSet<>();
        List<Report> reports = new ArrayList<>();
        List<Integer> groupIds = securityService.getGroupsFromRoles(userDetails);
        for (Integer id : groupIds) {
            try {
                reports.add(getGroupReport(id, start, end));
            } catch (DaoMistakeException e) {
                e.printStackTrace();
            }
        }
        return reports;
    }

    /**
     * TODO : process all results and group by week.
     */
    public Report getGroupReport(int groupId, LocalDate start, LocalDate end) throws DaoMistakeException {

        //get group listGroupEvents
        List<Person> groupList = ((PersonDaoImpl) personDao).listGroup(groupId);

        //get group meeting events
        List<Event> groupMeetings = eventDao.getGroupMeetings(groupId, start, end);

        //get calls visits meetings
        List<Map<String, Object>> weekCounts = eventDao.getCallsMeetingsVisits(groupId, start, end);

        //need to group results by weeks
        Report report = new Report();
        report.setStartRange(start);
        report.setEndRange(end);
        report.setGroupId(groupId);

        LocalDate currentStart = start.minusDays(0);
        LocalDate nearestEnd = DateUtil.getNearestWeekEnding(start.plusWeeks(1)).plusDays(1);
        int indxGrp = 0;
        int indxCnt = 0;

        //iterate over each week
        while(currentStart.compareTo(end) < 0) {

            //sort group meeting info
            List<String> newbies = new ArrayList<>();
            List<String> guests = new ArrayList<>();
            Map<String, Boolean> mapListed = getListed(groupList);

            //look through group meetings which happened during a week
            while (indxGrp < groupMeetings.size()
                && groupMeetings.get(indxGrp).getHappened().isBefore(nearestEnd)) {

                Person person = getPersonById(groupMeetings.get(indxGrp).getPersonId(), groupList);
                if (person == null) {
                    throw new DaoMistakeException(eventDao.getClass().getSimpleName() + "#getGroupMeetings() " +
                        "returned an event with personId which not belongs to a group.");
                }
                String name = getSimpleName(person);
                int categoryId = person.getCategoryId() - 1;
                if (categoryId == white.ordinal()) {
                    mapListed.put(name, true);
                } else if (categoryId == blue.ordinal() || categoryId == green.ordinal() || categoryId == brown.ordinal()) {
                    newbies.add(name);
                } else if (categoryId == guest.ordinal()) {
                    guests.add(name);
                }
                indxGrp++;
            }
            //look through counters which correspond to a week
            int meetingsNewbies = 0, visitsNewbies = 0, visitsListed = 0, meetingsListed = 0, calls = 0;
            while (indxCnt < weekCounts.size()) {

                Map<String, Object> row = weekCounts.get(indxCnt);
                LocalDate date = DateUtil.fromDate((Date)row.get(happened.name()));
                if (!(date.isBefore(nearestEnd) && date.isAfter(currentStart))
                    && !date.equals(nearestEnd) && !date.equals(currentStart)) {
                    break;
                }
                String eventTypeValue = (String)row.get(type.name());
                String categoryValue = (String)row.get(listed.name());
                int countValue = ((Long) row.get(count.name())).intValue();
                //sort by category and event type
                boolean white = categoryValue.equalsIgnoreCase("yes");
                boolean visit = eventTypeValue.equalsIgnoreCase(EventTypes.visit.name());
                boolean meeting = eventTypeValue.equalsIgnoreCase(EventTypes.meeting.name());
                boolean call = eventTypeValue.equalsIgnoreCase(EventTypes.call.name());
                if (white && visit) visitsListed += countValue;
                else if (white && meeting) meetingsListed += countValue;
                else if (!white && call) calls += countValue;
                else if (visit) visitsNewbies += countValue;
                else if (meeting) meetingsNewbies += countValue;
                indxCnt++;
            }

            report.addWeekRecord(currentStart, nearestEnd.minusDays(1), mapListed, newbies, guests, meetingsNewbies,
                visitsNewbies, meetingsListed, visitsListed, calls);

            //shift to a next week
            currentStart = currentStart.plusWeeks(1);
            nearestEnd = nearestEnd.plusWeeks(1);
        }

        return report;
    }

    public List<Report> getRegionReport(int groupId, LocalDate start, LocalDate end) {
        throw new NotImplementedException();
    }

    private Person getPersonById(int personId, List<Person> persons) {
        for(Person person : persons) {
            if (person.getId() == personId) {
                return person;
            }
        }
        return null;
    }

    private String getSimpleName(Person person) {
        return person.getFirstName() + " " + person.getMidName();
    }

    private Map<String, Boolean> getListed(List<Person> persons) {
        Map<String, Boolean> map = new HashMap<>();
        for (Person person : persons) {
            if (person.getCategoryId() == white.ordinal() + 1) {
                map.put(person.getFirstName() + " " + person.getMidName(), false);
            }
        }
        return map;
    }
}
