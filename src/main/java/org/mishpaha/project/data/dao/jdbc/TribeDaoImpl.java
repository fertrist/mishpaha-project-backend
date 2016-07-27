package org.mishpaha.project.data.dao.jdbc;

import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;

public class TribeDaoImpl extends DaoImplementation<Tribe> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TribeDaoImpl.class);

    public TribeDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Tribe entity) {
        String sql = String.format("INSERT INTO %s (name) values (%s)", table,
            Util.getQuotedString(entity.getName()));
        LOGGER.info(sql);
        return operations.update(sql);
    }

    @Override
    public int update(Tribe entity) {
        return operations.update(String.format("UPDATE %s SET name=%s WHERE id=%d",
            table,  Util.getQuotedString(entity.getName()), entity.getId()));
    }

    @Override
    public Tribe get(int id) {
        return operations.query(String.format(SELECT, table, id) ,rs -> {
            if (rs.next()) {
                return new Tribe(rs.getInt("id"), rs.getString("name"));
            }
            return null;
        });
    }

    @Override
    public List<Tribe> list() {
        return operations.query(String.format(SELECT_ALL, table) ,(rs, rowNum) -> {
            return new Tribe(rs.getInt("id"), rs.getString("name"));
        });
    }
}
