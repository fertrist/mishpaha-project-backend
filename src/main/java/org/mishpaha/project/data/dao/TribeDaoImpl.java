package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Tribe;

import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class TribeDaoImpl implements GenericDao<Tribe> {
    @Override
    public int saveOrUpdate(Tribe entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Tribe get(int id) {
        return null;
    }

    @Override
    public List<Tribe> list() {
        return null;
    }
}
