package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Person;

import javax.sql.DataSource;
import java.util.List;


/**
 * Person dao implementation.
 */
public class PersonDaoImpl extends DaoImplementation<Person> {

    public PersonDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Person entity) {
        return 0;
    }

    @Override
    public int update(Person entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Person get(int id) {
        return null;
    }

    @Override
    public List<Person> list() {
        return null;
    }
}
