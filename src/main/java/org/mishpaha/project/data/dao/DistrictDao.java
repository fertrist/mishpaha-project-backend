package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.District;

import java.util.List;

public interface DistrictDao {

    int saveOrUpdate(District district);

    int delete(int districtId);

    District get(int districtId);

    List<District> list();
}
