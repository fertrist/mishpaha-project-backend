package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Training;

import javax.sql.DataSource;
import java.util.List;

import static org.mishpaha.project.util.TestUtil.getDateAsString;

/**
 * Created by fertrist on 24.09.15.
 */
public class TrainingDaoImpl extends DaoImplementation<Training>{

    public TrainingDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Training entity) {
        return operations.update(String.format("INSERT INTO %s (name, tookPlace) values ('%s', '%s')",
            table, entity.getName(), getDateAsString(entity.getTookPlace())));
    }

    @Override
    public int update(Training entity) {
        return operations.update(String.format("UPDATE %s SET name='%s',tookPlace='%s' WHERE id=%d",
            table, entity.getName(), getDateAsString(entity.getTookPlace()), entity.getId()));
    }

    @Override
    public Training get(int id) {
        return operations.query(String.format(SELECT, table, id), rs -> {
            if (rs.next()) {
                return new Training(rs.getInt("id"), rs.getString("name"), rs.getDate("tookPlace"));
            }
            return null;
        });
    }

    @Override
    public List<Training> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, numRow) -> {
            return new Training(rs.getInt("id"), rs.getString("name"), rs.getDate("tookPlace"));
        });
    }
}
