package com.bfs.logindemo.repository;

import com.bfs.logindemo.entity.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Integer> {
    List<Choice> findByQuestion_QuestionId(int questionId);
}
