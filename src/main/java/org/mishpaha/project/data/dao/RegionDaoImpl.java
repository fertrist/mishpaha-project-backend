package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Region;

import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class RegionDaoImpl implements GenericDao<Region>{
    @Override
    public int saveOrUpdate(Region entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Region get(int id) {
        return null;
    }

    @Override
    public List<Region> list() {
        return null;
    }
}
