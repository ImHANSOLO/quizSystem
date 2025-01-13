package com.bfs.logindemo.service;

import com.bfs.logindemo.entity.Category;
import com.bfs.logindemo.entity.Choice;
import com.bfs.logindemo.entity.Question;
import com.bfs.logindemo.entity.Quiz;
import com.bfs.logindemo.entity.QuizQuestion;
import com.bfs.logindemo.entity.User;
import com.bfs.logindemo.repository.CategoryRepository;
import com.bfs.logindemo.repository.ChoiceRepository;
import com.bfs.logindemo.repository.QuestionRepository;
import com.bfs.logindemo.repository.QuizQuestionRepository;
import com.bfs.logindemo.repository.QuizRepository;
import com.bfs.logindemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public QuizService(QuizRepository quizRepository,
                       QuizQuestionRepository quizQuestionRepository,
                       QuestionRepository questionRepository,
                       ChoiceRepository choiceRepository,
                       UserRepository userRepository,
                       CategoryRepository categoryRepository) {
        this.quizRepository = quizRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.questionRepository = questionRepository;
        this.choiceRepository = choiceRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new Quiz record in DB.
     * Must not pass an invalid userId or categoryId (they can't be null).
     */
    public int startQuiz(int userId, int categoryId, String quizName) {
        // 1) Load User and Category
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        Category cat = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));

        // 2) Create a new Quiz entity with user, category
        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setCategory(cat);  // must not be null if DB column is NOT NULL
        quiz.setName(quizName);
        quiz.setTimeStart(LocalDateTime.now());

        // 3) Save the quiz so we get an auto-generated quizId
        quizRepository.save(quiz);
        int quizId = quiz.getQuizId();

        // 4) Now pick up to 5 active questions from that category
        List<Question> questionList = questionRepository
                .findByCategory_CategoryIdAndActiveTrue(categoryId);

        for (int i = 0; i < Math.min(5, questionList.size()); i++) {
            Question q = questionList.get(i);
            QuizQuestion qq = new QuizQuestion();
            qq.setQuiz(quiz);
            qq.setQuestion(q);
            // userChoice is null by default
            quizQuestionRepository.save(qq);
        }

        return quizId;
    }

    /** Mark end time of a quiz. */
    public void endQuiz(int quizId) {
        Optional<Quiz> opt = quizRepository.findById(quizId);
        if (opt.isPresent()) {
            Quiz quiz = opt.get();
            quiz.setTimeEnd(LocalDateTime.now());
            quizRepository.save(quiz);
        }
    }

    /** Find quiz by ID. */
    public Quiz findQuizById(int quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

    /**
     * Load quizQuestions for a given quizId
     * and fill in question + choices if needed.
     */
    public List<QuizQuestion> findQuizQuestions(int quizId) {
        List<QuizQuestion> qqList = quizQuestionRepository.findByQuiz_QuizId(quizId);
        // If your mapping is lazy, you might load question/choices explicitly,
        // but typically JPA will load them if your transaction is open
        return qqList;
    }

    /**
     * When user chooses an option in the quiz page.
     * userChoice is stored in quizQuestion.
     */
    public void updateUserChoice(int qqId, Integer choiceId) {
        QuizQuestion qq = quizQuestionRepository.findById(qqId).orElse(null);
        if (qq == null) return;
        if (choiceId == null) {
            qq.setUserChoice(null);
        } else {
            Choice c = choiceRepository.findById(choiceId).orElse(null);
            qq.setUserChoice(c);
        }
        quizQuestionRepository.save(qq);
    }

    /** Return true if user got >=3 correct answers out of 5. */
    public boolean isPass(List<QuizQuestion> qqList) {
        int correctCount = 0;
        for (QuizQuestion qq : qqList) {
            if (qq.getUserChoice() != null && qq.getUserChoice().isCorrect()) {
                correctCount++;
            }
        }
        return (correctCount >= 3);
    }

    /**
     * Re-calculate a quiz’s pass/fail result if a question's correct answers changed.
     * (Optional method to fix references in QuestionService.)
     */
    public void recalcQuizResult(int quizId) {
        // If you want to store pass/fail in DB, do that here. For now, no-ops.
        Quiz quiz = findQuizById(quizId);
        if (quiz == null) return;
        // Possibly do: boolean pass = isPass(findQuizQuestions(quizId));
        // If you want to store pass in quiz, then quiz.setPassed(pass).
        // quizRepository.save(quiz);
    }

    // various findAll or filters

    public List<Quiz> findByUser(int userId) {
        // uses a custom method in QuizRepository
        return quizRepository.findByUser_UserIdOrderByTimeEndDesc(userId);
    }

    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<Quiz> findQuizzesByUser(int userId) {
        return quizRepository.findByUser_UserIdOrderByTimeEndDesc(userId);
    }

    public List<Quiz> findQuizzesByCategory(int categoryId) {
        return quizRepository.findByCategory_CategoryIdOrderByTimeEndDesc(categoryId);
    }

    /** Sort by time_end desc. If timeEnd is null,
     *  some queries handle it separately.
     *  This version uses a custom finder in repository.
     */
    public List<Quiz> findAllQuizzesSortedDesc() {
        // e.g. if you have findAllByOrderByTimeEndDesc in QuizRepository
        return quizRepository.findAllByOrderByTimeEndDesc();
    }
}
