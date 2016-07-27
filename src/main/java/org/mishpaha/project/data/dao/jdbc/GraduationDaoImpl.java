package org.mishpaha.project.data.dao.jdbc;

import org.mishpaha.project.data.model.Graduation;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class GraduationDaoImpl extends DaoImplementation<Graduation>{

    public GraduationDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Graduation entity) {
        return operations.update(String.format("INSERT INTO %s (personId, schoolId) values (%d, %d)",
            table, entity.getPersonId(), entity.getSchoolId()));
    }

    @Override
    public int update(Graduation entity) {
        return 0;
    }

    @Override
    public Graduation get(int id) {
        return null;
    }

    @Override
    public List<Graduation> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, rowNum) -> {
            return new Graduation(rs.getInt("personId"), rs.getInt("schoolId"));
        });
    }

    public int delete(Graduation graduation) {
        return operations.update(String.format("DELETE FROM %s WHERE personId=%d AND schoolId=%d",
            table, graduation.getPersonId(), graduation.getSchoolId()));
    }

}
