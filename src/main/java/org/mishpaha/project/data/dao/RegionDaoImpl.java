package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Region;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class RegionDaoImpl extends DaoImplementation<Region>{
    public RegionDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Region entity) {
        return operations.update(String.format("INSERT INTO %s (leaderId, tribeId) values (%d, %d)",
            table, entity.getLeaderId(), entity.getTribeId()));
    }

    @Override
    public int update(Region entity) {
        return 0;
    }

    @Override
    public Region get(int id) {
        return null;
    }

    @Override
    public List<Region> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, rowNum) -> {
            return new Region(rs.getInt("leaderId"), rs.getInt("tribeId"));
        });
    }

    public int delete(Region region) {
        return operations.update(String.format("DELETE FROM %s WHERE leaderId=%d AND tribeId=%d",
            table, region.getLeaderId(), region.getTribeId()));
    }
}
