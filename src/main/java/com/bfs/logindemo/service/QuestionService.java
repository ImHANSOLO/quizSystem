package com.bfs.logindemo.service;

import com.bfs.logindemo.entity.Category;
import com.bfs.logindemo.entity.Choice;
import com.bfs.logindemo.entity.Question;
import com.bfs.logindemo.entity.QuizQuestion;
import com.bfs.logindemo.repository.CategoryRepository;
import com.bfs.logindemo.repository.ChoiceRepository;
import com.bfs.logindemo.repository.QuestionRepository;
import com.bfs.logindemo.repository.QuizQuestionRepository;
import com.bfs.logindemo.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;
    private final ChoiceRepository choiceRepo;
    private final CategoryRepository categoryRepo;
    private final QuizQuestionRepository quizQuestionRepo;
    private final QuizRepository quizRepo;

    // So we can call recalcQuizResult(...) if an answer changes
    private final QuizService quizService;

    public QuestionService(QuestionRepository questionRepo,
                           ChoiceRepository choiceRepo,
                           CategoryRepository categoryRepo,
                           QuizQuestionRepository quizQuestionRepo,
                           QuizRepository quizRepo,
                           QuizService quizService) {
        this.questionRepo = questionRepo;
        this.choiceRepo = choiceRepo;
        this.categoryRepo = categoryRepo;
        this.quizQuestionRepo = quizQuestionRepo;
        this.quizRepo = quizRepo;
        this.quizService = quizService;
    }

    public List<Question> findAllQuestions() {
        return questionRepo.findAll();
    }

    public Question findById(int questionId) {
        return questionRepo.findById(questionId).orElse(null);
    }

    public void createQuestion(Question question) {
        questionRepo.save(question);
    }

    public void updateQuestion(Question question) {
        questionRepo.save(question);
    }

    public void deleteQuestion(int questionId) {
        questionRepo.deleteById(questionId);
    }

    public void setQuestionActive(int questionId, boolean active) {
        Question q = questionRepo.findById(questionId).orElse(null);
        if (q != null) {
            q.setActive(active);
            questionRepo.save(q);
        }
    }

    // create question from raw params
    public int createQuestion(int categoryId, String description, boolean active) {
        // 1) load category
        Category cat = categoryRepo.findById(categoryId).orElse(null);

        // 2) build question
        Question q = new Question();
        q.setDescription(description);
        q.setActive(active);
        q.setCategory(cat); // attach category

        // 3) save
        questionRepo.save(q);
        return q.getQuestionId();
    }

    // create a new choice
    public void createChoice(int questionId, String desc, boolean isCorrect) {
        Question question = questionRepo.findById(questionId).orElse(null);
        if (question == null) return;

        Choice c = new Choice();
        c.setQuestion(question);
        c.setDescription(desc);
        c.setCorrect(isCorrect);

        choiceRepo.save(c);
    }

    // update question from raw params
    public void updateQuestion(int questionId, int categoryId, String description, boolean active) {
        Question q = questionRepo.findById(questionId).orElse(null);
        if (q == null) return;

        Category cat = categoryRepo.findById(categoryId).orElse(null);
        q.setCategory(cat);
        q.setDescription(description);
        q.setActive(active);
        questionRepo.save(q);
    }

    // update an existing choice
    public void updateChoice(int choiceId, String desc, boolean newCorrect) {
        Choice c = choiceRepo.findById(choiceId).orElse(null);
        if (c == null) return;
        boolean oldCorrect = c.isCorrect();

        c.setDescription(desc);
        c.setCorrect(newCorrect);
        choiceRepo.save(c);

        // If correctness changed, recalc pass/fail for any quiz referencing this question
        if (oldCorrect != newCorrect) {
            Question question = c.getQuestion();
            if (question == null) return;

            int qid = question.getQuestionId();

            // find all quizQuestions referencing this question
            List<QuizQuestion> listQQ = quizQuestionRepo.findByQuestion_QuestionId(qid);

            for (QuizQuestion qq : listQQ) {
                int quizId = qq.getQuiz().getQuizId();
                // recalc
                quizService.recalcQuizResult(quizId);
            }
        }
    }

    public List<Choice> findChoicesByQuestion(int questionId) {
        Question q = questionRepo.findById(questionId).orElse(null);
        if (q == null) return List.of();
        // if your mapping is set up so that q.getChoices() fetches them
        return q.getChoices();
    }
}
