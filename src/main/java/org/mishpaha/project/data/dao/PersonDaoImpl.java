package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Person;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;


/**
 * Person dao implementation.
 */
public class PersonDaoImpl extends DaoImplementation<Person> {

    public PersonDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }
    String template = "'%s'";

    @Override
    public int save(Person entity) {
        String sql = "INSERT INTO " + table + " (%s) values (%s)";
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (entity.getId() != 0) {
            fields.add("id");
            values.add("" + entity.getId());
        }
        if (!isEmpty(entity.getFirstName())) {
            fields.add("firstName");
            values.add(String.format(template, entity.getFirstName()));
        }
        if (!isEmpty(entity.getMidName())) {
            fields.add("midName");
            values.add(String.format(template, entity.getMidName()));
        }
        if (!isEmpty(entity.getLastName())) {
            fields.add("lastName");
            values.add(String.format(template, entity.getLastName()));
        }
        if (entity.getSex() != null) {
            fields.add("sex");
            values.add(String.format(template, entity.getSex().booleanValue()));
        }
        if (entity.isJew() != null) {
            fields.add("isJew");
            values.add(String.format(template, entity.isJew().booleanValue()));
        }
        if (entity.givesTithe() != null) {
            fields.add("givesTithe");
            values.add(String.format(template, entity.givesTithe().booleanValue()));
        }
        if (!isEmpty(entity.getAddress())) {
            fields.add("address");
            values.add(String.format(template, entity.getAddress()));
        }
        if (entity.getDistrictId() != 0) {
            fields.add("districtId");
            values.add("" + entity.getDistrictId());
        }
        if (entity.getBirthDay() != null) {
            fields.add("birthday");
            values.add(String.format(template, entity.getBirthdayAsString()));
        }
        sql = String.format(sql, String.join(", ", fields), String.join(", ", values));
        return jdbcOperations.update(sql);
    }

    @Override
    public int update(Person entity) {
        List<String> params = new ArrayList<>();
        if (entity.getId() != 0) {
            params.add("id=" + entity.getId());
        }
        if (!isEmpty(entity.getFirstName())) {
            params.add("firstName=" + String.format(template, entity.getFirstName()));
        }
        if (!isEmpty(entity.getMidName())) {
            params.add("midName=" + String.format(template, entity.getMidName()));
        }
        if (!isEmpty(entity.getLastName())) {
            params.add("lastName=" + String.format(template, entity.getLastName()));
        }
        if (entity.getSex() != null) {
            params.add("sex=" + String.format(template, entity.getSex().booleanValue()));
        }
        if (entity.getSex() != null) {
            params.add("isJew=" + String.format(template, entity.isJew().booleanValue()));
        }
        if (entity.givesTithe() != null) {
            params.add("givesTithe=" + String.format(template, entity.givesTithe().booleanValue()));
        }
        if (!isEmpty(entity.getAddress())) {
            params.add("address=" + String.format(template, entity.getAddress()));
        }
        if (entity.getDistrictId() != 0) {
            params.add("districtId=" + entity.getDistrictId());
        }
        if (entity.getBirthDay() != null) {
            params.add("birthday=" + String.format(template, entity.getBirthdayAsString()));
        }
        String sql = String.format(UPDATE, table, String.join(", ", params));
        return jdbcOperations.update(sql, entity.getId());
    }

    @Override
    public Person get(int id) {
        String sql = String.format(SELECT, table, id);
        return jdbcOperations.query(sql, rs -> {
            if (rs.next()) {
                return new Person(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("midName"),
                    rs.getString("lastName"),
                    rs.getBoolean("sex"),
                    rs.getDate("birthday"),
                    rs.getInt("districtId"),
                    rs.getString("address"),
                    rs.getBoolean("isJew"),
                    rs.getBoolean("givesTithe")
                );
            }
            return null;
        });
    }

    @Override
    public List<Person> list() {
        String sql = String.format(SELECT_ALL, table);
        return jdbcOperations.query(sql, (rs, numRow) -> {
            return new Person(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("midName"),
                rs.getString("lastName"),
                rs.getBoolean("sex"),
                rs.getDate("birthday"),
                rs.getInt("districtId"),
                rs.getString("address"),
                rs.getBoolean("isJew"),
                rs.getBoolean("givesTithe")
            );
        });
    }
}
