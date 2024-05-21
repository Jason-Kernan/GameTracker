package com.example.Game.Tracker.DAO;

import com.example.Game.Tracker.Models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO {
    private JdbcTemplate jdbcTemplate;
    public UserDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<User> getAllUsers(){
        return jdbcTemplate.query("SELECT * FROM users", this::mapRowToUser);
    }

    public User getUserByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", this::mapRowToUser, username);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public User createUser(User user) {
        jdbcTemplate.update("INSERT INTO users (username,password) VALUES (?,?)", user.getUsername(), user.getPassword());
        return getUserByUsername(user.getUsername());

    }

    public User updatedUser(User user) {
        jdbcTemplate.update("UPDATE users SET password = ? WHERE username = ?", user.getPassword(), user.getUsername());
        return getUserByUsername(user.getUsername());
    }

    public int deleteUser (String username) {
        return jdbcTemplate.update("DELETE FROM users WHERE username = ?", username);
    }

    public List<String> getRolesForUsers(String username) {
       return jdbcTemplate.queryForList("SELECT role FROM roles WHERE username = ?", String.class, username);
    }

    public void addRoleToUser(String username, String role) {
        try {
            jdbcTemplate.update("INSERT INTO roles (username, role) VALUES (?,?)", username, role);
        } catch (Exception ex){
        }
    }
    public void removeRoleFromUser (String username, String role) {
        try {
            jdbcTemplate.update("DELETE FROM roles WHERE username = ? AND role = ?", username, role);
        } catch (Exception ex){
        }
    }

    private User mapRowToUser(ResultSet row, int rowNumber) throws SQLException {
        User user = new User();
        user.setUsername(row.getString("username"));
        user.setPassword(row.getString("password"));
        return user;
    }

}
