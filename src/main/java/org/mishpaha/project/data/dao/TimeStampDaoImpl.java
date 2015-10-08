package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.TimeStamp;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class TimeStampDaoImpl extends DaoImplementation<TimeStamp>{

    public TimeStampDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(TimeStamp entity) {
        return 0;
    }

    @Override
    public int update(TimeStamp entity) {
        return 0;
    }

    @Override
    public TimeStamp get(int id) {
        return null;
    }

    @Override
    public List<TimeStamp> list() {
        return null;
    }
}
