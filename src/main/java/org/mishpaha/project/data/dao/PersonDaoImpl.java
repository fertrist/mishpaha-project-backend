package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;


/**
 * Person dao implementation.
 */
public class PersonDaoImpl implements GenericDao<Person> {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public int saveOrUpdate(Person entity) {
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
