package org.mishpaha.project.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.Application;
import org.mishpaha.project.config.Constants;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.User;
import org.mishpaha.project.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(Constants.PROFILE_TEST)
public class ServiceTest extends BaseTestClass {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testCreateRegionTribeRoles() {
        User user = new User("regionLeader", "thePassword123**!");
        user.setRoles(Arrays.asList("ROLE_REGION_1", "ROLE_REGION_2", "ROLE_GROUP_1"));
        User created = securityService.save(user);
        assertEquals(user.getUsername(), created.getUsername());
        assertTrue(passwordEncoder.matches(user.getPassword(), created.getPassword()));
        assertEquals(groupsPerRegion*2+2, created.getRoles().size());
        assertTrue(created.getRoles().contains("ROLE_REGION_1")
            && created.getRoles().contains("ROLE_REGION_2"));
        for (Group group : getGroups()) {
            if (group.getRegionId() != 1 && group.getRegionId() != 2) {
                continue;
            }
            assertTrue(created.getRoles().contains("ROLE_GROUP_" + group.getId()));
        }

        user = new User("tribeLeader", "thePassword321**!");
        user.setRoles(Collections.singletonList("ROLE_TRIBE_1"));
        created = securityService.save(user);
        assertEquals(user.getUsername(), created.getUsername());
        assertTrue(passwordEncoder.matches(user.getPassword(), created.getPassword()));
        assertEquals(groupsPerRegion*regionsPerTribe + regionsPerTribe + 1, created.getRoles().size());
        assertTrue(created.getRoles().contains("ROLE_TRIBE_1"));
        for (Region region : getRegions()) {
            if (region.getTribeId() != 1) {
                continue;
            }
            assertTrue(created.getRoles().contains("ROLE_REGION_" + region.getId()));
            for (Group group : getGroups()) {
                if (group.getRegionId() != region.getId()) {
                    continue;
                }
                assertTrue(created.getRoles().contains("ROLE_GROUP_" + group.getId()));
            }
        }
    }

}
