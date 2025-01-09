package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuizDao;
import com.bfs.logindemo.dao.QuizQuestionDao;
import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.QuizQuestion;
import com.bfs.logindemo.domain.Question;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {
    private final QuizDao quizDao;
    private final QuizQuestionDao quizQuestionDao;
    private final QuestionDao questionDao;

    public QuizService(QuizDao quizDao, QuizQuestionDao quizQuestionDao, QuestionDao questionDao) {
        this.quizDao = quizDao;
        this.quizQuestionDao = quizQuestionDao;
        this.questionDao = questionDao;
    }

    public int startQuiz(int userId, int categoryId, String quizName) {
        // create a quiz
        Quiz quiz = new Quiz();
        quiz.setUserId(userId);
        quiz.setCategoryId(categoryId);
        quiz.setName(quizName);
        quiz.setTimeStart(LocalDateTime.now());
        quizDao.createQuiz(quiz);

        // get a new quiz_id
        List<Quiz> userQuizzes = quizDao.findByUser(userId);
        Quiz createdQuiz = userQuizzes.get(userQuizzes.size() - 1);
        int quizId = createdQuiz.getQuizId();

        // Random/or direct access to the topic
        List<Question> questionList = questionDao.findByCategory(categoryId);
        // Take the first 5 questions or randomly
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

    public List<QuizQuestion> findQuizQuestions(int quizId) {
        return quizQuestionDao.findByQuiz(quizId);
    }

    public List<Quiz> findAllQuizzes() {
        return quizDao.findAll();
    }

    public List<Quiz> findByUser(int userId) {
        return quizDao.findByUser(userId);
    }

    // Filter by User ID
    public List<Quiz> findQuizzesByUser(int userId) {
        return quizDao.findByUser(userId);
    }

    // Filter by Category ID
    public List<Quiz> findQuizzesByCategory(int categoryId) {
        return quizDao.findByCategory(categoryId);
    }

}