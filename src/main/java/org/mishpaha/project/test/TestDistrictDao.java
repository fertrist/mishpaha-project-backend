package org.mishpaha.project.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.data.dao.DistrictDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests for basic jdbc operations.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfiguration.class)
public class TestDistrictDao {

    @Autowired
    private DistrictDAO districtDao;

    @Test
    public void testInsertion() {
//        District district = new District("");
//        assertEquals(districtDao.saveOrUpdate(), 1, "");
    }

}
