package com.webtodolist.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webtodolist.model.Task;
import com.webtodolist.model.Task.TaskStatus;
import com.webtodolist.model.User;
import com.webtodolist.model.Project;
import com.webtodolist.service.ProjectService;
import com.webtodolist.service.TaskService;
import com.webtodolist.service.UserService;

@Controller
public class DashboardController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping({ "/", "/index" })
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());
        User user = currentUser.orElse(null);

        List<Project> projects = Collections.emptyList();
        List<Task> allTasks = Collections.emptyList();

        if (user != null) {
            projects = projectService.findProjectsByUser(user);
            allTasks = projects.stream()
                    .flatMap(project -> project.getTasks().stream())
                    .toList();
            model.addAttribute("allTasks", allTasks);
        }

        model.addAttribute("user", user); // Passa l'entità User invece di UserDetails
        model.addAttribute("userService", userService); // Aggiungi userService al modello
        model.addAttribute("projects", projects);

        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        List<Task> todayTasks = taskService.findTasksByDeadlineBetween(todayStart, todayEnd);
        model.addAttribute("todayTasks", todayTasks);

        List<Task> completedTasks = allTasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                .toList();
        model.addAttribute("completedTasks", completedTasks);

        int totalTasks = allTasks.size();
        if (totalTasks > 0) {
            int completedCount = completedTasks.size();
            int inProgressCount = (int) allTasks.stream()
                    .filter(t -> t.getStatus() == TaskStatus.WORK_IN_PROGRESS)
                    .count();
            int pendingCount = (int) allTasks.stream()
                    .filter(t -> t.getStatus() == TaskStatus.PENDING || t.getStatus() == TaskStatus.STARTED)
                    .count();

            int completedPercentage = (completedCount * 100) / totalTasks;
            int inProgressPercentage = (inProgressCount * 100) / totalTasks;
            int pendingPercentage = (pendingCount * 100) / totalTasks;

            model.addAttribute("completedPercentage", completedPercentage);
            model.addAttribute("inProgressPercentage", inProgressPercentage);
            model.addAttribute("pendingPercentage", pendingPercentage);
        } else {
            model.addAttribute("completedPercentage", 0);
            model.addAttribute("inProgressPercentage", 0);
            model.addAttribute("pendingPercentage", 0);
        }

        return "index";
    }
}