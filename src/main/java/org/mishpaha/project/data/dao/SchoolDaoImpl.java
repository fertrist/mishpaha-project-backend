package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.School;

import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class SchoolDaoImpl implements GenericDao<School> {
    @Override
    public int saveOrUpdate(School entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public School get(int id) {
        return null;
    }

    @Override
    public List<School> list() {
        return null;
    }
}
