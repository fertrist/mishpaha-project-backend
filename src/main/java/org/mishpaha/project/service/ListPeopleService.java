package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.GroupDaoImpl;
import org.mishpaha.project.data.dao.PersonDaoImpl;
import org.mishpaha.project.data.dao.RegionDaoImpl;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.Tribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fertrist on 23.10.15.
 */
@Service
public class ListPeopleService {

    @Autowired
    private GenericDao<Person> personDao;
    @Autowired
    private GenericDao<Group> groupDao;
    @Autowired
    private GenericDao<Region> regionDao;
    @Autowired
    private GenericDao<Tribe> tribeDao;

    public Person getPerson(int id) {
        return personDao.get(id);
    }

    public int savePerson(Person person) {
        return personDao.save(person);
    }

    public void movePersonToGroup(Person person, int groupId) {

    }

    public void movePersonFromGroup() {

    }

    public Group getGroup(int groupId) {
        Group group = groupDao.get(groupId);
        group.setPersons(((PersonDaoImpl) personDao).listGroup(groupId));
        return group;
    }

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

}
