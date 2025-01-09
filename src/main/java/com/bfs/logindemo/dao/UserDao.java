package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setFirstname(rs.getString("firstname"));
        u.setLastname(rs.getString("lastname"));
        u.setActive(rs.getBoolean("is_active"));
        u.setAdmin(rs.getBoolean("is_admin"));
        return u;
    };

    public User findByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        List<User> list = jdbcTemplate.query(sql, rowMapper, email);
        return list.isEmpty() ? null : list.get(0);
    }

    public User findById(int userId) {
        String sql = "SELECT * FROM User WHERE user_id = ?";
        List<User> list = jdbcTemplate.query(sql, rowMapper, userId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int save(User user) {
        String sql = "INSERT INTO User(email, password, firstname, lastname, is_active, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getEmail(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.isActive(),
                user.isAdmin());
    }

    public int updateStatus(int userId, boolean isActive) {
        String sql = "UPDATE User SET is_active=? WHERE user_id=?";
        return jdbcTemplate.update(sql, isActive, userId);
    }

    public List<User> findAllUsers() {
        String sql = "SELECT * FROM User";
        return jdbcTemplate.query(sql, rowMapper);
    }

}