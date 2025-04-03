package com.webtodolist.controller;

import com.webtodolist.model.Project;
import com.webtodolist.model.Task;
import com.webtodolist.model.User;
import com.webtodolist.service.CustomUserDetails;
import com.webtodolist.service.ProjectService;
import com.webtodolist.service.TaskService;
import com.webtodolist.service.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/task-list")
    public String getTaskList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Retrieve the authenticated user using userService
        Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());

        List<Task> tasks = null;
        if (currentUser.isPresent()) {
            // Fetch all projects associated with the user using projectService
            List<Project> projects = projectService.findProjectsByUser(currentUser.get());

            // Retrieve all tasks for those projects
            tasks = projects.stream()
                            .flatMap(project -> project.getTasks().stream())
                            .peek(task -> task.setAssigner(userService.findById(task.getAssigner().getId()).orElse(null))) // Populate assigner details
                            .toList();
        }

        model.addAttribute("user", userDetails);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/tasks/new")
    public String newTask(@RequestParam(value = "projectId", required = false) Long projectId, Model model) {
        Task task = new Task();
        Project project = null;

        if (projectId != null) {
            project = projectService.findById(projectId);
            if (project == null) {
                return "redirect:/projects";
            }
            task.setProject(project);
        }

        model.addAttribute("task", task);
        model.addAttribute("project", project);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("projects", projectService.getAllProjects());
        return "taskNew";
    }

    @PostMapping("/tasks/create")
    public String createTask(@ModelAttribute("task") Task task, @RequestParam(value = "projectId", required = false) Long projectId, Model model) {
        try {
            // Ottieni CustomUserDetails dal contesto di sicurezza
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User currentUser = userDetails.getUser(); // Estrai l'entità User

            task.setAssigner(currentUser);
            task.setDataCreatedTask(java.time.LocalDateTime.now());

            if (projectId != null) {
                Project project = projectService.findById(projectId);
                if (project == null) {
                    throw new IllegalArgumentException("Progetto non trovato");
                }
                task.setProject(project);
            }

            taskService.saveTask(task);
            return "redirect:/projects/" + task.getProject().getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("task", task);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("projects", projectService.getAllProjects());
            model.addAttribute("project", projectId != null ? projectService.findById(projectId) : null);
            return "taskNew";
        }
    }
}