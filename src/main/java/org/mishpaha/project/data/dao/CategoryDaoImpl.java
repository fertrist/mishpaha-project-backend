package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;

/**
 * Category dao impl.
 */
public class CategoryDaoImpl extends DaoImplementation<Category> {

    public enum Categories{
        white, blue, green, brown, guest
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryDaoImpl.class);

    public CategoryDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Category entity) {
        if (entity.getId() > 0) {
            String sql = String.format("UPDATE %s SET name=? WHERE id=?", table);
            LOGGER.info(sql);
            return operations.update(sql, entity.getName(), entity.getId());
        } else {
            String sql = String.format("INSERT INTO %s (name) values (?)", table);
            LOGGER.info(sql);
            return operations.update(sql, entity.getName());
        }
    }

    @Override
    public int update(Category entity) {
        return 0;
    }

    @Override
    public Category get(int id) {
        String sql = String.format(SELECT, table, id);
        return operations.query(sql, rs -> {
            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("name"));
            }
            return null;
        });
    }

    @Override
    public List<Category> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, rowNum) -> {
            return new Category(rs.getInt("id"), rs.getString("name"));
        });
    }
}
