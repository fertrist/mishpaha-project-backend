package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.DoneTraining;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class DoneTrainingDaoImpl extends DaoImplementation<DoneTraining> {

    public DoneTrainingDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(DoneTraining entity) {
        return 0;
    }

    @Override
    public int update(DoneTraining entity) {
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
