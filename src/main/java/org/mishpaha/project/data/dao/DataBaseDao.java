package org.mishpaha.project.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

/**
 * Created by fertrist on 09.10.15.
 */
public class DataBaseDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DataBaseDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int cleanTables(String... tables) {
        return JdbcTestUtils.deleteFromTables(jdbcTemplate, tables);
    }

    public int createTables() {

    }

    public int dropTables() {

    }
}
