package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Phone;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.String.format;

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
            return operations.update(format(INSERT, table, "personId, phone",
                format("%d, '%s'", entity.getPersonId(), entity.getPhone())));
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
        return operations.query(format(SELECT_ALL, table), (rs, numRow) -> {
            return new Phone(rs.getInt("personId"), rs.getString("phone"));
        });
    }

    /**
     * Lists all phone numbers for given user.
     * @param personId user id.
     * @return list of Phones
     */
    public List<String> list(int personId) {
        return operations.query(format("SELECT * FROM %s WHERE personId=%d", table, personId),
            (rs, numRow) -> {
                return rs.getString("phone");
            });
    }

    /**
     * Remove given phone number.
     */
    public int delete(Phone phone) {
        return operations.update(format("DELETE FROM %s WHERE personId=%d AND phone='%s'",
            table, phone.getPersonId(), phone.getPhone()));
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
     * Removal of all person's phones.
     */
    @Override
    public int delete(int personId) {
        return operations.update(format("DELETE FROM %s WHERE personId=%d", table, personId));
    }

}
