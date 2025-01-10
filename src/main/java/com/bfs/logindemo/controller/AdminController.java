package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.Contact;
import com.bfs.logindemo.domain.*;
import com.bfs.logindemo.service.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;
    private final ContactService contactService;
    private final CategoryService categoryService;
    private final QuizService quizService;

    private final QuestionService questionService;

    public AdminController(JdbcTemplate jdbcTemplate, UserService userService,
                           ContactService contactService,
                           CategoryService categoryService,
                           QuizService quizService,
                           QuestionService questionService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
        this.contactService = contactService;
        this.categoryService = categoryService;
        this.quizService = quizService;
        this.questionService = questionService;
    }

    // admin main page
    @GetMapping("/home")
    public String adminHome(HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/"; // If not logged in or not an administrator, return to log-in page
        }
        return "admin/adminHome";
    }

    // User Management Page
    @GetMapping("/userManagement")
    public String userManagement(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        // Get all users
        List<User> allUsers = userService.findAllUsers();
        model.addAttribute("users", allUsers);
        return "admin/userManagement";
    }

    // Toggle user status (activate/deactivate)
    @PostMapping("/toggleUserStatus")
    public String toggleUserStatus(@RequestParam int userId,
                                   @RequestParam boolean isActive,
                                   HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        userService.changeUserStatus(userId, isActive);
        return "redirect:/admin/userManagement";
    }

    // Contact information management
    @GetMapping("/contactManagement")
    public String contactManagement(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        List<Contact> contacts = contactService.listAllContacts();
        model.addAttribute("contacts", contacts);
        return "admin/contactManagement";
    }

    @GetMapping("/questionManagement")
    public String questionManagement(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        // Load all categories
        model.addAttribute("categories", categoryService.findAll());

        // Load all questions
        model.addAttribute("questions", questionService.findAllQuestions());

        return "admin/questionManagement";
    }

    @PostMapping("/addQuestion")
    public String addQuestion(@RequestParam int categoryId,
                              @RequestParam String description,
                              @RequestParam(required = false, defaultValue = "true") boolean isActive,
                              HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        Question q = new Question();
        q.setCategoryId(categoryId);
        q.setDescription(description);
        q.setActive(isActive);
        questionService.createQuestion(q);

        return "redirect:/admin/questionManagement";
    }

    // update questions
    @PostMapping("/updateQuestion")
    public String updateQuestion(@RequestParam int questionId,
                                 @RequestParam int categoryId,
                                 @RequestParam String description,
                                 @RequestParam(required = false, defaultValue = "true") boolean isActive,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        Question existing = questionService.findById(questionId);
        if (existing != null) {
            existing.setCategoryId(categoryId);
            existing.setDescription(description);
            existing.setActive(isActive);
            questionService.updateQuestion(existing);
        }
        return "redirect:/admin/questionManagement";
    }

    // delete questions
    @PostMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam int questionId,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        questionService.deleteQuestion(questionId);
        return "redirect:/admin/questionManagement";
    }

    // Activate/deactivate topic
    @PostMapping("/toggleQuestionActive")
    public String toggleQuestionActive(@RequestParam int questionId,
                                       @RequestParam boolean isActive,
                                       HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        questionService.setQuestionActive(questionId, isActive);
        return "redirect:/admin/questionManagement";
    }


    // Test result management
    @GetMapping("/quizManagement")
    public String quizManagement(HttpSession session,
                                 @RequestParam(required = false) Integer filterUserId,
                                 @RequestParam(required = false) Integer filterCategoryId,
                                 Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/"; // only admin can access
        }

        List<Quiz> results;
        if (filterUserId != null) {
            // if we have user filter
            results = quizService.findQuizzesByUser(filterUserId);
        } else if (filterCategoryId != null) {
            // if we have category filter
            results = quizService.findQuizzesByCategory(filterCategoryId);
        } else {
            // default => sorted desc
            results = quizService.findAllQuizzesSortedDesc();
        }

        model.addAttribute("allQuizzes", results);
        // also provide categories for filter
        model.addAttribute("categories", categoryService.findAll());
        return "admin/quizManagement";
    }


    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String name, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        categoryService.createCategory(name);
        return "redirect:/admin/questionManagement";
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@RequestParam int categoryId,
                                 @RequestParam String newName,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        categoryService.updateCategory(categoryId, newName);
        return "redirect:/admin/questionManagement";
    }

    @PostMapping("/deleteCategory")
    public String deleteCategory(@RequestParam int categoryId,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        categoryService.deleteCategory(categoryId);
        return "redirect:/admin/questionManagement";
    }

    @GetMapping("/quizDetail")
    public String quizDetail(@RequestParam int quizId,
                             HttpSession session,
                             Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }

        // find quiz
        Quiz quiz = quizService.findQuizById(quizId);
        if (quiz == null) {
            return "redirect:/admin/quizManagement";
        }
        // load QuizQuestions
        List<QuizQuestion> qqList = quizService.findQuizQuestions(quizId);
        // check pass/fail
        boolean pass = quizService.isPass(qqList);
        model.addAttribute("quiz", quiz);
        model.addAttribute("quizQuestions", qqList);
        model.addAttribute("passFail", pass ? "PASS" : "FAIL");
        return "admin/quizDetail";
    }


}