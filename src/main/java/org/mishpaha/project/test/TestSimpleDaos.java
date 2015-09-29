package org.mishpaha.project.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mishpaha.project.config.MvcConfiguration;
import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.model.Category;
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
public class TestSimpleDaos {

    private final String shouldSucceedMessage = "Request should succeed.";
    private final String shouldFailMessage = "Request should fail.";

    @Autowired
    private GenericDao<District> districtDao;
    @Autowired
    private GenericDao<Category> categoryDao;

    @Test
    public void testListInsertDeleteDistrict() {
        int id = 100;
        List<District> districtList = districtDao.list();
        districtList.forEach(System.out::println);
        District newDistrict = new District("Дарницкий");
        Assert.assertEquals(shouldSucceedMessage, districtDao.saveOrUpdate(newDistrict), 1);
        List<District> resultList = districtDao.list();
        resultList.forEach(System.out::println);
        Assert.assertEquals("New district should be added.", 1, resultList.size() - districtList.size());

        //delete district
        Assert.assertEquals(shouldSucceedMessage, districtDao.delete(newDistrict.getId()), 1);
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
        Assert.assertEquals(shouldSucceedMessage, districtDao.saveOrUpdate(updatedDistrict), 1);
        result = districtDao.get(id);
        Assert.assertEquals("District name must match.", "Абалонь", result.getName());
    }

    @Test
    public void testDeleteDistrict(){
        int id = 0;
        //test delete district
        List<District> initialList = districtDao.list();
        initialList.forEach(System.out::println);
        Assert.assertEquals(shouldSucceedMessage, 1, districtDao.delete(id));
        List<District> finalList = districtDao.list();
        finalList.forEach(System.out::println);

        //district is absent
        Assert.assertEquals("District should be removed.", districtDao.get(id), null);
        Assert.assertEquals("District should be removed.", initialList.size() - finalList.size(), 1);
    }

    @Test
    public void testListInsertCategory() {

    }
}
