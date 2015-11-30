package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Region;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.String.format;

/**
 * Created by fertrist on 24.09.15.
 */
public class RegionDaoImpl extends DaoImplementation<Region>{
    public RegionDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Region region) {
        return operations.update(format("INSERT INTO %s (id, leaderId, tribeId) values (%d, %d, %d)",
            table, region.getId(), region.getLeaderId(), region.getTribeId()));
    }

    @Override
    public int update(Region region) {
        return 0;
    }

    @Override
    public Region get(int id) {
        return operations.query(format(SELECT, table, id), rs -> {
            if (rs.next()) {
                return new Region(rs.getInt("id"), rs.getInt("leaderId"), rs.getInt("tribeId"));
            }
            return null;
        });
    }

    @Override
    public List<Region> list() {
        return operations.query(format(SELECT_ALL, table), (rs, rowNum) -> {
            return new Region(rs.getInt("leaderId"), rs.getInt("tribeId"));
        });
    }

    public List<Integer> getRegionsForTribe(int tribeId) {
        return operations.query(format("SELECT * from %s WHERE tribeId=%d", table, tribeId),
            (rs, rowNum) -> {
                return rs.getInt("id");
            });
    }
}
