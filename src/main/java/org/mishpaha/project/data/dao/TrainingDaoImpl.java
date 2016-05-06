package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Training;
import org.mishpaha.project.util.DateUtil;

import javax.sql.DataSource;
import java.util.List;

public class TrainingDaoImpl extends DaoImplementation<Training>{

    public TrainingDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Training entity) {
        return operations.update(String.format("INSERT INTO %s (name, tookPlace) values ('%s', '%s')",
            table, entity.getName(), entity.getTookPlace()));
    }

    @Override
    public int update(Training entity) {
        return operations.update(String.format("UPDATE %s SET name='%s',tookPlace='%s' WHERE id=%d",
            table, entity.getName(), entity.getTookPlace(), entity.getId()));
    }

    @Override
    public Training get(int id) {
        return operations.query(String.format(SELECT, table, id), rs -> {
            if (rs.next()) {
                return new Training(rs.getInt("id"), rs.getString("name"), DateUtil.fromDate(rs.getDate("tookPlace")));
            }
            return null;
        });
    }

    @Override
    public List<Training> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, numRow) -> {
            return new Training(rs.getInt("id"), rs.getString("name"), DateUtil.fromDate(rs.getDate("tookPlace")));
        });
    }
}
