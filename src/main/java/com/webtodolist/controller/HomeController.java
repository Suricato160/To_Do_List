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
    
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
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
