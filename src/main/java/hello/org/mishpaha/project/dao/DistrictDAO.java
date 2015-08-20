package hello.org.mishpaha.project.dao;


import hello.org.mishpaha.project.model.District;

import java.util.List;

public interface DistrictDAO {

    public int saveOrUpdate(District district);

    public int delete(int districtId);

    public District get(int districtId);

    public List<District> list();
}
