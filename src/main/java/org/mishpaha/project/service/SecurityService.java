package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.GroupDaoImpl;
import org.mishpaha.project.data.dao.RegionDaoImpl;
import org.mishpaha.project.data.dao.SecurityDaoImpl;
import org.mishpaha.project.data.dao.Unit.Units;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.User;
import org.mishpaha.project.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        if (user.getRoles() != null) {
            Set<String> roles = new HashSet<>(user.getRoles());
            for (String role : user.getRoles()) {
                if (!(role.contains(Units.GROUP.name()) || role.contains(Units.REGION.name())
                    || role.contains(Units.TRIBE.name()))) {
                    continue;
                }
                roles.addAll(expandRole(role));
            }
            user.setRoles(new ArrayList<>(roles));
        }
        User created = securityDao.save(user);
        created.setRoles(compressRoles(created.getRoles()));
        return created;
    }

    public Set<String> expandRole(String role) {
        Set<String> roles = new HashSet<>();
        int id = Util.getUnitIdFromRole(role);
        Units unit = Util.getUnitFromRole(role);
        if (unit == Units.REGION) {
            ((GroupDaoImpl) groupDao).getGroupsForRegion(id).stream()
                .forEach(groupId -> roles.add("ROLE_GROUP_" + groupId));
        } else if (unit == Units.TRIBE) {
            ((RegionDaoImpl) regionDao).getRegionsForTribe(id).stream()
                .forEach(regionId -> {
                    roles.add("ROLE_REGION_" + regionId);
                    ((GroupDaoImpl) groupDao).getGroupsForRegion(regionId).stream()
                        .forEach(groupId -> roles.add("ROLE_GROUP_" + groupId));
                });
        }
        return roles;
    }

    public List<String> compressRoles(List<String> roles) {
        Set<String> compressed = new HashSet<>(roles);
        roles.stream().forEach(role -> {
            Units unit = Util.getUnitFromRole(role);
            if (unit != Units.TRIBE && unit != Units.REGION) {
                return;
            }
            int id = Util.getUnitIdFromRole(role);
            if (unit == Units.TRIBE) {
                ((RegionDaoImpl) regionDao).getRegionsForTribe(id).stream().forEach(regionId -> {
                        compressed.remove("ROLE_REGION_" + regionId);
                        ((GroupDaoImpl) groupDao).getGroupsForRegion(regionId).stream()
                            .forEach(groupId -> compressed.remove("ROLE_GROUP_" + groupId));
                    });
            } else {
                ((GroupDaoImpl) groupDao).getGroupsForRegion(id).stream()
                    .forEach(groupId -> compressed.remove("ROLE_GROUP_" + groupId));
            }
        });
        return new ArrayList<>(compressed);
    }

    public User update(User user) {
        User existent = securityDao.get(user.getUsername());
        if (user.getPassword() != null && !passwordEncoder.matches(user.getPassword(), existent.getPassword())){
            securityDao.updatePassword(user.getUsername(), user.getPassword());
        }
        if (user.getRoles() != null) {
            //check are there any new roles added
            List<String> newRoles = new ArrayList<>(user.getRoles());
            newRoles.removeAll(existent.getRoles());
            Set<String> roles = new HashSet<>(user.getRoles());
            for (String role : user.getRoles()) {
                if (!(role.contains(Units.GROUP.name()) || role.contains(Units.REGION.name())
                    || role.contains(Units.TRIBE.name()))) {
                    continue;
                }
                roles.addAll(expandRole(role));
            }
            user.setRoles(new ArrayList<>(roles));
            securityDao.saveRoles(user.getUsername(), newRoles);

            //check if there some roles were removed
            existent.getRoles().removeAll(user.getRoles());
            securityDao.deleteRoles(user.getUsername(), existent.getRoles());
        }
        return securityDao.get(user.getUsername());
    }

    public int delete(String username){
        if (securityDao.deleteRoles(username) == 0) {
            return 0;
        }
        return securityDao.delete(username);
    }

    public List<User> getAllUsers() {
        List<User> users = securityDao.list();
        users.forEach(user -> user.setRoles(compressRoles(user.getRoles())));
        return users;
    }
}
