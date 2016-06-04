package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.GroupDaoImpl;
import org.mishpaha.project.data.dao.RegionDaoImpl;
import org.mishpaha.project.data.dao.SecurityDaoImpl;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SecurityService {

    @Autowired
    private SecurityDaoImpl securityDao;
    @Autowired
    private GenericDao<Person> personDao;
    @Autowired
    private GenericDao<Region> regionDao;
    @Autowired
    private GenericDao<Group> groupDao;

    public enum Unit {
        GROUP, REGION, TRIBE
    }

    public User save(User user) {
        if (user.getRoles() != null) {
            Set<String> roles = new HashSet<>(user.getRoles());
            for (String role : user.getRoles()) {
                if (!(role.contains(Unit.GROUP.name()) || role.contains(Unit.REGION.name())
                    || role.contains(Unit.TRIBE.name()))) {
                    continue;
                }
                String [] split = role.split("_");
                int id = Integer.valueOf(split[split.length-1]);
                if (role.contains(Unit.REGION.name())) {
                    ((GroupDaoImpl) groupDao).getGroupsForRegion(id).stream()
                        .forEach(groupId -> roles.add("ROLE_GROUP_" + groupId));
                } else if (role.contains(Unit.TRIBE.name())) {
                    ((RegionDaoImpl) regionDao).getRegionsForTribe(id).stream()
                        .forEach(regionId -> {
                            roles.add("ROLE_REGION_" + regionId);
                            ((GroupDaoImpl) groupDao).getGroupsForRegion(regionId).stream()
                                .forEach(groupId -> roles.add("ROLE_GROUP_" + groupId));
                        });
                }
            }
            user.setRoles(new ArrayList<>(roles));
        }
        return securityDao.save(user);
    }

    public int delete(String username){
        if (securityDao.deleteRoles(username) == 0) {
            return 0;
        }
        return securityDao.delete(username);
    }

    public List<User> getAllUsers() {
        return securityDao.list();
    }
}
