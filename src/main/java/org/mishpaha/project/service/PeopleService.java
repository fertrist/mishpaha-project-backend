package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.EmailDaoImpl;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.GroupDaoImpl;
import org.mishpaha.project.data.dao.PersonDaoImpl;
import org.mishpaha.project.data.dao.PhoneDaoImpl;
import org.mishpaha.project.data.dao.RegionDaoImpl;
import org.mishpaha.project.data.dao.Unit;
import org.mishpaha.project.data.dao.Unit.Units;
import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private GenericDao<Category> categoryDao;
    @Autowired
    private SecurityService securityService;

    public List<Tribe> getPeople(UserDetails userDetails) {
        //get roles
        List<String> roles = securityService.processRoles(userDetails);
        //get all data
        List<Tribe> result = new ArrayList<>();
        for (String role : roles) {
            Unit unit = getUnitByRole(role);
            addUnitToResult(result, unit);
        }
        return result;
    }

    private Unit getUnitByRole(String role) {
        int id = Util.getUnitIdFromRole(role);
        Units unit = Util.getUnitFromRole(role);
        if (unit == Units.TRIBE) {
            return getTribe(id);
        } else if (unit == Units.REGION) {
            return getRegion(id);
        }
        return getGroup(id);
    }

    private void addUnitToResult(List<Tribe> result, Unit unit) {
        if (unit.getUnit() == Units.TRIBE) {
            Tribe tribe = (Tribe) unit;
            addTribeToResult(result, tribe);
        } else if (unit.getUnit() == Units.REGION) {
            Region region = (Region) unit;
            addRegionToResult(result, region);
        } else if (unit.getUnit() == Units.GROUP) {
            Group group = (Group) unit;
            addGroupToResult(result, group);
        }
    }

    private void addTribeToResult(List<Tribe> result, Tribe tribe) {
        for (Tribe t : result) {
            if (t.getId() != tribe.getId()) {
                continue;
            }
            for (Region region : tribe.getRegions()) {
                if (!containsUnit(t.getRegions(), region.getId())){
                    t.getRegions().add(region);
                } else {
                    t.getRegions().stream().forEach(r -> {
                        if (r.getId() == region.getId()) {
                            appendGroupsWithDiff(r.getGroups(), region.getGroups());
                        }
                    });
                }
            }
            return;
        }
        result.add(tribe);
    }

    private void addRegionToResult(List<Tribe> result, Region region) {
        boolean added = false;
        for (Tribe tribe : result) {
            //check if tribe for that region exists
            if (tribe.getId() != region.getTribeId()) {
                continue;
            }
            //if region already exists just add it's groups
            for (Region reg : tribe.getRegions()) {
                if (reg.getId() == region.getId()) {
                    appendGroupsWithDiff(reg.getGroups(), region.getGroups());
                    return;
                }
            }
            //else just add that region
            added = tribe.getRegions().add(region);
        }
        //if required tribe doesn't exist just create it
        if (!added) {
            Tribe tribe = tribeDao.get(region.getTribeId());
            tribe.getRegions().add(region);
            result.add(tribe);
        }
    }

    private void appendGroupsWithDiff(List<Group> groups, List<Group> diffGroups) {
        diffGroups.stream().forEach(diff -> {
            if (!containsUnit(groups, diff.getId())) {
                groups.add(diff);
            }
        });
    }

    private boolean containsUnit(List<? extends Unit> units, int unitId) {
        for (Unit unit : units) {
            if (unit.getId() == unitId) {
                return true;
            }
        }
        return false;
    }

    private void addGroupToResult(List<Tribe> people, Group group) {
        Region region = regionDao.get(group.getId());
        region.getGroups().add(group);
        boolean added = false;
        for (Tribe tribe : people) {
            if (tribe.getId() != region.getTribeId()) {
                continue;
            }
            for (Region reg : tribe.getRegions()) {
                if (reg.getId() != group.getRegionId()) {
                    continue;
                }
                if (!containsUnit(reg.getGroups(), group.getId())) {
                    added = reg.getGroups().add(group);
                } else {
                    return;
                }
            }
            //if has reached this place, then there is no required region
            if (!added) {
                tribe.getRegions().add(region);
            }
        }
        //if has reached this place, there is no required tribe
        if (!added) {
            Tribe newTribe = tribeDao.get(region.getTribeId());
            newTribe.getRegions().add(region);
            people.add(newTribe);
        }
    }

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
    public Group getGroup(int groupId) {
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
            groups.add(getGroup(groupId));
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

    public List<Category> getCategories() {
        return categoryDao.list();
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
