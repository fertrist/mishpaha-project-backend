package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Ministry;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class MinistryDaoImpl extends DaoImplementation<Ministry>{

    public MinistryDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Ministry entity) {
        return 0;
    }

    @Override
    public int update(Ministry entity) {
        return 0;
    }

    @Override
    public Ministry get(int id) {
        return null;
    }

    @Override
    public List<Ministry> list() {
        return null;
    }
}
