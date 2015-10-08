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
        return jdbcOperations.update(String.format(INSERT, table, "personId, phone",
            String.format("%d, '%s'", entity.getPersonId(), entity.getPhone())));
    }

    /**
     * Implement update as delete+save.
     */
    @Override
    public int update(Phone entity) {
        throw new UnsupportedOperationException("Calling unsupported action.");
    }

    @Override
    public Phone get(int personId) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Calling unsupported action.");
    }

    /**
     * To list all phones.
     * @return list of Phones
     */
    @Override
    public List<Phone> list() {
        return jdbcOperations.query(String.format(SELECT_ALL, table), (rs, numRow) -> {
            return new Phone(rs.getInt("personId"), rs.getString("phone"));
        });
    }

    /**
     * To list all phones which belong to user.
     * @param personId user id.
     * @return list of Phones
     */
    public List<String> list(int personId) {
        return jdbcOperations.query(String.format("SELECT * FROM %s WHERE personId=%d", table, personId),
            (rs, numRow) -> {
                return rs.getString("phone");
            });
    }

    @Override
    public int delete(int personId) {
        return jdbcOperations.update(String.format("DELETE FROM %s WHERE personId=%d", table, personId));
    }

    public int delete(Phone phone) {
        return jdbcOperations.update(String
            .format("DELETE FROM %s WHERE personId=%d AND phone=%s", table, phone.getPersonId(), phone.getPhone()));
    }
}
