package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Region;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class RegionDaoImpl extends DaoImplementation<Region>{
    public RegionDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Region entity) {
        return 0;
    }

    @Override
    public int update(Region entity) {
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
