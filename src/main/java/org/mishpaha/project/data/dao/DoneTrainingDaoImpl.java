package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.DoneTraining;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class DoneTrainingDaoImpl implements GenericDao<DoneTraining> {

    private String table = "categories";
    private JdbcOperations jdbcOperations;

    public DoneTrainingDaoImpl(DataSource dataSource) {
        jdbcOperations = new JdbcTemplate(dataSource);
    }

    @Override
    public int saveOrUpdate(DoneTraining entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public DoneTraining get(int id) {
        return null;
    }

    @Override
    public List<DoneTraining> list() {
        return null;
    }
}
