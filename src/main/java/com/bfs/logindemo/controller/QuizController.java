package com.bfs.logindemo.controller;

import com.bfs.logindemo.entity.Quiz;
import com.bfs.logindemo.entity.QuizQuestion;
import com.bfs.logindemo.entity.User;
import com.bfs.logindemo.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/start")
    public String startQuiz(@RequestParam int categoryId,
                            @RequestParam String quizName,
                            HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/";

        int quizId = quizService.startQuiz(user.getUserId(), categoryId, quizName);
        return "redirect:/quiz/do?quizId=" + quizId;
    }

    @GetMapping("/do")
    public String doQuiz(@RequestParam int quizId,
                         HttpSession session,
                         Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/";
        }
        Quiz quiz = quizService.findQuizById(quizId);
        if (quiz == null || !quiz.getUser().getUserId().equals(user.getUserId())) {
            return "redirect:/home";
        }

        // load quizQuestions & each question + choices
        List<QuizQuestion> qqList = quizService.findQuizQuestions(quizId);
        model.addAttribute("quiz", quiz);
        model.addAttribute("quizQuestions", qqList);
        return "quiz";
    }

    @PostMapping("/submit")
    public String submitQuiz(
            @RequestParam int quizId,
            HttpSession session,
            HttpServletRequest request
    ) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/";
        }

        List<QuizQuestion> qqList = quizService.findQuizQuestions(quizId);

        for (QuizQuestion qq : qqList) {
            String paramName = "userChoice_" + qq.getQqId();
            String choiceIdStr = request.getParameter(paramName);
            if (choiceIdStr != null && !choiceIdStr.isEmpty()) {
                int choiceId = Integer.parseInt(choiceIdStr);
                // update the DB
                quizService.updateUserChoice(qq.getQqId(), choiceId);
            }
        }
        quizService.endQuiz(quizId);
        return "redirect:/quiz/result?quizId=" + quizId;
    }


    @GetMapping("/result")
    public String quizResult(@RequestParam int quizId,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/";

        Quiz quiz = quizService.findQuizById(quizId);
        if (quiz == null || !quiz.getUser().getUserId().equals(user.getUserId())) {
            return "redirect:/home";
        }

        List<QuizQuestion> qqList = quizService.findQuizQuestions(quizId);
        boolean pass = quizService.isPass(qqList);

        model.addAttribute("quiz", quiz);
        model.addAttribute("quizQuestions", qqList);
        model.addAttribute("passFail", pass ? "PASS" : "FAIL");
        return "result";
    }
}
