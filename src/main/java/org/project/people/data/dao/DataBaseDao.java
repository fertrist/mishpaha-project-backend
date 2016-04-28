package org.project.people.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by fertrist on 09.10.15.
 */
public class DataBaseDao {

    private static final String OS = System.getProperty("os.name");
    private static final String SEPARATOR = OS.contains("Windows") ? "\\" : "/";
    private static final String INIT_SCRIPT = /*String.format("main%sresources%ssql%screateTables.sql",
        SEPARATOR, SEPARATOR, SEPARATOR)*/ "G:\\programming\\people-project\\src\\main\\resources\\sql\\mysql" +
        "\\mysqlTables.sql";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DataBaseDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int cleanTables(String... tables) {
        return JdbcTestUtils.deleteFromTables(jdbcTemplate, tables);
    }

    public int createTables() {
        //return jdbcTemplate.update(readScript(INIT_SCRIPT));
        try {
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new FileSystemResource(INIT_SCRIPT));
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String readScript(String pathToScript) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(pathToScript));
            StringBuilder builder = new StringBuilder();
            String line;
            while (!StringUtils.isEmpty(line = reader.readLine())) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void dropTables(String... tables) {
        JdbcTestUtils.dropTables(jdbcTemplate, tables);
    }

}
