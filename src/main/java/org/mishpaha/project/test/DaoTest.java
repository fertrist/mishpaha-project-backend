package org.mishpaha.project.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.Application;
import org.mishpaha.project.data.dao.EmailDaoImpl;
import org.mishpaha.project.data.dao.EventDaoImpl;
import org.mishpaha.project.data.dao.PhoneDaoImpl;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mishpaha.project.data.dao.EventDaoImpl.ReportFields.count;
import static org.mishpaha.project.data.dao.EventDaoImpl.ReportFields.happened;
import static org.mishpaha.project.data.dao.EventDaoImpl.ReportFields.listed;
import static org.mishpaha.project.data.dao.EventDaoImpl.ReportFields.type;
import static org.mishpaha.project.data.dao.EventDaoImpl.ReportFields.week;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class DaoTest extends BaseTestClass {

    private final String shouldSucceedMessage = "Request should succeed.";
    private final String shouldFailMessage = "Request should fail.";

    @Test
    public void testListInsertDeletePerson() {
        List<Person> initialList = personDao.list();
        initialList.forEach(System.out::println);
        int id = 100;
        Person newPerson = new Person(id, "Иван", "Иванов", "Иванович", true, LocalDate.of(1987, 9, 3),
            true, false, 1, null, null);
        assertEquals(shouldSucceedMessage, personDao.save(newPerson), 1);
        List<Person> resultList = personDao.list();
        resultList.forEach(System.out::println);
        assertEquals("New person should be added.", 1, resultList.size() - initialList.size());
        Person person = personDao.get(id);
        assertEquals("Objects should be equal!", person, newPerson);

        //delete person
        assertEquals(shouldSucceedMessage, 1, personDao.delete(id));
        List<Person> finalList = personDao.list();
        finalList.forEach(System.out::println);

        //person is absent
        assertEquals("Person should be removed.", personDao.get(id), null);
        assertEquals("Person should be removed.", resultList.size() - finalList.size(), 1);
    }

    @Test
    public void testUpdatePerson() {
        int id = 5;
        //check that person is present
        Person person = personDao.get(id);
        System.out.println(String.format("Got a person by id=%d: %s", id, person.toString()));
        assertEquals("Person name must match.", getPersons().get(id-1).getFirstName(), person.getFirstName());

        //test update
        Person updatedPerson = new Person();
        updatedPerson.setId(id);
        updatedPerson.setFirstName("Имя-Имя-1");
        assertEquals(shouldSucceedMessage, personDao.update(updatedPerson), 1);
        person = personDao.get(id);
        assertEquals("Person name must match.", "Имя-Имя-1", person.getFirstName());
    }

    /**
     * Test part of report - count of calls, visits, meetings by categories.
     */
    @Test
    public void testCountCallsMeetingsVisits() {
        LocalDate now = LocalDate.now();
        List<Map<String, Object>> list = ((EventDaoImpl) eventDao).getCallsMeetingsVisits(1, now.minusMonths(3), now);
        for (Map<String, Object> rowMap : list) {
            Set<String> keySet = rowMap.keySet();
            for (String key : keySet) {
                key = key.toLowerCase();
                assertTrue(key.equalsIgnoreCase(count.name()) || key.equalsIgnoreCase(type.name()) || key
                    .equalsIgnoreCase(week.name()) || key.equalsIgnoreCase(listed.name())
                    || key.equalsIgnoreCase(happened.name()));
                switch (key) {
                    case "count" :
                        assertTrue(rowMap.get(key) instanceof Number);
                        break;
                    case "listed" :
                        assertTrue(((String) rowMap.get(key)).equalsIgnoreCase("yes")
                            || ((String) rowMap.get(key)).equalsIgnoreCase("no"));
                        break;
                    case "type" :
                        assertTrue(((String) rowMap.get(key)).equalsIgnoreCase("visit")
                            || ((String) rowMap.get(key)).equalsIgnoreCase("call")
                            || ((String) rowMap.get(key)).equalsIgnoreCase("meeting"));
                        break;
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        list.forEach(builder::append);
        System.out.println(builder.toString());
    }

    /**
     * Test selection of group events.
     */
    @Test
    public void testSelectGroupMeetings() {
        LocalDate now = LocalDate.now();
        LocalDate past = now.minusMonths(3);
        List<Event> events = ((EventDaoImpl) eventDao).getGroupMeetings(1, past, now);
        for (Event event : events) {
            assertEquals(1, event.getGroupId());
            assertNotEquals(0, event.getPersonId());
            assertEquals(EventDaoImpl.EventTypes.valueOf("group").ordinal() + 1, event.getTypeId());
            assertTrue(event.getHappened().equals(past) || event.getHappened().equals(now) ||
                (event.getHappened().isAfter(past) && event.getHappened().isBefore(now)));
        }
        System.out.println(Arrays.toString(events.toArray()));
    }

    @Test
    public void testListInsertDeletePhone() {
        List<Phone> initialList = phoneDao.list();
        initialList.forEach(System.out::println);
        Assert.assertEquals("Initial count doesn't not match.", getPersons().size()*2, initialList.size());

        //add new phone record
        int personId = 1;
        Phone newPhone = new Phone(personId, "0631234565");
        Assert.assertEquals(shouldSucceedMessage, 1, phoneDao.save(newPhone));
        List<String> personsPhones = ((PhoneDaoImpl) phoneDao).list(personId);
        personsPhones.forEach(System.out::println);
        Assert.assertEquals("Phone count doesn't not match.", getPhones(personId).size() + 1, personsPhones.size());
        //add two more phones
        newPhone.setPhone("0977894563");
        Assert.assertEquals(shouldSucceedMessage, 1, phoneDao.save(newPhone));
        newPhone.setPhone("0980004562");
        Assert.assertEquals(shouldSucceedMessage, 1, phoneDao.save(newPhone));
        personsPhones = ((PhoneDaoImpl) phoneDao).list(personId);
        personsPhones.forEach(System.out::println);
        Assert.assertEquals("Phone count doesn't not match.", getPhones(personId).size() + 3, personsPhones.size());

        //delete phone
        Assert.assertEquals(shouldSucceedMessage, 1, ((PhoneDaoImpl) phoneDao).delete(newPhone));
        personsPhones = ((PhoneDaoImpl) phoneDao).list(personId);
        personsPhones.forEach(System.out::println);
        Assert.assertEquals("Phone count doesn't not match.", getPhones(personId).size() + 2, personsPhones.size());
        //delete others by one request
        Assert.assertEquals(shouldSucceedMessage, 4, phoneDao.delete(personId));
        personsPhones = ((PhoneDaoImpl) phoneDao).list(personId);
        Assert.assertEquals("List should be empty.", getPhones(personId).size()-2, personsPhones.size());

        //phone is absent
        initialList = phoneDao.list();
        Assert.assertEquals("Phone count doesn't not match.", getPersons().size()*2-2, initialList.size());
    }

    /**
     * For phone update is delete + save. TODO : encapsulate it in transactional business logic method.
     */
    @Test
    public void testUpdatePhone() {
        //add new phone record
        phoneDao.list().forEach(System.out::println);
        int personId = 3;
        String oldPhone = ((PhoneDaoImpl) phoneDao).list(personId).get(1);
        Assert.assertEquals(shouldSucceedMessage, 1, ((PhoneDaoImpl) phoneDao).delete(new Phone(personId, oldPhone)));
        phoneDao.list().forEach(System.out::println);

        //"update" phone
        String newPhone = "0993652356";
        Assert.assertEquals(shouldSucceedMessage, 1, phoneDao.save(new Phone(personId, newPhone)));
        phoneDao.list().forEach(System.out::println);
        //try to save the same phone
        Assert.assertEquals(shouldSucceedMessage, 0, phoneDao.save(new Phone(personId, newPhone)));
    }

    @Test
    public void testListInsertDeleteEmail() {
        List<Email> initialList = emailDao.list();
        initialList.forEach(System.out::println);
        Assert.assertEquals("Initial count doesn't not match.", getPersons().size()*2, initialList.size());

        //add new email record
        int personId = 3;
        Email newEmail = new Email(personId, "fedorov.fedor@i.ua");
        Assert.assertEquals(shouldSucceedMessage, 1, emailDao.save(newEmail));
        List<String> personsEmails = ((EmailDaoImpl) emailDao).list(personId);
        personsEmails.forEach(System.out::println);
        Assert.assertEquals("Email count doesn't not match.", 3, personsEmails.size());
        //add two more emails
        newEmail.setEmail("fedorov.fedor@ukr.net");
        Assert.assertEquals(shouldSucceedMessage, 1, emailDao.save(newEmail));
        newEmail.setEmail("fedorov.fedor@yandex.ru");
        Assert.assertEquals(shouldSucceedMessage, 1, emailDao.save(newEmail));
        personsEmails = ((EmailDaoImpl) emailDao).list(personId);
        personsEmails.forEach(System.out::println);
        Assert.assertEquals("Email count doesn't not match.", 5, personsEmails.size());

        //delete email
        Assert.assertEquals(shouldSucceedMessage, 1, ((EmailDaoImpl) emailDao).delete(newEmail));
        personsEmails = ((EmailDaoImpl) emailDao).list(personId);
        personsEmails.forEach(System.out::println);
        Assert.assertEquals("Email count doesn't not match.", 4, personsEmails.size());
        //delete other by one request
        Assert.assertEquals(shouldSucceedMessage, 4, emailDao.delete(personId));
        personsEmails = ((EmailDaoImpl) emailDao).list(personId);
        Assert.assertEquals("List should be empty.", 0, personsEmails.size());

        //email is absent
        initialList = emailDao.list();
        Assert.assertEquals("Email count doesn't not match.", getPersons().size()*2-2, initialList.size());
    }
}
