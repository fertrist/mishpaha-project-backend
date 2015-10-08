package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Tribe;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class TribeDaoImpl extends DaoImplementation<Tribe> {

    public TribeDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Tribe entity) {
        return 0;
    }

    @Override
    public int update(Tribe entity) {
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
