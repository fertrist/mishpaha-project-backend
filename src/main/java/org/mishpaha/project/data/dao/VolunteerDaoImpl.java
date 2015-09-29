package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Volunteer;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class VolunteerDaoImpl extends DaoImplementation<Volunteer> {

    public VolunteerDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Volunteer entity) {
        return 0;
    }

    @Override
    public int update(Volunteer entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Volunteer get(int id) {
        return null;
    }

    @Override
    public List<Volunteer> list() {
        return null;
    }
}
