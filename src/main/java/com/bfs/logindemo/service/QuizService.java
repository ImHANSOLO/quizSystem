package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.*;
import com.bfs.logindemo.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {
    private final QuizDao quizDao;
    private final QuizQuestionDao quizQuestionDao;
    private final QuestionDao questionDao;
    private final ChoiceDao choiceDao;

    public QuizService(QuizDao quizDao,
                       QuizQuestionDao quizQuestionDao,
                       QuestionDao questionDao,
                       ChoiceDao choiceDao) {
        this.quizDao = quizDao;
        this.quizQuestionDao = quizQuestionDao;
        this.questionDao = questionDao;
        this.choiceDao = choiceDao;
    }

    public int startQuiz(int userId, int categoryId, String quizName) {
        // create a new quiz record
        Quiz quiz = new Quiz();
        quiz.setUserId(userId);
        quiz.setCategoryId(categoryId);
        quiz.setName(quizName);
        quiz.setTimeStart(LocalDateTime.now());
        quizDao.createQuiz(quiz);

        // find newly created quiz
        List<Quiz> userQuizzes = quizDao.findByUser(userId);
        Quiz createdQuiz = userQuizzes.get(userQuizzes.size() - 1);
        int quizId = createdQuiz.getQuizId();

        // pick questions from that category
        List<Question> questionList = questionDao.findByCategory(categoryId);
        // only up to 5
        for (int i = 0; i < Math.min(5, questionList.size()); i++) {
            Question q = questionList.get(i);
            QuizQuestion qq = new QuizQuestion();
            qq.setQuizId(quizId);
            qq.setQuestionId(q.getQuestionId());
            quizQuestionDao.insert(qq);
        }

        return quizId;
    }

    public void endQuiz(int quizId) {
        quizDao.updateEndTime(quizId, LocalDateTime.now());
    }

    public Quiz findQuizById(int quizId) {
        return quizDao.findById(quizId);
    }

    // load QuizQuestion，and load Question + choices for every QQ
    public List<QuizQuestion> findQuizQuestions(int quizId) {
        List<QuizQuestion> qqList = quizQuestionDao.findByQuiz(quizId);
        for (QuizQuestion qq : qqList) {
            // load question
            Question question = questionDao.findById(qq.getQuestionId());
            if (question != null) {
                // load choice list
                List<Choice> choiceList = choiceDao.findByQuestion(question.getQuestionId());
                question.setChoices(choiceList);
            }
            qq.setQuestion(question);
        }
        return qqList;
    }

    // update user_choice_id while submitting
    public void updateUserChoice(int qqId, Integer choiceId) {
        quizQuestionDao.updateUserChoice(qqId, choiceId);
    }

    // pass/fail checks
    public boolean isPass(List<QuizQuestion> qqList) {
        int correctCount = 0;
        for (QuizQuestion qq : qqList) {
            if (isCorrectChoice(qq)) {
                correctCount++;
            }
        }
        return (correctCount >= 3);
    }
    private boolean isCorrectChoice(QuizQuestion qq) {
        if (qq.getUserChoiceId() == null) return false;
        Choice c = choiceDao.findById(qq.getUserChoiceId());
        return (c != null && c.isCorrect());
    }

    public List<Quiz> findByUser(int userId) {
        return quizDao.findByUser(userId);
    }

    public List<Quiz> findAllQuizzes() {
        return quizDao.findAll();
    }

    public List<Quiz> findQuizzesByUser(int userId) {
        return quizDao.findByUser(userId);
    }

    public List<Quiz> findQuizzesByCategory(int categoryId) {
        return quizDao.findByCategory(categoryId);
    }
}
