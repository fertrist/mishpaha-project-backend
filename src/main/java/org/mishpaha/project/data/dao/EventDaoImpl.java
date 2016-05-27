package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.EventType;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.util.DateUtil;

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

    @Override
    public int save(Event entity) {
        String sql = format(INSERT, table, "personId, groupId, typeId, happened",
            format("%d,%d,%d,%s", entity.getPersonId(), entity.getGroupId(), entity.getTypeId(),
                getQuotedString(entity.getHappened().toString())));
        return operations.update(sql);
    }

    public int saveEventType(String eventType) {
        return operations.update(format(INSERT, getTable(EventType.class), "type",
            getQuotedString(eventType)));
    }

    public int deleteEventType(String eventType) {
        return operations.update(format("DELETE FROM %s WHERE type=%s", getTable(EventType.class),
            getQuotedString(eventType)));
    }

    @Override
    public int update(Event entity) {
        throw new UnsupportedOperationException();
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

    public List<Event> list(int groupId, LocalDate start, LocalDate end) {
        return list(new Group(groupId), start, end, null, null);
    }

    /**
     * List event based on filter params.
     */
    public List<Event> list(Unit unit, LocalDate start, LocalDate end,
                                          String[] eventTypes, String[] categories) {
        String sql = getFilterRequest(unit, start, end, eventTypes, categories);
        return operations.query(sql, (rs, rowNum) -> {
            return getEvent(rs);
        });
    }

    private Event getEvent(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setTypeId(rs.getInt("typeId"));
        event.setPersonId(rs.getInt("personId"));
        event.setGroupId(rs.getInt("groupId"));
        event.setHappened(DateUtil.fromDate(rs.getDate("happened")));
        return event;
    }

    private String getFilterRequest(Unit unit, LocalDate start, LocalDate end, String[] eventTypes,
                                    String[] categories) {
        String eventsTbl = getTable(Event.class);

        String joinEventTypes = "JOIN " + getTable(EventType.class) + " et ON e.typeId=et.id ";

        String joinPersons = "JOIN " + getTable(Person.class) + " p ON e.personId=p.id ";

        String joinPersonsCategories = categories != null && categories.length > 0 ?
            joinPersons + "JOIN " + getTable(Category.class) + " c ON p.categoryId=c.id " : "";

        String joinGroups = unit.getUnit() == Unit.Units.REGION ?
            "JOIN " + getTable(Group.class) + " g ON e" + ".groupId=g.id " : "";

        String unitIdClause = (unit.getUnit() == Unit.Units.REGION ? "g.regionId=" : "e.groupId=") + unit.getId() + " ";

        String betweenClause = "(e.happened BETWEEN "
            + getQuotedString(start.toString()) + " AND " + getQuotedString(end.toString()) + ")";

        String typesClause = "";
        if (eventTypes != null && eventTypes.length > 0) {
            List<String> types = new ArrayList<>();
            for (String type : eventTypes) {
                types.add("et.type=" + type);
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
        selectFields.add("et.type as typeName");
        selectFields.add("e.happened");
        selectFields.add("e.personId");
        if (isNotEmpty(joinPersonsCategories)) {
            selectFields.add("c.name as category");
        }

        return
            "SELECT " + join(selectFields, ",")
                + "FROM " + eventsTbl + " e " + joinEventTypes + joinPersonsCategories + joinGroups
                + "WHERE " + unitIdClause + "AND " + betweenClause + typesClause + categoriesClause;
    }

    /**
     * TODO do single call to count things:
     * visits : blue, brown, green categories
     * visits : white category
     * meetings : blue, brown, green categories
     * meetings : white category
     * calls : blue, brown, green categories
     * calls : white category
     *
     * TODO use group by clause
     */
    public List<Map<String, Object>> getGroupCallsVisitsMeetings(int groupId, LocalDate start, LocalDate end) {
        String persons = getTable(Person.class);
        String events = table;
        String categories = getTable(Category.class);
        //select needed subtable
        String eventsSubTable = "(SELECT e.id, c.name as category, e.typeId as type, e.happened FROM "
            + events + " e JOIN " + persons + " p ON e.personId=p.id JOIN " + categories + " c ON p.categoryId=c.id "
            + "WHERE e.groupId=" + groupId + " AND (e.happened BETWEEN "
            + getQuotedString(start.toString()) + " AND " + getQuotedString(end.toString()) + ") "
            + "AND (c.name='green' OR c.name='blue' OR c.name='brown')"
            + ")";

        //aggregate data
        String sql = "SELECT e.type, e.category, count(e.id) as count, e.happened FROM "
            + eventsSubTable + " e GROUP BY e.type, e.category, e.happened";
        return operations.queryForList(sql);
    }
}
