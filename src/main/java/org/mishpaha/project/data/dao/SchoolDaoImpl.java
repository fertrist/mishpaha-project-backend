package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.School;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class SchoolDaoImpl extends DaoImplementation<School> {

    public SchoolDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(School entity) {
        return 0;
    }

    @Override
    public int update(School entity) {
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
