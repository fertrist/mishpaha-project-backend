package org.mishpaha.project.controller;

import org.mishpaha.project.data.dao.DistrictDao;
import org.mishpaha.project.data.model.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistrictController {

    @Autowired
    private DistrictDao districtDao;

    @RequestMapping("/district")
    public District getDistricts() {
            return districtDao.get(1);
    }
}
