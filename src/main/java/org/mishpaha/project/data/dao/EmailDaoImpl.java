package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Email;
import org.springframework.dao.DataAccessException;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by fertrist on 09.10.15.
 */
public class EmailDaoImpl extends DaoImplementation<Email>{

    public EmailDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Email entity) {
        try {
            return operations.update(format(INSERT, table, "personId, email",
                format("%d, '%s'", entity.getPersonId(), entity.getEmail())));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
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
