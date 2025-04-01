package com.webtodolist.controller;

import com.webtodolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
=======
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Collections;
>>>>>>> abccd9115538223eb2da639e009fff9e1e76df67

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

    @GetMapping("/create")
    public String showCreateForm(@RequestParam Long userId, Model model) {
        Task task = new Task();
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            task.setUser(userOptional.get());
            model.addAttribute("task", task);
            model.addAttribute("userId", userId);
            return "task-form";
        } else {
            return "redirect:/tasks/";
        }
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute Task task, @RequestParam Long userId, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            task.setUser(userOptional.get());
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("successMessage", "Task saved successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
        }
        return "redirect:/tasks/user/" + userId;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            model.addAttribute("task", task);
            model.addAttribute("userId", task.getUser().getId());
            return "task-form";
        } else {
            return "redirect:/tasks/";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            Long userId = task.getUser().getId();
            taskService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully!");
            return "redirect:/tasks/user/" + userId;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found!");
            return "redirect:/tasks/";
        }
    }

    @GetMapping("/updateStatus/{id}")
    public String updateTaskStatus(@PathVariable Long id, @RequestParam Task.TaskStatus status, RedirectAttributes redirectAttributes) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            Long userId = task.getUser().getId();
            task.setStatus(status);
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("successMessage", "Task status updated successfully!");
            return "redirect:/tasks/user/" + userId;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Task not found!");
            return "redirect:/tasks/";
        }
    }
}