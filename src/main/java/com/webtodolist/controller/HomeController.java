package com.webtodolist.controller;

import com.webtodolist.entity.Task;
import com.webtodolist.entity.User;
import com.webtodolist.service.TaskService;
import com.webtodolist.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public HomeController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
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
    
    @GetMapping({"/", "/index"})
    public String getDashboard(Model model) {
        List<Task> tasks = taskService.findAllTasks();
        model.addAttribute("tasks", tasks);
        return "index"; // Nome del template Thymeleaf
    }
}
