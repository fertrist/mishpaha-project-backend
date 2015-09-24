package org.mishpaha.project.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.data.dao.DistrictDao;
import org.mishpaha.project.data.model.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Tests for basic jdbc operations.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MvcConfiguration.class)
public class TestDistrictDao {

    @Autowired
    private DistrictDao districtDao;

    @Test
    public void testInsertionList() {
        List<District> districtList = districtDao.list();
        districtList.forEach(System.out::println);
        District newDistrict = new District("Дарницкий");
        Assert.assertEquals("Request should succeed.", districtDao.saveOrUpdate(newDistrict), 1);
        List<District> resultList = districtDao.list();
        resultList.forEach(System.out::println);
        Assert.assertEquals("New district should be added.", 1, resultList.size() - districtList.size());
    }

    @Test
    public void testUpdateDelete() {
        int id = 1;
        District result = districtDao.get(id);
        System.out.println(String.format("Got a district by id=%d: %s", id, result.toString()));
        Assert.assertEquals("District name must match.", "Оболонь", result.getDistrict());
        District updatedDistrict = new District(id, "Абалонь");
        Assert.assertEquals("Request should succeed.", districtDao.saveOrUpdate(updatedDistrict), id);
        districtDao.list().forEach(System.out::println);
        result = districtDao.get(id);
        Assert.assertEquals("District name must match.", "Абалонь", result.getDistrict());
    }

    @Test
    public void testInsertListPhones() {

    }
}
