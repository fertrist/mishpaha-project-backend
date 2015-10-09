package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Phone;
import org.springframework.dao.DataAccessException;

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
        try {
            return jdbcOperations.update(String.format(INSERT, table, "personId, phone",
                String.format("%d, '%s'", entity.getPersonId(), entity.getPhone())));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Lists all phone numbers.
     * @return list of Phones
     */
    @Override
    public List<Phone> list() {
        return jdbcOperations.query(String.format(SELECT_ALL, table), (rs, numRow) -> {
            return new Phone(rs.getInt("personId"), rs.getString("phone"));
        });
    }

    /**
     * Lists all phone numbers for given user.
     * @param personId user id.
     * @return list of Phones
     */
    public List<String> list(int personId) {
        return jdbcOperations.query(String.format("SELECT * FROM %s WHERE personId=%d", table, personId),
            (rs, numRow) -> {
                return rs.getString("phone");
            });
    }

    /**
     * Remove given phone number.
     */
    public int delete(Phone phone) {
        return jdbcOperations.update(String
            .format("DELETE FROM %s WHERE personId=%d AND phone='%s'", table, phone.getPersonId(), phone.getPhone()));
    }

    /**
     * Update will be service transaction of delete+save.
     */
    @Override
    public int update(Phone entity) {
        throw new UnsupportedOperationException("Calling unsupported action.");
    }

    /**
     * Doesn't support single phone get.
     */
    @Override
    public Phone get(int personId) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Calling unsupported action.");
    }

    /**
     * Doesn't support removal of all person's emails.
     */
    @Override
    public int delete(int personId) {
        throw new UnsupportedOperationException("Calling unsupported action.");
    }

}