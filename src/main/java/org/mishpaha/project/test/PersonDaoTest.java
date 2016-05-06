package org.mishpaha.project.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfiguration.class)
@WebAppConfiguration
public class PersonDaoTest extends BaseTestClass {

    @Autowired
    private GenericDao<Person> personDao;
    private final String shouldSucceedMessage = "Request should succeed.";
    private final String shouldFailMessage = "Request should fail.";

    @Test
    public void testListInsertDeletePerson() {
        List<Person> initialList = personDao.list();
        initialList.forEach(System.out::println);
        int id = 100;
        Person newPerson = new Person(id, "Иван", "Иванов", "Иванович", true, LocalDate.of(1987, 9, 3),
            true, false, 1, null, null);
        Assert.assertEquals(shouldSucceedMessage, personDao.save(newPerson), 1);
        List<Person> resultList = personDao.list();
        resultList.forEach(System.out::println);
        Assert.assertEquals("New person should be added.", 1, resultList.size() - initialList.size());
        Person person = personDao.get(id);
        Assert.assertEquals("Objects should be equal!", person, newPerson);

        //delete person
        Assert.assertEquals(shouldSucceedMessage, 1, personDao.delete(id));
        List<Person> finalList = personDao.list();
        finalList.forEach(System.out::println);

        //person is absent
        Assert.assertEquals("Person should be removed.", personDao.get(id), null);
        Assert.assertEquals("Person should be removed.", resultList.size() - finalList.size(), 1);
    }

    @Test
    public void testUpdatePerson() {
        int id = 5;
        //check that person is present
        Person person = personDao.get(id);
        System.out.println(String.format("Got a person by id=%d: %s", id, person.toString()));
        Assert.assertEquals("Person name must match.", getPersons().get(id-1).getFirstName(), person.getFirstName());

        //test update
        Person updatedPerson = new Person();
        updatedPerson.setId(id);
        updatedPerson.setFirstName("Имя-Имя-1");
        Assert.assertEquals(shouldSucceedMessage, personDao.update(updatedPerson), 1);
        person = personDao.get(id);
        Assert.assertEquals("Person name must match.", "Имя-Имя-1", person.getFirstName());
    }
}
