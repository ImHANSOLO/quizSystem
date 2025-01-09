package com.bfs.logindemo.dao;


import com.bfs.logindemo.domain.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public QuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Question> rowMapper = (rs, rowNum) -> {
        Question q = new Question();
        q.setQuestionId(rs.getInt("question_id"));
        q.setCategoryId(rs.getInt("category_id"));
        q.setDescription(rs.getString("description"));
        q.setActive(rs.getBoolean("is_active"));
        return q;
    };

    // Search by category
    public List<Question> findByCategory(int categoryId) {
        String sql = "SELECT * FROM Question WHERE category_id = ? AND is_active = true";
        return jdbcTemplate.query(sql, rowMapper, categoryId);
    }

    public List<Question> findAll() {
        String sql = "SELECT * FROM Question";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Question findById(int questionId) {
        String sql = "SELECT * FROM Question WHERE question_id = ?";
        List<Question> list = jdbcTemplate.query(sql, rowMapper, questionId);
        return list.isEmpty() ? null : list.get(0);
    }

    // add new questions
    public int save(Question q) {
        String sql = "INSERT INTO Question (category_id, description, is_active) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, q.getCategoryId(), q.getDescription(), q.isActive());
    }

    // Update title (only category, description, is active)
    public int update(Question q) {
        String sql = "UPDATE Question SET category_id = ?, description = ?, is_active = ? WHERE question_id = ?";
        return jdbcTemplate.update(sql, q.getCategoryId(), q.getDescription(), q.isActive(), q.getQuestionId());
    }

    // delete questions
    public int delete(int questionId) {
        String sql = "DELETE FROM Question WHERE question_id = ?";
        return jdbcTemplate.update(sql, questionId);
    }

    // Set topic activation/deactivation
    public int setActive(int questionId, boolean active) {
        String sql = "UPDATE Question SET is_active = ? WHERE question_id = ?";
        return jdbcTemplate.update(sql, active, questionId);
    }
}