package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Phone;

import java.util.List;

/**
 * Model class which represents phone No.
 */
public class PhoneDaoImpl implements GenericDao<Phone>{

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
