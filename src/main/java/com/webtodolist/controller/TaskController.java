package com.webtodolist.controller;

import com.webtodolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Collections;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public String showTaskList(Model model) {
        // Always add tasks attribute, default to empty list if null
        model.addAttribute("tasks", taskRepository.findAll() != null ? 
            taskRepository.findAll() : Collections.emptyList());
        return "index"; // Refers to index.html template
    }
}