package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionDao questionDao;

    @Autowired
    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public Question getQuestion() {
        return questionDao.getQuestion();
    }

    public String checkAnswer(Choice selectedChoice) {
        Question question = questionDao.getQuestion();
        Choice correctChoice = question.getChoices().get(question.getCorrectChoiceId() - 1);
        return selectedChoice.equals(correctChoice) ? "Correct!" : "Incorrect";
    }

    public Optional<Choice> getChoiceById(Integer selectedChoiceId) {
        return questionDao
                .getQuestion()
                .getChoices()
                .stream()
                .filter(choice -> choice.getId() == selectedChoiceId)
                .findFirst();
    }
}
