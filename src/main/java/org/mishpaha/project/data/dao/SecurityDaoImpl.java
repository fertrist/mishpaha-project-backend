package org.mishpaha.project.data.dao;

import org.mishpaha.project.config.SecurityConfig;
import org.mishpaha.project.data.model.User;
import org.mishpaha.project.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.format;

public class SecurityDaoImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityDaoImpl.class);
    protected JdbcOperations operations;

    public SecurityDaoImpl(DataSource dataSource) {
        operations = new JdbcTemplate(dataSource);
    }

    public User save(User user) {
        String sql = format("INSERT INTO users(username, password) VALUES (?, ?)");
        LOGGER.info(sql);
        StandardPasswordEncoder passwordEncoder = new StandardPasswordEncoder(SecurityConfig.SECRET);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        if (operations.update(sql, user.getUsername(), hashedPassword) == 0) {
            return null;
        } else {
            if (user.getRoles() != null) {
                saveRoles(user.getUsername(), user.getRoles());
            }
            User created = get(user.getUsername());
            return created;
        }
    }

    public int saveRoles(String username, List<String> roles) {
        String sql = format("INSERT INTO user_roles (username, role) VALUES ('%s',?)", username);
        int [] results = operations.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, roles.get(i));
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
        if (roles.size() != results.length) {
            return 0;
        }
        for (int i = 0; i < results.length; i++) {
            if (results[i] == 0) {
                return 0;
            }
        }
        return 1;
    }

    public User get(String username) {
        User user = operations.query("SELECT * FROM users WHERE username=" + Util.getQuotedString(username),
            rs -> {
                if (rs.next()) {
                    return new User(rs.getString("username"), rs.getString("password"));
                } else {
                    return null;
                }
            });
        user.setRoles(getRoles(username));
        return user;
    }

    public List<String> getRoles(String username){
        return operations.query("SELECT role FROM user_roles WHERE username=" + Util.getQuotedString(username),
            (resultSet, i) -> {return resultSet.getString("role");});
    }

    public int delete(String username) {
        return operations.update("DELETE FROM users WHERE username=" + Util.getQuotedString(username));
    }

    public int deleteRoles(String username) {
        return operations.update("DELETE FROM user_roles WHERE username=" + Util.getQuotedString(username));
    }

    public List<User> list() {
        List<User> users = operations.query("SELECT username, password FROM users",
            (rs, numRow) -> {
                return new User(rs.getString("username"), rs.getString("password"));
            });
        for (User user : users) {
            user.setRoles(getRoles(user.getUsername()));
        }
        return users;
    }

}
