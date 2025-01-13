package com.bfs.logindemo.repository;

import com.bfs.logindemo.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Replaces old QuizQuestionDao
 */
@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {

    // find by quiz ID
    // Replaces old "findByQuizQuizId"
    List<QuizQuestion> findByQuiz_QuizId(int quizId);

    // find by question ID
    List<QuizQuestion> findByQuestion_QuestionId(int questionId);
}
