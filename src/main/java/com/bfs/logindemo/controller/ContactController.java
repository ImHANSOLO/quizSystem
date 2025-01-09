package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.Contact;
import com.bfs.logindemo.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String showContactPage() {
        return "contact";
    }

    @PostMapping("/submit")
    public String submitContact(@RequestParam String subject,
                                @RequestParam String message,
                                @RequestParam String email,
                                Model model) {
        Contact c = new Contact();
        c.setSubject(subject);
        c.setMessage(message);
        c.setEmail(email);

        contactService.submitContact(c);
        model.addAttribute("msg", "Thanks for contacting us!");
        return "contact";
    }
}