package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Training;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class TrainingDaoImpl extends DaoImplementation<Training>{

    public TrainingDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Training entity) {
        return 0;
    }

    @Override
    public int update(Training entity) {
        return 0;
    }

    @Override
    public Training get(int id) {
        return null;
    }

    @Override
    public List<Training> list() {
        return null;
    }
}
