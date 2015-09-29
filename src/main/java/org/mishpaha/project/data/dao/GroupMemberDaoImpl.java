package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.GroupMember;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class GroupMemberDaoImpl extends DaoImplementation<GroupMember>{

    public GroupMemberDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(GroupMember entity) {
        return 0;
    }

    @Override
    public int update(GroupMember entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public GroupMember get(int id) {
        return null;
    }

    @Override
    public List<GroupMember> list() {
        return null;
    }
}
