package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Person;

import java.util.List;

public interface PersonDao {

    List<Person> list();

    Person getPerson(int id);

    int saveOrUpdate(Person person);

    int delete(int id);
}
