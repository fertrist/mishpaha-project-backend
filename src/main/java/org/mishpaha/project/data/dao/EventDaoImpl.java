package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.EventType;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.util.DateUtil;
import org.mishpaha.project.util.ModelUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.mishpaha.project.util.ModelUtil.getTable;
import static org.mishpaha.project.util.Util.getQuotedString;

public class EventDaoImpl extends DaoImplementation<Event>{

    public EventDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
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

    private Event getEvent(ResultSet rs) throws SQLException {
        return new Event(
            rs.getInt("id"),
            rs.getInt("personId"),
            rs.getInt("groupId"),
            rs.getInt("typeId"),
            DateUtil.fromDate(rs.getDate("happened"))
        );
    }

    @Override
    public List<Event> list() { throw new UnsupportedOperationException();}

    public List<Event> list(int groupId, LocalDate start, LocalDate end) {
        String sql = format("SELECT * FROM %s WHERE groupId=%d AND happened BETWEEN %s AND %s",
            table, groupId, getQuotedString(start.toString()), getQuotedString(end.toString()));
        return operations.query(sql, (rs, numRow) -> {
            return getEvent(rs);
        });
    }

    public List<Map<String, Object>> getGroupReport(int groupId, LocalDate start, LocalDate end) {
        String persons = ModelUtil.getTable(Person.class);
        String events = table;

        //select needed subtable
        String eventsSubTable = "(SELECT e.id, p.categoryId, e.typeId, e.happened FROM "
            + events + " e JOIN " + persons + " p "
            + "ON e.personId=p.id WHERE e.groupId=" + groupId + " AND e.happened BETWEEN "
            + getQuotedString(start.toString()) + " AND " + getQuotedString(end.toString()) + ")";

        //aggregate data
        String sql = "SELECT e.typeId, e.categoryId, count(e.id) as count, e.happened FROM "
            + eventsSubTable + " e GROUP BY e.typeId, e.categoryId, e.happened";
        return operations.queryForList(sql);
    }
}
