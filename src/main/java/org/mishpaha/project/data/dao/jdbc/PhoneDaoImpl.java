package org.mishpaha.project.data.dao.jdbc;

import org.mishpaha.project.data.model.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

/**
 * Model class which represents phone No.
 */
public class PhoneDaoImpl extends DaoImplementation<Phone>{

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneDaoImpl.class);

    public PhoneDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Phone entity) {
        try {
            String sql = format(INSERT, table, "personId, phone",
                format("%d, '%s'", entity.getPersonId(), entity.getPhone()));
            LOGGER.info(sql);
            return operations.update(sql);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int batchSave(int id, List<String> phones) {
        String sql = "INSERT INTO phones(personId, phone) VALUES(?, ?)";
        return operations.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, phones.get(i));
            }

            @Override
            public int getBatchSize() {
                return phones.size();
            }
        }).length;
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
