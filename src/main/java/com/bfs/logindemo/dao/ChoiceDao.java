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

    /**
     * 根据 questionId 查找所有 Choice
     */
    public List<Choice> findByQuestion(int questionId) {
        String sql = "SELECT * FROM Choice WHERE question_id = ?";
        return jdbcTemplate.query(sql, rowMapper, questionId);
    }

    /**
     * 根据 choiceId 查找单个 Choice
     *
     * @param choiceId 要查找的 choice_id
     * @return 若找到则返回 Choice；否则返回 null
     */
    public Choice findById(int choiceId) {
        String sql = "SELECT * FROM Choice WHERE choice_id = ?";
        List<Choice> list = jdbcTemplate.query(sql, rowMapper, choiceId);
        return (list.isEmpty()) ? null : list.get(0);
    }
}
