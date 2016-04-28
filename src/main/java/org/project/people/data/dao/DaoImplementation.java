package org.project.people.data.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by fertrist on 25.09.15.
 */
abstract public class DaoImplementation<T> implements GenericDao<T> {
    public static final String SELECT = "SELECT * FROM %s WHERE id=%d";
    public static final String SELECT_ALL = "SELECT * FROM %s";
    private static final String DELETE_BY_ID = "DELETE FROM %s WHERE id=%d";
    public static final String INSERT = "INSERT INTO %s (%s) values (%s)";
    public static final String UPDATE = "UPDATE %s SET %s WHERE id=?";

    protected String table = null;
    protected JdbcOperations operations;

    public DaoImplementation(DataSource dataSource, String table) {
        this.table = table;
        operations = new JdbcTemplate(dataSource);
    }

    @Override
    public int delete(int id) {
        return table == null ? 0 : operations.update(String.format(DELETE_BY_ID, table, id));
    }

}
