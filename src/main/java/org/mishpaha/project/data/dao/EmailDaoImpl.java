package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Email;
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
 * Created by fertrist on 09.10.15.
 */
public class EmailDaoImpl extends DaoImplementation<Email>{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailDaoImpl.class);

    public EmailDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Email entity) {
        try {
            String sql = format(INSERT, table, "personId, email",
                format("%d, '%s'", entity.getPersonId(), entity.getEmail()));
            LOGGER.info(sql);
            return operations.update(sql);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int batchSave(int id, List<String> emails) {
        String sql = "INSERT INTO emails(personId, email) VALUES(?, ?)";
        return operations.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, emails.get(i));
            }

            @Override
            public int getBatchSize() {
                return emails.size();
            }
        }).length;
    }

    /**
     * Lists all emails.
     */
    @Override
    public List<Email> list() {
        return operations.query(format(SELECT_ALL, table), (rs, numRow) -> {
            return new Email(rs.getInt("personId"), rs.getString("email"));
        });
    }

    /**
     * Lists all emails fot given person.
     */
    public List<String> list(int personId) {
        return operations.query(format("SELECT * FROM %s WHERE personId=%d", table, personId),
            (rs, numRow) -> {
                return rs.getString("email");
            });
    }

    /**
     * Removes specific email of given person.
     */
    public int delete(Email email) {
        return operations.update(
            format("DELETE FROM %s WHERE personId=%d AND email='%s'", table, email.getPersonId(), email.getEmail()));
    }

    /**
     * Update will be service transaction of delete+save.
     */
    @Override
    public int update(Email entity) {
        throw new UnsupportedOperationException("Calling unsupported action.");
    }

    @Override
    public Email get(int personId) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Calling unsupported action.");
    }

    /**
     * Doesn't support removal of all person's emails.
     */
    @Override
    public int delete(int personId) {
        return operations.update(format("DELETE FROM %s WHERE personId=%d", table, personId));
    }

}
