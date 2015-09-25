package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Project;

import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class ProjectDaoImpl implements GenericDao<Project>{
    @Override
    public int saveOrUpdate(Project entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Project get(int id) {
        return null;
    }

    @Override
    public List<Project> list() {
        return null;
    }
}
