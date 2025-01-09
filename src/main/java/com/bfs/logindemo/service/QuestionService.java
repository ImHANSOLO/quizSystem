package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.domain.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionDao questionDao;

    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public List<Question> findAllQuestions() {
        return questionDao.findAll();
    }

    public List<Question> findByCategory(int categoryId) {
        return questionDao.findByCategory(categoryId);
    }

    public Question findById(int questionId) {
        return questionDao.findById(questionId);
    }

    public void createQuestion(Question question) {
        questionDao.save(question);
    }

    public void updateQuestion(Question question) {
        questionDao.update(question);
    }

    public void deleteQuestion(int questionId) {
        questionDao.delete(questionId);
    }

    // Activate or deactivate a topic
    public void setQuestionActive(int questionId, boolean active) {
        questionDao.setActive(questionId, active);
    }
}