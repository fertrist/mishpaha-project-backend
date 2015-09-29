package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.District;

import javax.sql.DataSource;
import java.util.List;


public class DistrictDaoImpl extends DaoImplementation<District> {

    public DistrictDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int saveOrUpdate(District entity) {
        if (entity.getId() > 0) {
            String sql = String.format("UPDATE %s SET name=? WHERE id=?", table);
            return jdbcOperations.update(sql, entity.getName(), entity.getId());
        } else {
            String sql = String.format("INSERT INTO %s (name) values (?)", table);
            return jdbcOperations.update(sql, entity.getName());
        }
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
