package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.QuizQuestion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizQuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizQuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<QuizQuestion> rowMapper = (rs, rowNum) -> {
        QuizQuestion qq = new QuizQuestion();
        qq.setQqId(rs.getInt("qq_id"));
        qq.setQuizId(rs.getInt("quiz_id"));
        qq.setQuestionId(rs.getInt("question_id"));
        int userChoiceId = rs.getInt("user_choice_id");
        if (rs.wasNull()) {
            qq.setUserChoiceId(null);
        } else {
            qq.setUserChoiceId(userChoiceId);
        }
        return qq;
    };

    public int insert(QuizQuestion qq) {
        String sql = "INSERT INTO QuizQuestion (quiz_id, question_id, user_choice_id) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, qq.getQuizId(), qq.getQuestionId(), qq.getUserChoiceId());
    }

    public List<QuizQuestion> findByQuiz(int quizId) {
        String sql = "SELECT * FROM QuizQuestion WHERE quiz_id=?";
        return jdbcTemplate.query(sql, rowMapper, quizId);
    }

    public int updateUserChoice(int qqId, Integer choiceId) {
        String sql = "UPDATE QuizQuestion SET user_choice_id=? WHERE qq_id=?";
        return jdbcTemplate.update(sql, choiceId, qqId);
    }
}