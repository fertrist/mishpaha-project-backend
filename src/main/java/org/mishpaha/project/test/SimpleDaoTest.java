package org.mishpaha.project.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.data.dao.EmailDaoImpl;
import org.mishpaha.project.data.dao.PhoneDaoImpl;
import org.mishpaha.project.data.model.District;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Phone;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Tests for basic jdbc operations.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfiguration.class)
public class SimpleDaoTest extends BaseDaoTestClass{

    @Test
    public void testListInsertDeleteDistrict() {
        List<District> initialList = districtDao.list();
        initialList.forEach(System.out::println);
        int id = 100;
        District newDistrict = new District(id, "Дарницкий");
        Assert.assertEquals(shouldSucceedMessage, districtDao.save(newDistrict), 1);
        List<District> resultList = districtDao.list();
        resultList.forEach(System.out::println);
        Assert.assertEquals("New district should be added.", 1, resultList.size() - initialList.size());

        //delete district
        Assert.assertEquals(shouldSucceedMessage, 1, districtDao.delete(id));
        List<District> finalList = districtDao.list();
        finalList.forEach(System.out::println);

        //district is absent
        Assert.assertEquals("District should be removed.", districtDao.get(id), null);
        Assert.assertEquals("District should be removed.", resultList.size() - finalList.size(), 1);
    }

    @Test
    public void testUpdateDistrict() {
        int id = 1;
        //check that district is present
        District result = districtDao.get(id);
        System.out.println(String.format("Got a district by id=%d: %s", id, result.toString()));
        Assert.assertEquals("District name must match.", "Оболонь", result.getName());

        //test update
        District updatedDistrict = new District(id, "Абалонь");
        Assert.assertEquals(shouldSucceedMessage, districtDao.update(updatedDistrict), 1);
        result = districtDao.get(id);
        Assert.assertEquals("District name must match.", "Абалонь", result.getName());
    }

    @Test
    public void testListInsertDeletePhone() {
        List<Phone> initialList = phoneDao.list();
        initialList.forEach(System.out::println);
        Assert.assertEquals("Initial count doesn't not match.", 6, initialList.size());

        //add new phone record
        int personId = 5;
        Phone newPhone = new Phone(personId, "0631234565");
        Assert.assertEquals(shouldSucceedMessage, 1, phoneDao.save(newPhone));
        List<String> personsPhones = ((PhoneDaoImpl) phoneDao).list(personId);
        personsPhones.forEach(System.out::println);
        Assert.assertEquals("Phone count doesn't not match.", 1, personsPhones.size());
        //add two more phones
        newPhone.setPhone("0977894563");
        Assert.assertEquals(shouldSucceedMessage, 1, phoneDao.save(newPhone));
        newPhone.setPhone("0980004562");
        Assert.assertEquals(shouldSucceedMessage, 1, phoneDao.save(newPhone));
        personsPhones = ((PhoneDaoImpl) phoneDao).list(personId);
        personsPhones.forEach(System.out::println);
        Assert.assertEquals("Phone count doesn't not match.", 3, personsPhones.size());

        //delete phone
        Assert.assertEquals(shouldSucceedMessage, 1, ((PhoneDaoImpl) phoneDao).delete(newPhone));
        personsPhones = ((PhoneDaoImpl) phoneDao).list(personId);
        personsPhones.forEach(System.out::println);
        Assert.assertEquals("Phone count doesn't not match.", 2, personsPhones.size());
        //delete another two by one request
        Assert.assertEquals(shouldSucceedMessage, 2, phoneDao.delete(personId));
        personsPhones = ((PhoneDaoImpl) phoneDao).list(personId);
        Assert.assertEquals("List should be empty.", 0, personsPhones.size());

        //phone is absent
        initialList = phoneDao.list();
        Assert.assertEquals("Phone count doesn't not match.", 6, initialList.size());
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
        Assert.assertEquals("Initial count doesn't not match.", 6, initialList.size());

        //add new email record
        int personId = 3;
        Email newEmail = new Email(personId, "fedorov.fedor@gmail.com");
        Assert.assertEquals(shouldSucceedMessage, 1, emailDao.save(newEmail));
        List<String> personsEmails = ((EmailDaoImpl) emailDao).list(personId);
        personsEmails.forEach(System.out::println);
        Assert.assertEquals("Email count doesn't not match.", 1, personsEmails.size());
        //add two more emails
        newEmail.setEmail("0977894563");
        Assert.assertEquals(shouldSucceedMessage, 1, emailDao.save(newEmail));
        newEmail.setEmail("0980004562");
        Assert.assertEquals(shouldSucceedMessage, 1, emailDao.save(newEmail));
        personsEmails = ((EmailDaoImpl) emailDao).list(personId);
        personsEmails.forEach(System.out::println);
        Assert.assertEquals("Email count doesn't not match.", 3, personsEmails.size());

        //delete email
        Assert.assertEquals(shouldSucceedMessage, 1, ((EmailDaoImpl) emailDao).delete(newEmail));
        personsEmails = ((EmailDaoImpl) emailDao).list(personId);
        personsEmails.forEach(System.out::println);
        Assert.assertEquals("Email count doesn't not match.", 2, personsEmails.size());
        //delete another two by one request
        Assert.assertEquals(shouldSucceedMessage, 2, emailDao.delete(personId));
        personsEmails = ((EmailDaoImpl) emailDao).list(personId);
        Assert.assertEquals("List should be empty.", 0, personsEmails.size());

        //email is absent
        initialList = emailDao.list();
        Assert.assertEquals("Email count doesn't not match.", 6, initialList.size());
    }
}
