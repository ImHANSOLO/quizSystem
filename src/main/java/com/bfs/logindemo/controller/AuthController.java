package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String rootRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        System.out.println("show login page");
        return "login";
    }

//    @GetMapping("/login/data")
//    @ResponseBody
//    public String showLoginPage2() {
//        System.out.println("show login page2");
//        return "login";
//    }


    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          HttpSession session,
                          Model model) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("loggedUser", user);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password or user not active");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        System.out.println("Im here");
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String firstname,
                             @RequestParam String lastname,
                             Model model) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setActive(true);
        user.setAdmin(false);

        boolean success = userService.registerUser(user);
        if (!success) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }

        return "redirect:/login";
    }
}