package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.QuizQuestion;
import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // quiz starts
    @PostMapping("/start")
    public String startQuiz(@RequestParam int categoryId,
                            @RequestParam String quizName,
                            HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/";

        int quizId = quizService.startQuiz(user.getUserId(), categoryId, quizName);
        return "redirect:/quiz/do?quizId=" + quizId;
    }

    // question page
    @GetMapping("/do")
    public String doQuiz(@RequestParam int quizId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/";

        Quiz quiz = quizService.findQuizById(quizId);
        if (quiz == null || quiz.getUserId() != user.getUserId()) {
            return "redirect:/home";
        }
        List<QuizQuestion> qqList = quizService.findQuizQuestions(quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("quizQuestions", qqList);
        return "quiz";
    }

    // quiz ends
    @PostMapping("/submit")
    public String submitQuiz(@RequestParam int quizId) {
        // calculate the score based on the options selected by the user
        quizService.endQuiz(quizId);
        // redirect to result page
        return "redirect:/quiz/result?quizId=" + quizId;
    }

    // result page
    @GetMapping("/result")
    public String quizResult(@RequestParam int quizId,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/";

        Quiz quiz = quizService.findQuizById(quizId);
        if (quiz == null || quiz.getUserId() != user.getUserId()) {
            return "redirect:/home";
        }

        // find quizQuestions
        List<QuizQuestion> qqList = quizService.findQuizQuestions(quizId);

        // compute pass/fail
        // we do a new method in quizService
        boolean pass = quizService.isPass(qqList);

        model.addAttribute("quiz", quiz);
        model.addAttribute("quizQuestions", qqList);
        model.addAttribute("passFail", pass ? "PASS" : "FAIL");

        return "result";
    }
}
