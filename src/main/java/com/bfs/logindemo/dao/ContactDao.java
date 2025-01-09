package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Contact;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ContactDao {

    private final JdbcTemplate jdbcTemplate;

    public ContactDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Contact> rowMapper = (rs, rowNum) -> {
        Contact c = new Contact();
        c.setContactId(rs.getInt("contact_id"));
        c.setSubject(rs.getString("subject"));
        c.setMessage(rs.getString("message"));
        c.setEmail(rs.getString("email"));
        Timestamp t = rs.getTimestamp("time");
        if (t != null) c.setTime(t.toLocalDateTime());
        return c;
    };

    public int save(Contact contact) {
        String sql = "INSERT INTO Contact (subject, message, email) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, contact.getSubject(), contact.getMessage(), contact.getEmail());
    }

    public List<Contact> findAll() {
        String sql = "SELECT * FROM Contact ORDER BY time DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }
}