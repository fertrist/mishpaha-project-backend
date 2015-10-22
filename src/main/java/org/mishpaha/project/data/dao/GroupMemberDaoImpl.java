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
        return operations.update(String.format("INSERT INTO %s (personId, groupId, categoryId) values(%d, %d, %d)",
            table, entity.getPersonId(), entity.getGroupId(), entity.getCategoryId()));
    }

    @Override
    public int update(GroupMember entity) {
        return operations.update(String.format("UPDATE %s SET groupId=%d,categoryId=%d WHERE personId=%d",
            table, entity.getGroupId(), entity.getCategoryId(), entity.getPersonId()));
    }

    @Override
    public GroupMember get(int id) {
        return operations.query(String.format("SELECT FROM %s WHERE personId=%d", table, id), rs -> {
            if (rs.next()) {
                return new GroupMember(rs.getInt("personId"), rs.getInt("groupId"), rs.getInt("categoryId"));
            }
            return null;
        });
    }

    @Override
    public List<GroupMember> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, rowNum) -> {
            return new GroupMember(rs.getInt("personId"), rs.getInt("groupId"), rs.getInt("categoryId"));
        });
    }

    public int delete(GroupMember member) {
        return operations.update("DELETE FROM %s WHERE personId=%d", table, member.getPersonId());
    }
}
