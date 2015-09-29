package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Group;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class GroupDaoImpl extends DaoImplementation<Group>{

    public GroupDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Group entity) {
        return 0;
    }

    @Override
    public int update(Group entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public Group get(int id) {
        return null;
    }

    @Override
    public List<Group> list() {
        return null;
    }
}
