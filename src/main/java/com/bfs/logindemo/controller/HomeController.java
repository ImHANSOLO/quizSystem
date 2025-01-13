package com.bfs.logindemo.controller;

import com.bfs.logindemo.entity.User;
import com.bfs.logindemo.service.CategoryService;
import com.bfs.logindemo.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final QuizService quizService;

    public HomeController(CategoryService categoryService, QuizService quizService) {
        this.categoryService = categoryService;
        this.quizService = quizService;
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return "redirect:/"; // redirect to the login page if not logged in
        }

        // Loading the categories
        model.addAttribute("categories", categoryService.findAll());

        // Load the most recent quiz for this user
        model.addAttribute("userQuizzes", quizService.findByUser(user.getUserId()));
        return "home";
    }
}