package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.EmailDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.GroupDaoImpl;
import org.mishpaha.project.data.dao.PersonDaoImpl;
import org.mishpaha.project.data.dao.PhoneDaoImpl;
import org.mishpaha.project.data.dao.RegionDaoImpl;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleService {

    @Autowired
    private GenericDao<Person> personDao;
    @Autowired
    private GenericDao<Group> groupDao;
    @Autowired
    private GenericDao<Region> regionDao;
    @Autowired
    private GenericDao<Tribe> tribeDao;
    @Autowired
    private GenericDao<Phone> phoneDao;
    @Autowired
    private GenericDao<Email> emailDao;

    /**
     * P0
     */
    public Group getGroupSummary(int groupId) {
        Group group = groupDao.get(groupId);
        group.setPersons(((PersonDaoImpl) personDao).listGroup(groupId));
        return group;
    }

    /**
     * P1
     */
    public Group getGroupInfo(int groupId) {
        Group group = groupDao.get(groupId);
        List<Person> persons = ((PersonDaoImpl) personDao).listGroup(groupId);
        for(Person person : persons){
            person.setEmails(getPersonEmails(person.getId()));
            person.setPhones(getPersonPhones(person.getId()));
        }
        group.setPersons(persons);
        return group;
    }

    /**
     * P2
     */
    public Region getRegion(int regionId) {
        Region region = regionDao.get(regionId);
        List<Integer> groupIds = ((GroupDaoImpl) groupDao).getGroupsForRegion(regionId);
        List<Group> groups = new ArrayList<>();
        for (int groupId : groupIds) {
            groups.add(getGroupSummary(groupId));
        }
        region.setGroups(groups);
        return region;
    }

    /**
     * P2
     */
    public Tribe getTribe(int tribeId) {
        Tribe tribe = tribeDao.get(tribeId);
        List<Integer> regionIds = ((RegionDaoImpl) regionDao).getRegionsForTribe(tribeId);
        List<Region> regions = new ArrayList<>();
        for (int regionId : regionIds) {
            regions.add(getRegion(regionId));
        }
        tribe.setRegions(regions);
        return tribe;
    }

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

    public int updatePerson(Person person) {
        return personDao.update(person);
    }

    public int deletePerson(int id) {
        return personDao.delete(id);
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
