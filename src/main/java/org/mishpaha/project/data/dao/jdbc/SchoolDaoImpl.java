package org.mishpaha.project.data.dao.jdbc;

import org.mishpaha.project.data.model.School;
import org.mishpaha.project.util.DateUtil;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.String.format;
import static org.mishpaha.project.util.DateUtil.getDateAsQuotedString;
import static org.mishpaha.project.util.Util.getQuotedString;

public class SchoolDaoImpl extends DaoImplementation<School> {

    public SchoolDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(School entity) {
        return operations.update(format(
            "INSERT INTO %s (schoolLevel, start, graduation, teacher) values(%s, %s, %s, %s)",
            table, getQuotedString(entity.getSchoolLevel().toString()), getDateAsQuotedString(entity.getStart()),
            getDateAsQuotedString(entity.getGraduation()), getQuotedString(entity.getTeacher())));
    }

    @Override
    public int update(School entity) {
        return operations.update(format("UPDATE %s SET schoolLevel='%s',start='%s',graduation='%s',teacher=%s",
            table, entity.getSchoolLevel().toString(), entity.getStart(), entity.getGraduation(), entity.getTeacher()));
    }

    @Override
    public School get(int id) {
        return operations.query(format(SELECT, table, id), rs -> {
            if (rs.next()) {
                return new School(rs.getInt("id"), rs.getString("schoolLevel"), DateUtil.fromDate(rs.getDate("start")),
                    DateUtil.fromDate(rs.getDate("graduation")), rs.getString("teacher"));
            }
            return null;
        });
    }

    @Override
    public List<School> list() {
        return operations.query(format(SELECT_ALL, table), (rs, rowNum) -> {
            return new School(rs.getInt("id"), rs.getString("schoolLevel"), DateUtil.fromDate(rs.getDate("start")),
                DateUtil.fromDate(rs.getDate("graduation")), rs.getString("teacher"));
        });
    }
}
