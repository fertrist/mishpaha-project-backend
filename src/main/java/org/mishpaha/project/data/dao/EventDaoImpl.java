package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.EventType;
import org.mishpaha.project.util.TestUtil;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static org.mishpaha.project.util.ModelUtil.getTable;
import static org.mishpaha.project.util.TestUtil.getDateAsQuotedString;
import static org.mishpaha.project.util.TestUtil.getQuotedString;

public class EventDaoImpl extends DaoImplementation<Event>{

    public EventDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Event entity) {
        String sql = format(INSERT, table, "personId, groupId, typeId, happened",
            format("%d,%d,%d,%s", entity.getPersonId(), entity.getGroupId(), entity.getTypeId(),
                getDateAsQuotedString(entity.getHappened())));
        return operations.update(sql);
    }

    public int saveEventType(String eventType) {
        return operations.update(format(INSERT, getTable(EventType.class), "type",
            getQuotedString(eventType)));
    }

    public int deleteEventType(String eventType) {
        return operations.update(format("DELETE FROM %s WHERE type=", getTable(EventType.class),
            getQuotedString(eventType)));
    }

    @Override
    public int update(Event entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Event get(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Event> list() { throw new UnsupportedOperationException();}

    public List<Event> list(int groupId, Date start, Date end) {
        String sql = format("SELECT * FROM %s WHERE groupId=%d AND happened BETWEEN %s AND %s",
            table, groupId, getDateAsQuotedString(start), getDateAsQuotedString(end));
        return operations.query(sql, (rs, numRow) -> {
            return new Event(
                rs.getInt("personId"),
                rs.getInt("groupId"),
                rs.getInt("typeId"),
                TestUtil.getDate(rs.getDate("happened").toString())
            );
        });
    }
}
