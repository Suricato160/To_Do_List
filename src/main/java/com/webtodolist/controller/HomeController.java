package com.webtodolist.controller;

import com.webtodolist.entity.User;
import com.webtodolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/projects")
    public String projects(Model model) {
        // Default to user ID 1 for demonstration
        Optional<User> userOptional = userService.findById(1L);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        }
        return "projects";
    }
}
