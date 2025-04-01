package com.webtodolist.controller;

import com.webtodolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/task-list")
    public String getTaskList(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "task-list";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Hello, World!";
    }
}
