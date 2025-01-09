package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuizDao;
import com.bfs.logindemo.dao.QuizQuestionDao;
import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.dao.ChoiceDao; // <-- assume you have a ChoiceDao
import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.QuizQuestion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {
    private final QuizDao quizDao;
    private final QuizQuestionDao quizQuestionDao;
    private final QuestionDao questionDao;
    private final ChoiceDao choiceDao; // we need for isCorrectChoice

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
        Quiz quiz = new Quiz();
        quiz.setUserId(userId);
        quiz.setCategoryId(categoryId);
        quiz.setName(quizName);
        quiz.setTimeStart(LocalDateTime.now());
        quizDao.createQuiz(quiz);

        // get newly created quiz
        List<Quiz> userQuizzes = quizDao.findByUser(userId);
        Quiz createdQuiz = userQuizzes.get(userQuizzes.size() - 1);
        int quizId = createdQuiz.getQuizId();

        // pick questions
        List<Question> questionList = questionDao.findByCategory(categoryId);
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

    public List<Quiz> findQuizzesByUser(int userId) {
        return quizDao.findByUser(userId);
    }

    public List<Quiz> findQuizzesByCategory(int categoryId) {
        return quizDao.findByCategory(categoryId);
    }

    // new method to determine pass/fail
    public boolean isPass(List<QuizQuestion> quizQuestions) {
        int correctCount = 0;
        for (QuizQuestion qq : quizQuestions) {
            if (isCorrectChoice(qq)) {
                correctCount++;
            }
        }
        // pass if correctCount >= 3
        return (correctCount >= 3);
    }

    // helper to check if user_choice_id is the correct choice
    public boolean isCorrectChoice(QuizQuestion qq) {
        if (qq.getUserChoiceId() == null) {
            return false;
        }
        Choice c = choiceDao.findById(qq.getUserChoiceId());
        return (c != null && c.isCorrect());
    }
}
