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
        Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());
    
        List<Task> tasks = null;
        if (currentUser.isPresent()) {
            List<Project> projects = projectService.findProjectsByUser(currentUser.get());
            tasks = projects.stream()
                    .flatMap(project -> project.getTasks().stream())
                    .toList();
            model.addAttribute("user", currentUser.get());
        } else {
            model.addAttribute("user", null);
        }
    
        model.addAttribute("tasks", tasks);
        model.addAttribute("userService", userService);
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

    @GetMapping("/tasks/edit")
public String editTask(@RequestParam("taskId") Long taskId, Model model) {
    Task task = taskService.findTaskById(taskId);
    if (task == null) {
        return "redirect:/task-list"; // Task non trovata, torna alla lista
    }

    model.addAttribute("task", task);
    model.addAttribute("project", task.getProject()); // Progetto associato, se presente
    model.addAttribute("users", userService.getAllUsers());
    model.addAttribute("projects", projectService.getAllProjects());
    model.addAttribute("userService", userService); // Per il nome completo nella sidebar
    Optional<User> currentUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    model.addAttribute("user", currentUser.orElse(null)); // Utente autenticato
    return "taskEdit";
}

@PostMapping("/tasks/update")
public String updateTask(@ModelAttribute("task") Task task, 
                         @RequestParam(value = "projectId", required = false) Long projectId, 
                         Model model) {
    try {
        // Recupera la task esistente
        Task existingTask = taskService.findTaskById(task.getId());
        if (existingTask == null) {
            throw new IllegalArgumentException("Task non trovata");
        }

        // Aggiorna i campi
        existingTask.setTitolo(task.getTitolo());
        existingTask.setDescrizione(task.getDescrizione());
        existingTask.setDataDeadline(task.getDataDeadline());
        existingTask.setCategoria(task.getCategoria());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());

        // Aggiorna l'utente assegnato
        Optional<User> assignedUser = userService.findById(task.getUser().getId());
        if (assignedUser.isPresent()) {
            existingTask.setUser(assignedUser.get());
        } else {
            throw new IllegalArgumentException("Utente non trovato");
        }

        // Aggiorna il progetto, se specificato
        if (projectId != null) {
            Project project = projectService.findById(projectId);
            if (project == null) {
                throw new IllegalArgumentException("Progetto non trovato");
            }
            existingTask.setProject(project);
        }

        taskService.saveTask(existingTask);
        return "redirect:/task-list"; // O torna al dettaglio del progetto: /projects/{projectId}
    } catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage());
        model.addAttribute("task", task);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("project", projectId != null ? projectService.findById(projectId) : null);
        model.addAttribute("userService", userService);
        Optional<User> currentUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", currentUser.orElse(null));
        return "taskEdit";
    }
}
}