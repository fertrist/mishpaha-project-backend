package org.mishpaha.project.data.dao.jdbc;

import org.mishpaha.project.data.model.Ministry;
import org.mishpaha.project.util.Util;

import javax.sql.DataSource;
import java.util.List;

public class MinistryDaoImpl extends DaoImplementation<Ministry>{

    public MinistryDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Ministry entity) {
        return operations.update(String.format(INSERT, table, "name", Util.getQuotedString(entity.getName())));
    }

    @Override
    public int update(Ministry entity) {
        return operations.update(String.format("UPDATE %s SET name='%s WHERE id=%d",
            table, entity.getName(), entity.getId()));
    }

    @Override
    public Ministry get(int id) {
        return operations.query(String.format(SELECT, table, id), rs -> {
            if (rs.next()) {
                return new Ministry(rs.getInt("id"), rs.getString("name"));
            }
            return null;
        });
    }

    @Override
    public List<Ministry> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, numRow) -> {
            return new Ministry(rs.getInt("id"), rs.getString("name"));
        });
    }
}
