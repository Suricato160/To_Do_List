package com.webtodolist.controller;

import com.webtodolist.entity.Task;
import com.webtodolist.entity.User;
import com.webtodolist.service.TaskService;
import com.webtodolist.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Default to user ID 1 for demonstration, in a real app this would come from the session
        return getTasksByUserId(1L, model);
    }

    @GetMapping("/user/{userId}")
    public String getTasksByUserId(@PathVariable Long userId, Model model) {
        // Retrieve the user
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
        } else {
            // Create a default user if not found to prevent template errors
            User defaultUser = new User();
            defaultUser.setNome("Guest");
            defaultUser.setCognome("User");
            defaultUser.setEmail("guest@example.com");
            model.addAttribute("user", defaultUser);
        }

        // Retrieve tasks for the user
        List<Task> tasks = taskService.findByUserId(userId);

        // Calculate percentages for task status
        long totalTasks = tasks.size();
        long completedTasks = tasks.stream().filter(task -> task.getStatus() == Task.TaskStatus.COMPLETED).count();
        long inProgressTasks = tasks.stream().filter(task -> task.getStatus() == Task.TaskStatus.WORK_IN_PROGRESS).count();
        long notStartedTasks = tasks.stream().filter(task -> task.getStatus() == Task.TaskStatus.PENDING).count();

        double completedPercentage = totalTasks > 0 ? (completedTasks * 100.0 / totalTasks) : 0;
        double inProgressPercentage = totalTasks > 0 ? (inProgressTasks * 100.0 / totalTasks) : 0;
        double notStartedPercentage = totalTasks > 0 ? (notStartedTasks * 100.0 / totalTasks) : 0;

        // Add data to the model
        model.addAttribute("tasks", tasks);
        model.addAttribute("userId", userId);
        model.addAttribute("completedPercentage", Math.round(completedPercentage));
        model.addAttribute("inProgressPercentage", Math.round(inProgressPercentage));
        model.addAttribute("notStartedPercentage", Math.round(notStartedPercentage));

        // Return the template name
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) Long userId, Model model) {
        // Default to user ID 1 if not provided
        Long userIdToUse = userId != null ? userId : 1L;
        return getTasksByUserId(userIdToUse, model);
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