package com.bfs.logindemo.repository;

import com.bfs.logindemo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Replaces old QuestionDao
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // For "only active" questions by category
    List<Question> findByCategory_CategoryIdAndActiveTrue(int categoryId);

    // If also want something like findAllActive:
    // List<Question> findByActiveTrue();
}
