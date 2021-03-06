package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.*;
import org.mishpaha.project.util.DateUtil;
import org.mishpaha.project.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mishpaha.project.util.Util.getQuotedString;
import static org.springframework.util.StringUtils.isEmpty;


/**
 * Person dao implementation.
 */
public class PersonDaoImpl extends DaoImplementation<Person> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDaoImpl.class);

    public PersonDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }
    private String template = "'%s'";

    @Override
    public int save(Person entity) {
        String sql = "INSERT INTO " + table + " (%s) values (%s)";
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (entity.getId() != 0) {
            fields.add("id");
            values.add(String.valueOf(entity.getId()));
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
            values.add(String.format(template, entity.getSex()));
        }
        if (entity.isJew() != null) {
            fields.add("isJew");
            values.add(String.format(template, entity.isJew()));
        }
        if (entity.givesTithe() != null) {
            fields.add("givesTithe");
            values.add(String.format(template, entity.givesTithe()));
        }
        if (entity.getCategoryId() != 0) {
            fields.add("categoryId");
            values.add(String.valueOf(entity.getCategoryId()));
        }
        if (entity.getBirthDay() != null) {
            fields.add("birthday");
            values.add(getQuotedString(entity.getBirthDay().toString()));
        }
        if (entity.getComment() != null) {
            fields.add("address");
            values.add(getQuotedString(entity.getAddress()));
        }
        if (entity.getComment() != null) {
            fields.add("comment");
            values.add(getQuotedString(entity.getComment()));
        }
        sql = String.format(sql, String.join(", ", fields), String.join(", ", values));
        LOGGER.info(sql);
        return operations.update(sql);
    }

    public Person updatePerson(Person person) {
        int result = update(person);
        return get(person.getId());
    }

    @Override
    public int update(Person entity) {
        List<String> params = new ArrayList<>();
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
            params.add("sex=" + String.format(template, entity.getSex()));
        }
        if (entity.getSex() != null) {
            params.add("isJew=" + String.format(template, entity.isJew()));
        }
        if (entity.givesTithe() != null) {
            params.add("givesTithe=" + String.format(template, entity.givesTithe()));
        }
        if (entity.givesTithe() != null) {
            params.add("categoryId=" + entity.getCategoryId());
        }
        if (entity.getBirthDay() != null) {
            params.add("birthday=" + String.format(template, entity.getBirthDay().toString()));
        }
        if (!isEmpty(entity.getAddress())) {
            params.add("address=" + String.format(template, entity.getAddress()));
        }
        if (!isEmpty(entity.getComment())) {
            params.add("comment=" + String.format(template, entity.getComment()));
        }
        if (!isEmpty(entity.getAddress())) {
            params.add("address=" + String.format(template, entity.getAddress()));
        }
        if (!isEmpty(entity.getComment())) {
            params.add("comment=" + String.format(template, entity.getComment()));
        }
        String sql = String.format(UPDATE, table, String.join(", ", params));
        return operations.update(sql, entity.getId());
    }

    private static Person getPerson(ResultSet rs) throws SQLException {
        return new Person(
            rs.getInt("id"),
            rs.getString("firstName"),
            rs.getString("midName"),
            rs.getString("lastName"),
            rs.getBoolean("sex"),
            DateUtil.fromDate(rs.getDate("birthday")),
            rs.getBoolean("isJew"),
            rs.getBoolean("givesTithe"),
            rs.getInt("categoryId"),
            rs.getString("address"),
            rs.getString("comment")
        );
    }

    @Override
    public Person get(int id) {
        String sql = String.format(SELECT, table, id);
        return operations.query(sql, rs -> {
            if (rs.next()) {
                return getPerson(rs);
            }
            return null;
        });
    }

    @Override
    public List<Person> list() {
        String sql = String.format(SELECT_ALL, table);
        return operations.query(sql, (rs, numRow) -> {
            return getPerson(rs);
        });
    }

    public List<Person> listGroup(int groupId) {
        String groupMembers = ModelUtil.getTable(GroupMember.class);
        String sql = String.format("SELECT * FROM %1$s JOIN %2$s WHERE %3$s.id=%4$s.personId AND %5$s.groupId=%6$d",
                table, groupMembers, table, groupMembers, groupMembers, groupId);
        return operations.query(sql, (rs, numRow) -> {
            return getPerson(rs);
        });
    }

}
