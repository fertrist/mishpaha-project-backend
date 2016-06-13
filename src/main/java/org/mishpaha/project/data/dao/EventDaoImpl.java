package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.EventType;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isNotEmpty;
import static org.apache.commons.lang.StringUtils.join;
import static org.mishpaha.project.util.ModelUtil.getTable;
import static org.mishpaha.project.util.Util.getQuotedString;

public class EventDaoImpl extends DaoImplementation<Event>{

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDaoImpl.class);

    public enum EventTypes{
        meeting, visit, call, group, club, common
    }

    public enum ReportFields {
        type, listed, happened, week, count
    }

    public EventDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    public static final Map<String, String> reportFields;
    static {
        reportFields = new HashMap<>();
        reportFields.put("eventType", "typeId");
        reportFields.put("category", "categoryId");
        reportFields.put("count", "count");
        reportFields.put("date", "happened");

    }

    public Event saveGetEvent(Event event) {
        String sql = format(INSERT, table, "personId, groupId, typeId, happened",
            format("%d,%d,%d,%s", event.getPersonId(), event.getGroupId(), event.getTypeId(),
                getQuotedString(event.getHappened().toString())));
        LOGGER.info(sql);
        operations.update(sql);
        return get(event);
    }

    @Override
    public int save(Event entity) {
        String sql = format(INSERT, table, "personId, groupId, typeId, happened",
            format("%d,%d,%d,%s", entity.getPersonId(), entity.getGroupId(), entity.getTypeId(),
                getQuotedString(entity.getHappened().toString())));
        LOGGER.info(sql);
        return operations.update(sql);
    }

    public Event get(Event event) {
        String sql = "SELECT id, typeId, personId, groupId, happened, comment FROM " + table
            + " WHERE typeId=" + event.getTypeId() + " AND personId=" + event.getPersonId()
            + " AND happened=" + getQuotedString(event.getHappened().toString());
        LOGGER.info(sql);
        return operations.query(sql, rs -> {
            if (rs.next()) {
                return getEvent(rs);
            }
            return null;
        });
    }

    @Override
    public int update(Event entity) {
        throw new UnsupportedOperationException();
    }

    public int update(int id, String comment) {
        return operations.update(format(UPDATE, table, "comment=" + comment), id);
    }

    @Override
    public Event get(int id) {
        return operations.query(format(SELECT, table, id), rs -> {
            if (rs.next()) {
                return getEvent(rs);
            }
            return null;
        });
    }

    @Override
    public List<Event> list() { throw new UnsupportedOperationException();}

    public List<Event> listGroupEvents(int groupId, LocalDate start, LocalDate end) {
        return list(new Group(groupId), start, end, null, null);
    }

    /**
     * List event based on filter params.
     */
    public List<Event> list(Unit unit, LocalDate start, LocalDate end,
                                          String[] eventTypes, String[] categories) {
        String sql = getFilterRequest(unit, start, end, eventTypes, categories);
        sql += " ORDER BY e.happened";
        return operations.query(sql, (rs, rowNum) -> {
            return getEvent(rs);
        });
    }

    public List<Event> getGroupMeetings(int groupId, LocalDate start, LocalDate end) {
        return list(new Group(groupId), start, end, new String[]{"group"}, null);
    }

    /**
     * visits : blue, brown, green categories
     * visits : white category
     * meetings : blue, brown, green categories
     * meetings : white category
     * calls : blue, brown, green categories
     * calls : white category
     */
    public List<Map<String, Object>> getCallsMeetingsVisits(int groupId, LocalDate start, LocalDate end) {
        String joinEventTypes = "JOIN " + getTable(EventType.class) + " et ON e.typeId=et.id ";
        String joinPersons = "JOIN " + getTable(Person.class) + " p ON e.personId=p.id ";
        String joinPersonsCategories = joinPersons + "JOIN " + getTable(Category.class) + " c ON p.categoryId=c.id ";

        //select needed subtable
        String sql = "SELECT et.type, CASE WHEN c.name='white' THEN 'yes' ELSE 'no' END as listed, "
            + "e.happened, WEEK(e.happened) as week, count(e.id) as count "
            + "FROM " + table + " e " + joinEventTypes + joinPersonsCategories
            + "WHERE e.groupId=" + groupId + " AND (e.happened BETWEEN "
            + getQuotedString(start.toString()) + " AND " + getQuotedString(end.toString()) + ") "
            + "AND (et.type='visit' OR et.type='meeting' OR et.type='call') "
            + "AND (c.name='blue' OR c.name='green' OR c.name='white' OR c.name='brown') "
            + "GROUP BY et.type, listed, week, e.happened ORDER BY week, happened";
        //aggregate data
        return operations.queryForList(sql);
    }

    /**
     * Save event type.
     */
    public int saveEventType(String eventType) {
        String sql = format(INSERT, getTable(EventType.class), "type",
            getQuotedString(eventType));
        LOGGER.info(sql);
        return operations.update(sql);
    }

    /**
     * Remove event type.
     */
    public int deleteEventType(String eventType) {
        return operations.update(format("DELETE FROM %s WHERE type=%s", getTable(EventType.class),
            getQuotedString(eventType)));
    }

    /**
     * List all event types.
     */
    public List<EventType> listEventTypes() {
        return operations.query("SELECT * FROM eventTypes", (rs, rowNum) -> {
           return new EventType(rs.getInt("id"), rs.getString("type"));
        });
    }

    /**
     * Creates event instance from result set.
     */
    private Event getEvent(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setTypeId(rs.getInt("typeId"));
        event.setPersonId(rs.getInt("personId"));
        event.setGroupId(rs.getInt("groupId"));
        event.setHappened(DateUtil.fromDate(rs.getDate("happened")));
        event.setComment(rs.getString("comment"));
        return event;
    }

    /**
     * Creates sql request which contains options regarding event type and category.
     * @param unit group or region
     * @param start start range
     * @param end end range
     * @param eventTypes include following types of events
     * @param categories include following categories of people
     * @return generated request
     */
    private String getFilterRequest(Unit unit, LocalDate start, LocalDate end, String[] eventTypes,
                                    String[] categories) {
        String eventsTbl = getTable(Event.class);

        String joinEventTypes = "JOIN " + getTable(EventType.class) + " et ON e.typeId=et.id ";

        String joinPersons = "JOIN " + getTable(Person.class) + " p ON e.personId=p.id ";

        String joinPersonsCategories = categories != null && categories.length > 0 ?
            joinPersons + "JOIN " + getTable(Category.class) + " c ON p.categoryId=c.id " : "";

        String joinUnits = "";
        if (unit.getUnit() == Unit.Units.REGION || unit.getUnit() == Unit.Units.TRIBE) {
            joinUnits = "JOIN " + getTable(Group.class) + " g ON e.groupId=g.id ";
        }
        if (unit.getUnit() == Unit.Units.TRIBE) {
            joinUnits += "JOIN " + getTable(Region.class) + " r ON g.regionId=r.id ";
        }

        String unitIdClause = "e.groupId=";
        if (unit.getUnit() == Unit.Units.REGION) {
            unitIdClause = "g.regionId=";
        } else if (unit.getUnit() == Unit.Units.TRIBE) {
            unitIdClause = "r.tribeId=";
        }
        unitIdClause += unit.getId() + " ";

        String betweenClause = "(e.happened BETWEEN "
            + getQuotedString(start.toString()) + " AND " + getQuotedString(end.toString()) + ")";

        String typesClause = "";
        if (eventTypes != null && eventTypes.length > 0) {
            List<String> types = new ArrayList<>();
            for (String type : eventTypes) {
                types.add("et.type='" + type + "'");
            }
            typesClause = format(" AND (%s)", join(types, " AND "));
        }

        String categoriesClause = "";
        if (categories != null && categories.length > 0) {
            List<String> categs = new ArrayList<>();
            for (String categ : categories) {
                categs.add("c.name" + categ);
            }
            categoriesClause = format(" AND (%s)", join(categs, " AND "));
        }

        List<String> selectFields = new ArrayList<>();
        selectFields.add("e.id");
        selectFields.add("e.typeId");
        selectFields.add("e.groupId");
        //selectFields.add("et.type as typeName");
        selectFields.add("e.happened");
        selectFields.add("e.personId");
        if (isNotEmpty(joinPersonsCategories)) {
            selectFields.add("c.name as category");
        }
        selectFields.add("e.comment");

        return
            "SELECT " + join(selectFields, ",")
                + " FROM " + eventsTbl + " e " + joinEventTypes + joinPersonsCategories + joinUnits
                + "WHERE " + unitIdClause + "AND " + betweenClause + typesClause + categoriesClause;
    }

}
