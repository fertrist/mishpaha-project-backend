package org.mishpaha.project.service;

import org.mishpaha.project.data.dao.GenericDao;
import org.mishpaha.project.data.dao.PersonDaoImpl;
import org.mishpaha.project.data.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fertrist on 23.10.15.
 */
@Service
public class PersonService {

    @Autowired
    private GenericDao<Person> personDao;

    public List<Person> getHomeGroupList(int groupId) {
        return ((PersonDaoImpl) personDao).listHomeGroup(groupId);
    }

    public List<Person> getHomeGroupInfoList(int groupId) {
        return ((PersonDaoImpl) personDao).listHomeGroupInfo(groupId);
    }
}
