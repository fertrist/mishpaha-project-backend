/*
package org.mishpaha.project.data.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by fertrist on 24.09.15.
 *//*

public abstract class SQLTemplate {

    private static Map<Class, String> TABLES = new HashMap<>();
    static {
        TABLES.put(PersonDaoImpl.class, "persons");
        TABLES.put(DistrictDaoImpl.class, "districts");
    }

    public String getListSQL() {
        return String.format("SELECT * FROM %s", TABLES.get(this.getClass()));
    }

    public String getSelectSQL(int id) {
        return String.format("SELECT * FROM %s WHERE id=%d", TABLES.get(this.getClass()), id);
    }

    public String getDeleteSQL(int id) {
        return String.format("DELETE FROM %s WHERE id=%d", TABLES.get(this.getClass()), id);
    }

    public String getInsertSQL(Map<String, String> map) {

        return String.format("INSERT INTO %s (%s) values (%s)", TABLES.get(this.getClass()),
            String.join(", ", fields), String.join(", ", values));
    }

    public String getUpdateSQL(int id, List<String> fields, List<String> values) {
        StringBuilder updatedFields = new StringBuilder();
        if (fields.size() != values.size()) return "";
        updatedFields.append(fields.get(0)).append("=").append("'").append(values.get(0)).append("'");
        for (int i = 1; i < fields.size(); i++) {
            updatedFields.append(", ").append(fields.get(i)).append("=").append(values.get(i));
        }
        return String.format("UPDATE %s SET %s WHERE id=%d",
            TABLES.get(this.getClass()), updatedFields.toString(), id);
    }
}
*/
