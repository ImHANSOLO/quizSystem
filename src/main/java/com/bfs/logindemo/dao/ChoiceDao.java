package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Choice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChoiceDao {
    private final JdbcTemplate jdbcTemplate;

    public ChoiceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Choice> rowMapper = (rs, rowNum) -> {
        Choice c = new Choice();
        c.setChoiceId(rs.getInt("choice_id"));
        c.setQuestionId(rs.getInt("question_id"));
        c.setDescription(rs.getString("description"));
        c.setCorrect(rs.getBoolean("is_correct"));
        return c;
    };

    public List<Choice> findByQuestion(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id=?";
        return jdbcTemplate.query(sql, rowMapper, questionId);
    }
}
