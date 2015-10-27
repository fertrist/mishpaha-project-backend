package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.Address;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.mishpaha.project.util.TestUtil.getQuotedString;
import static org.springframework.util.StringUtils.isEmpty;


public class AddressDaoImpl extends DaoImplementation<Address> {

    public AddressDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(Address address) {
        String sql = "INSERT INTO " + table + " (%s) values (%s)";
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        if (!isEmpty(address.getCountry())) {
            fields.add("personId");
            values.add(String.valueOf(address.getPersonId()));
        }
        if (!isEmpty(address.getCountry())) {
            fields.add("country");
            values.add(getQuotedString(address.getCountry()));
        }
        if (!isEmpty(address.getRegion())) {
            fields.add("region");
            values.add(getQuotedString(address.getRegion()));
        }
        if (!isEmpty(address.getCity())) {
            fields.add("city");
            values.add(getQuotedString(address.getCity()));
        }
        if (!isEmpty(address.getDistrict())) {
            fields.add("district");
            values.add(getQuotedString(address.getDistrict()));
        }
        if (!isEmpty(address.getStreet())) {
            fields.add("street");
            values.add(getQuotedString(address.getStreet()));
        }
        if (!isEmpty(address.getBuilding())) {
            fields.add("building");
            values.add(getQuotedString(address.getBuilding()));
        }
        if (address.getFlat() != 0) {
            fields.add("flat");
            values.add(String.valueOf(address.getFlat()));
        }
        sql = String.format(sql, String.join(",", fields), String.join(",", values));
        return operations.update(String.format(sql, fields, values));
    }

    @Override
    public int update(Address address) {
        String sql = String.format("UPDATE %s SET %s WHERE id=%d", table, address.getPersonId());
        List<String> params = new ArrayList<>();
        if (address.getCountry() != null) {
            params.add("country=" + getQuotedString(address.getCountry()));
        }
        if (address.getRegion() != null) {
            params.add("region=" + getQuotedString(address.getRegion()));
        }
        if (address.getCity() != null) {
            params.add("city=" + getQuotedString(address.getCity()));
        }
        if (address.getDistrict() != null) {
            params.add("district=" + getQuotedString(address.getDistrict()));
        }
        if (address.getStreet() != null) {
            params.add("street=" + getQuotedString(address.getStreet()));
        }
        if (address.getBuilding() != null) {
            params.add("building=" + getQuotedString(address.getBuilding()));
        }
        if (address.getFlat() != 0) {
            params.add("flat=" + getQuotedString(String.valueOf(address.getFlat())));
        }
        sql = String.format(sql, String.join(",", params));
        return operations.update(sql);
    }

    @Override
    public Address get(int personId) {
        String sql = String.format("SELECT * from %s WHERE personId=%d", table, personId);
        return operations.query(sql, rs -> {
            if (rs.next()) {
                Address address = new Address();
                address.setPersonId(rs.getInt("personId"));
                address.setCountry(rs.getString("country"));
                address.setRegion(rs.getString("region"));
                address.setCity(rs.getString("city"));
                address.setDistrict(rs.getString("district"));
                address.setStreet(rs.getString("street"));
                address.setBuilding(rs.getString("building"));
                address.setFlat(rs.getInt("flat"));
                return address;
            }
            return null;
        });
    }

    @Override
    public List<Address> list() {
        return operations.query(String.format(SELECT_ALL, table), (rs, rowNum) -> {
            Address address = new Address();
            address.setPersonId(rs.getInt("personId"));
            address.setCountry(rs.getString("country"));
            address.setRegion(rs.getString("region"));
            address.setCity(rs.getString("city"));
            address.setDistrict(rs.getString("district"));
            address.setStreet(rs.getString("street"));
            address.setBuilding(rs.getString("building"));
            address.setFlat(rs.getInt("flat"));
            return address;
        });
    }
}
