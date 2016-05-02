package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.util.ModelUtil;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.String.format;

public class GroupDaoImpl extends DaoImplementation<Group>{

    public GroupDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Group group) {
        return operations.update(format("INSERT INTO %s (id, leaderId, regionId) values (%d, %d, %d)",
            table, group.getId(), group.getLeaderId(), group.getRegionId()));
    }

    @Override
    public int update(Group group) {
        return 0;
    }

    @Override
    public Group get(int id) {
        return operations.query(format("SELECT %1$s.id, %1$s.leaderId, %1$s.regionId, %2$s.firstName, %2$s.midName " +
                "FROM %1$s JOIN %2$s ON %1$s.leaderId=%2$s.id WHERE %1$s.id=%3$d",
            table, ModelUtil.getTable(Person.class), id), rs -> {
            if (rs.next()) {
                Group group = new Group(rs.getInt("id"), rs.getInt("leaderId"), rs.getInt("regionId"));
                group.setLeader(rs.getString("firstName") + " " + rs.getString("midName"));
                return group;
            }
            return null;
        });
    }

    @Override
    public List<Group> list() {
        return operations.query(format(SELECT_ALL, table), (rs, rowNum) -> {
            return new Group(rs.getInt("id"), rs.getInt("leaderId"), rs.getInt("regionId"));
        });
    }

    public int delete(Group group) {
        return operations.update(format("DELETE FROM %s WHERE leaderId=%d AND regionId=%d",
            table, group.getLeaderId(), group.getRegionId()));
    }

    public List<Integer> getGroupsForRegion(int regionId) {
        return operations.query(format("SELECT * from %s WHERE regionId=%d", table, regionId),
            (rs, rowNum) -> {
                return rs.getInt("id");
            });
    }
}
