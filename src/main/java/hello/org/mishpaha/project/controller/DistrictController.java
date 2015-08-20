package hello.org.mishpaha.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hello.org.mishpaha.project.dao.DistrictDAO;
import hello.org.mishpaha.project.model.District;

@RestController
public class DistrictController {

    @Autowired
    private DistrictDAO districtDAO;

    @RequestMapping("/district")
    public District getDistricts() {
            return districtDAO.get(1);
    }
}
