package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Project;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class ProjectDaoImpl extends DaoImplementation<Project>{

    public ProjectDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Project entity) {
        return 0;
    }

    @Override
    public int update(Project entity) {
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
