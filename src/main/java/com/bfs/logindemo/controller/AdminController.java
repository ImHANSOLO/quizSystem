package com.bfs.logindemo.controller;

import com.bfs.logindemo.entity.Contact;
import com.bfs.logindemo.entity.Question;
import com.bfs.logindemo.entity.Quiz;
import com.bfs.logindemo.entity.QuizQuestion;
import com.bfs.logindemo.entity.User;
import com.bfs.logindemo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ContactService contactService;
    private final CategoryService categoryService;
    private final QuizService quizService;
    private final QuestionService questionService;

    // Remove JdbcTemplate from constructor
    public AdminController(UserService userService,
                           ContactService contactService,
                           CategoryService categoryService,
                           QuizService quizService,
                           QuestionService questionService) {
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
            return "redirect:/";
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

    // question management
    @GetMapping("/questionManagement")
    public String questionManagement(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        // load all categories
        model.addAttribute("categories", categoryService.findAll());
        // load all questions
        model.addAttribute("questions", questionService.findAllQuestions());
        return "admin/questionManagement";
    }

    // Add question (small form)
    @PostMapping("/addQuestion")
    public String addQuestion(@RequestParam int categoryId,
                              @RequestParam String description,
                              @RequestParam(defaultValue = "true") boolean isActive,
                              HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }

        // Instead of q.setCategoryId(categoryId), we do:
        Question q = new Question();
        q.setDescription(description);
        q.setActive(isActive);

        // fetch the Category
        // then q.setCategory(category)
        q.setCategory(categoryService.findById(categoryId));

        questionService.createQuestion(q);
        return "redirect:/admin/questionManagement";
    }

    // show add page
    @GetMapping("/questionAdd")
    public String showAddQuestionPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }
        model.addAttribute("categories", categoryService.findAll());
        return "admin/questionAdd";
    }

    // 2) big form for questionAdd
    @PostMapping("/questionAdd")
    public String doAddQuestion(@RequestParam int categoryId,
                                @RequestParam String description,
                                @RequestParam(defaultValue="false") boolean active,
                                @RequestParam List<String> choiceDesc,
                                @RequestParam int correctIndex,
                                HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }

        // create question from raw params
        int questionId = questionService.createQuestion(categoryId, description, active);

        // create choices
        for (int i = 0; i < choiceDesc.size(); i++) {
            boolean isCorrect = (i == correctIndex);
            questionService.createChoice(questionId, choiceDesc.get(i), isCorrect);
        }
        return "redirect:/admin/questionManagement";
    }

    // show edit
    @GetMapping("/questionEdit")
    public String showEditQuestionPage(@RequestParam int questionId,
                                       HttpSession session,
                                       Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }

        Question q = questionService.findById(questionId);
        if (q == null) {
            return "redirect:/admin/questionManagement";
        }

        model.addAttribute("question", q);
        model.addAttribute("choices", questionService.findChoicesByQuestion(questionId));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/questionEdit";
    }

    // handle edit
    @PostMapping("/questionEdit")
    public String doEditQuestion(@RequestParam int questionId,
                                 @RequestParam int categoryId,
                                 @RequestParam String description,
                                 @RequestParam(defaultValue="false") boolean active,
                                 @RequestParam List<Integer> choiceIds,
                                 @RequestParam List<String> choiceDesc,
                                 @RequestParam int correctIndex,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }

        questionService.updateQuestion(questionId, categoryId, description, active);

        // update each choice
        for (int i = 0; i < choiceIds.size(); i++) {
            int chId = choiceIds.get(i);
            String desc = choiceDesc.get(i);
            boolean isCorrect = (i == correctIndex);
            questionService.updateChoice(chId, desc, isCorrect);
        }
        return "redirect:/admin/questionManagement";
    }

    // update question (small form)
    @PostMapping("/updateQuestion")
    public String updateQuestion(@RequestParam int questionId,
                                 @RequestParam int categoryId,
                                 @RequestParam String description,
                                 @RequestParam(defaultValue = "true") boolean isActive,
                                 HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }

        // load existing question
        Question existing = questionService.findById(questionId);
        if (existing != null) {
            // set new props
            // old: existing.setCategoryId(categoryId)
            existing.setCategory(categoryService.findById(categoryId));

            existing.setDescription(description);
            existing.setActive(isActive);
            questionService.updateQuestion(existing);
        }
        return "redirect:/admin/questionManagement";
    }

    // delete question
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

    // activate/deactivate question
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

    // quiz management
    @GetMapping("/quizManagement")
    public String quizManagement(HttpSession session,
                                 @RequestParam(required = false) Integer filterUserId,
                                 @RequestParam(required = false) Integer filterCategoryId,
                                 Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/";
        }

        List<Quiz> results;
        if (filterUserId != null) {
            results = quizService.findQuizzesByUser(filterUserId);
        } else if (filterCategoryId != null) {
            results = quizService.findQuizzesByCategory(filterCategoryId);
        } else {
            results = quizService.findAllQuizzesSortedDesc();
        }

        model.addAttribute("allQuizzes", results);
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

        Quiz quiz = quizService.findQuizById(quizId);
        if (quiz == null) {
            return "redirect:/admin/quizManagement";
        }

        List<QuizQuestion> qqList = quizService.findQuizQuestions(quizId);
        boolean pass = quizService.isPass(qqList);

        model.addAttribute("quiz", quiz);
        model.addAttribute("quizQuestions", qqList);
        model.addAttribute("passFail", pass ? "PASS" : "FAIL");
        return "admin/quizDetail";
    }
}
