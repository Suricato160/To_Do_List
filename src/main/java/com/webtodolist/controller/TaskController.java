package com.webtodolist.controller;

import com.webtodolist.repository.TaskRepository;
import com.webtodolist.service.UserService;
import com.webtodolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.webtodolist.entity.Task;
import com.webtodolist.entity.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;

    @GetMapping("/tasks")
    public String showAllTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks != null ? tasks : Collections.emptyList());
        return "tasks"; // Points to tasks.html template
    }
    
    @GetMapping("/tasks/user/{userId}")
    public String showUserTasks(@PathVariable Long userId, Model model) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Task> tasks = taskService.findByUserId(user.getId());
            model.addAttribute("tasks", tasks);
            model.addAttribute("user", user);
            return "user-tasks"; // Points to user-tasks.html template
        } else {
            return "redirect:/tasks";
        }
    }

    @GetMapping("/tasks/create")
    public String showCreateForm(@RequestParam Long userId, Model model) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            Task task = new Task();
            task.setUser(userOptional.get());
            model.addAttribute("task", task);
            model.addAttribute("userId", userId);
            model.addAttribute("statuses", Task.TaskStatus.values());
            model.addAttribute("isEdit", false);
            return "task-form";
        } else {
            return "redirect:/tasks";
        }
    }

    @PostMapping("/tasks/save")
    public String saveTask(@ModelAttribute Task task, @RequestParam Long userId, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            task.setUser(user);
            if (task.getStatus() == null) {
                task.setStatus(Task.TaskStatus.PENDING);
            }
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("successMessage", "Task saved successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
        }
        return "redirect:/tasks/user/" + userId;
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            model.addAttribute("task", task);
            model.addAttribute("userId", task.getUser().getId());
            model.addAttribute("statuses", Task.TaskStatus.values());
            model.addAttribute("isEdit", true);
            return "task-form";
        } else {
            return "redirect:/tasks";
        }
    }

    @GetMapping("/tasks/delete/{id}")
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
            return "redirect:/tasks";
        }
    }

    @GetMapping("/tasks/updateStatus/{id}")
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
            return "redirect:/tasks";
        }
    }
}