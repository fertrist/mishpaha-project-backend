package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Phone;

import javax.sql.DataSource;
import java.util.List;

/**
 * Model class which represents phone No.
 */
public class PhoneDaoImpl extends DaoImplementation<Phone>{

    public PhoneDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Phone entity) {
        return 0;
    }

    @Override
    public int update(Phone entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Phone get(int id) {
        return null;
    }

    @Override
    public List<Phone> list() {
        return null;
    }
}
