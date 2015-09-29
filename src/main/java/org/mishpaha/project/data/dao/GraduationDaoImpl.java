package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Graduation;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class GraduationDaoImpl extends DaoImplementation<Graduation>{

    public GraduationDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Graduation entity) {
        return 0;
    }

    @Override
    public int update(Graduation entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Graduation get(int id) {
        return null;
    }

    @Override
    public List<Graduation> list() {
        return null;
    }
}
