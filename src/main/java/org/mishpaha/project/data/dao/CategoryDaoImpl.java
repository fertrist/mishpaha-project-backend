package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Category;

import javax.sql.DataSource;
import java.util.List;

/**
 * Category dao impl.
 */
public class CategoryDaoImpl extends DaoImplementation<Category> {

    public CategoryDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int saveOrUpdate(Category entity) {
        if (entity.getId() > 0) {
            String sql = String.format("UPDATE %s SET district=? WHERE id=?", table);
            return jdbcOperations.update(sql, entity.getCategory(), entity.getId());
        } else {
            String sql = String.format("INSERT INTO %s (district) values (?)", table);
            return jdbcOperations.update(sql, entity.getCategory());
        }
    }

    @Override
    public Category get(int id) {
        String sql = String.format(SELECT, table, id);
        return jdbcOperations.query(sql, rs -> {
            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("category"));
            }
            return null;
        });
    }

    @Override
    public List<Category> list() {
        return jdbcOperations.query(String.format(SELECT_ALL, table), (rs, rowNum) -> {
            return new Category(rs.getInt("id"), rs.getString("category"));
        });
    }
}
