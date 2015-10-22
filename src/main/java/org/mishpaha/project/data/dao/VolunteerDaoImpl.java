package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Volunteer;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class VolunteerDaoImpl extends DaoImplementation<Volunteer> {

    public VolunteerDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Volunteer entity) {
        return operations.update(String.format(INSERT, table, "personId, ministryId",
            new StringBuilder(String.valueOf(entity.getPersonId())).append(",").append(entity.getMinistryId())));
    }

    @Override
    public int update(Volunteer entity) {
        return 0;
    }

    @Override
    public Volunteer get(int id) {
        return null;
    }

    @Override
    public List<Volunteer> list() {
        return null;
    }

    public List<String> list(int personId) {
        String ministries = "ministries";
        return operations.query(String.format("SELECT %s.name FROM %s ", ministries, table)
            + String.format("JOIN %s ON %s.ministryId=%s.id", ministries, table, ministries)
            + String.format("WHERE %s.personId=%d", table, personId), (rs, rowNum) -> {
            return rs.getString("ministries.name");
        });
    }

    public int delete(Volunteer volunteer) {
        return operations.update(String.format("DELETE FROM %s WHERE personId=%d AND ministryId=%d",
            table, volunteer.getPersonId(), volunteer.getMinistryId()));
    }
}
