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
        return operations.update(String.format("INSERT INTO %s (leaderId, regionId) values (%d, %d)",
            table, entity.getLeaderId(), entity.getRegionId()));
    }

    @Override
    public int update(Group entity) {
        return 0;
    }

    @Override
    public Group get(int id) {
        return null;
    }

    @Override
    public List<Group> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, rowNum) -> {
            return new Group(rs.getInt("leaderId"), rs.getInt("regionId"));
        });
    }

    public int delete(Group group) {
        return operations.update(String.format("DELETE FROM %s WHERE leaderId=%d AND tribeId=%d",
            table, group.getLeaderId(), group.getRegionId()));
    }
}
