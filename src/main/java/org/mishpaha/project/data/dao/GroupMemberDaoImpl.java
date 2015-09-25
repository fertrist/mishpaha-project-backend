package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.GroupMember;

import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class GroupMemberDaoImpl implements GenericDao<GroupMember>{
    @Override
    public int saveOrUpdate(GroupMember entity) {
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
