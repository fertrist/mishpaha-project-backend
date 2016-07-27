package org.mishpaha.project.data.dao.jdbc;

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
        return operations.update(String.format(INSERT, table, "personId, trainingId",
                new StringBuilder("").append(entity.getPersonId()).append(",").append(entity.getTrainingId())));
    }

    @Override
    public int update(DoneTraining entity) {
        return 0;
    }

    @Override
    public DoneTraining get(int id) {
        return null;
    }

    @Override
    public List<DoneTraining> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, numRow) -> {
            return new DoneTraining(rs.getInt("personId"), rs.getInt("trainingId"));
        });
    }

    public int delete(DoneTraining training) {
        return operations.update(String.format("DELETE from %s WHERE personId=%d AND trainingId=%d",
            table, training.getPersonId(), training.getTrainingId()));
    }
}
