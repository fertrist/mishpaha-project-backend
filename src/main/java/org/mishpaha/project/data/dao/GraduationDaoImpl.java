package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Graduation;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class GraduationDaoImpl implements GenericDao<Graduation>{

    private String table = "categories";
    private JdbcOperations jdbcOperations;

    public GraduationDaoImpl(DataSource dataSource) {
        jdbcOperations = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(Graduation entity) {
        return 0;
    }

    @Override
    public int update(Graduation entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Graduation get(int id) {
        return null;
    }

    @Override
    public List<Graduation> list() {
        return null;
    }
}
