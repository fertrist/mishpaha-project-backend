package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.District;

import javax.sql.DataSource;
import java.util.List;


public class DistrictDaoImpl extends DaoImplementation<District> {

    public DistrictDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(District entity) {
        String sql = (entity.getId() <= 0) ? String.format("INSERT INTO %s (name) values (?)", table)
            : String.format("INSERT INTO %s (id, name) values (%d, ?)", table, entity.getId());
        return jdbcOperations.update(sql, entity.getName());
    }

    @Override
    public int update(District entity) {
        String sql = String.format("UPDATE %s SET name=? WHERE id=?", table);
        return jdbcOperations.update(sql, entity.getName(), entity.getId());
    }

    @Override
    public District get(int id) {
        String sql = String.format(SELECT, table, id);
        return jdbcOperations.query(sql, rs -> {
            if (rs.next()) {
                return new District(rs.getInt("id"), rs.getString("name"));
            }
            return null;
        });
    }

    @Override
    public List<District> list() {
        return jdbcOperations.query(String.format(SELECT_ALL, table), (rs, rowNum) -> {
            return new District(rs.getInt("id"), rs.getString("name"));
        });
    }
}
