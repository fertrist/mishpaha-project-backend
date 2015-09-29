package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.GroupChange;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class GroupChangeDaoImpl extends DaoImplementation<GroupChange> {

    public GroupChangeDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(GroupChange entity) {
        return 0;
    }

    @Override
    public int update(GroupChange entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public GroupChange get(int id) {
        return null;
    }

    @Override
    public List<GroupChange> list() {
        return null;
    }
}
