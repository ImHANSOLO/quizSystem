package com.bfs.logindemo.repository;

import com.bfs.logindemo.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Replaces old QuizDao
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    // e.g. find all quizzes for a user, sorted by time_end desc
    List<Quiz> findByUser_UserIdOrderByTimeEndDesc(int userId);

    // e.g. find all quizzes for a category, sorted by time_end desc
    List<Quiz> findByCategory_CategoryIdOrderByTimeEndDesc(int categoryId);

    // If you want a custom ordering
    List<Quiz> findAllByOrderByTimeEndDesc();

    // Optional example custom queries:
    @Query("SELECT q FROM Quiz q ORDER BY q.quizId DESC")
    List<Quiz> findAllQuizIdDesc();

    @Query("SELECT q FROM Quiz q ORDER BY (CASE WHEN q.timeEnd IS NULL THEN q.timeStart ELSE q.timeEnd END) DESC")
    List<Quiz> findAllSortedByTime();
}
