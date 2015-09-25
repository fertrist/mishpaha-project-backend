package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.GroupChange;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class GroupChangeDaoImpl implements GenericDao<GroupChange> {

    private String table = "categories";
    private JdbcOperations jdbcOperations;

    public GroupChangeDaoImpl(DataSource dataSource) {
        jdbcOperations = new JdbcTemplate(dataSource);
    }

    @Override
    public int saveOrUpdate(GroupChange entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public GroupChange get(int id) {
        return null;
    }

    @Override
    public List<GroupChange> list() {
        return null;
    }
}
