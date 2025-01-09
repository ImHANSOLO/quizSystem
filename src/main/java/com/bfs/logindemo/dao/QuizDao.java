package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Quiz;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Quiz> rowMapper = (rs, rowNum) -> {
        Quiz q = new Quiz();
        q.setQuizId(rs.getInt("quiz_id"));
        q.setUserId(rs.getInt("user_id"));
        q.setCategoryId(rs.getInt("category_id"));
        q.setName(rs.getString("name"));
        Timestamp start = rs.getTimestamp("time_start");
        Timestamp end = rs.getTimestamp("time_end");
        if (start != null) q.setTimeStart(start.toLocalDateTime());
        if (end != null) q.setTimeEnd(end.toLocalDateTime());
        return q;
    };

    public int createQuiz(Quiz quiz) {
        String sql = "INSERT INTO Quiz (user_id, category_id, name, time_start) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                quiz.getUserId(),
                quiz.getCategoryId(),
                quiz.getName(),
                Timestamp.valueOf(quiz.getTimeStart()));
    }

    public List<Quiz> findByUser(int userId) {
        String sql = "SELECT * FROM Quiz WHERE user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public Quiz findById(int quizId) {
        String sql = "SELECT * FROM Quiz WHERE quiz_id = ?";
        List<Quiz> list = jdbcTemplate.query(sql, rowMapper, quizId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int updateEndTime(int quizId, LocalDateTime endTime) {
        String sql = "UPDATE Quiz SET time_end = ? WHERE quiz_id = ?";
        return jdbcTemplate.update(sql, Timestamp.valueOf(endTime), quizId);
    }

    public List<Quiz> findAll() {
        String sql = "SELECT * FROM Quiz ORDER BY quiz_id DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Quiz> findByCategory(int categoryId) {
        String sql = "SELECT * FROM Quiz WHERE category_id = ? ORDER BY quiz_id DESC";
        return jdbcTemplate.query(sql, rowMapper, categoryId);
    }
}