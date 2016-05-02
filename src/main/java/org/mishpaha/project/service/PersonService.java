package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.EmailDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.PhoneDaoImpl;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private GenericDao<Person> personDao;
    @Autowired
    private GenericDao<Phone> phoneDao;
    @Autowired
    private GenericDao<Email> emailDao;

    public List<String> getPersonEmails(int id) {
        return ((EmailDaoImpl) emailDao).list(id);
    }

    public List<String> getPersonPhones(int id) {
        return ((PhoneDaoImpl) phoneDao).list(id);
    }

    public Person getPerson(int id) {
        return personDao.get(id);
    }

    /**
     * P1
     * @param person
     * @return
     */
    public int savePerson(Person person) {
        return personDao.save(person);
    }

    /**
     * P2
     * @param person
     * @param groupId
     */
    public void movePersonToGroup(Person person, int groupId) {

    }

    /**
     * P1
     */
    public void movePersonFromGroup() {

    }

}
