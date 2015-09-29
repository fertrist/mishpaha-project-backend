package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Training;

import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class TrainingDaoImpl implements GenericDao<Training>{
    @Override
    public int save(Training entity) {
        return 0;
    }

    @Override
    public int update(Training entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
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
