package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Category> rowMapper = (rs, rowNum) -> {
        Category c = new Category();
        c.setCategoryId(rs.getInt("category_id"));
        c.setName(rs.getString("name"));
        return c;
    };

    public List<Category> findAll() {
        String sql = "SELECT * FROM Category";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Category findById(int id) {
        String sql = "SELECT * FROM Category WHERE category_id = ?";
        List<Category> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public int insert(Category c) {
        String sql = "INSERT INTO Category (name) VALUES (?)";
        return jdbcTemplate.update(sql, c.getName());
    }

    public int updateName(int categoryId, String newName) {
        String sql = "UPDATE Category SET name=? WHERE category_id=?";
        return jdbcTemplate.update(sql, newName, categoryId);
    }

    public int delete(int categoryId) {
        String sql = "DELETE FROM Category WHERE category_id=?";
        return jdbcTemplate.update(sql, categoryId);
    }

}