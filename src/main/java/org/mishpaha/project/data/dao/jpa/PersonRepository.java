package org.mishpaha.project.data.dao.jpa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mishpaha.project.data.model.Person;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class PersonRepository implements GenericDao<Person> {

    private static String table = Person.class.getSimpleName();

    private SessionFactory sessionFactory;

    public PersonRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Person save(Person entity) {
        Session session = sessionFactory.openSession();
        Serializable id = session.save(entity);
        Person person = entity.clone();
        person.setId((Integer) id);
        return person;
    }

    @Override
    public Person update(Person entity) {
        return null;
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
    public List list() {
        Session session = sessionFactory.openSession();
        List persons = session.createQuery("from " + table).list();
        session.close();
        return persons;
    }
}
