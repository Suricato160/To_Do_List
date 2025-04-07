package com.webtodolist.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Task> todayTasks = Collections.emptyList();
        List<Task> completedTasks = Collections.emptyList();

        if (user != null) {
            projects = projectService.findProjectsByUser(user);
            allTasks = projects.stream()
                              .flatMap(project -> project.getTasks().stream())
                              .toList();

            // Definisci i limiti temporali
            LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
            LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
            LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2); // Limite per task completate

            // Filtra le task di oggi (non completate) in base al ruolo
            if ("PROJECT_MANAGER".equals(user.getRole())) { // Adatta se il ruolo è un enum
                todayTasks = allTasks.stream()
                                     .filter(task -> task.getAssigner() != null && task.getAssigner().getId().equals(user.getId())) // Create dall'utente
                                     .filter(task -> task.getStatus() != TaskStatus.COMPLETED) // Non completate
                                     .filter(task -> task.getDataDeadline() != null && 
                                                     task.getDataDeadline().isAfter(todayStart) && 
                                                     task.getDataDeadline().isBefore(todayEnd))
                                     .collect(Collectors.toList());
            } else {
                todayTasks = allTasks.stream()
                                     .filter(task -> task.getUser() != null && task.getUser().getId().equals(user.getId())) // Assegnate all'utente
                                     .filter(task -> task.getStatus() != TaskStatus.COMPLETED) // Non completate
                                     .filter(task -> task.getDataDeadline() != null && 
                                                     task.getDataDeadline().isAfter(todayStart) && 
                                                     task.getDataDeadline().isBefore(todayEnd))
                                     .collect(Collectors.toList());
            }

            // Filtra le task completate negli ultimi 2 giorni in base al ruolo
            if ("PROJECT_MANAGER".equals(user.getRole())) {
                completedTasks = allTasks.stream()
                                         .filter(task -> task.getAssigner() != null && task.getAssigner().getId().equals(user.getId())) // Create dall'utente
                                         .filter(task -> task.getStatus() == TaskStatus.COMPLETED) // Completate
                                         .filter(task -> task.getDataCompleted() != null && 
                                                         task.getDataCompleted().isAfter(twoDaysAgo)) // Ultimi 2 giorni
                                         .collect(Collectors.toList());
            } else {
                completedTasks = allTasks.stream()
                                         .filter(task -> task.getUser() != null && task.getUser().getId().equals(user.getId())) // Assegnate all'utente
                                         .filter(task -> task.getStatus() == TaskStatus.COMPLETED) // Completate
                                         .filter(task -> task.getDataCompleted() != null && 
                                                         task.getDataCompleted().isAfter(twoDaysAgo)) // Ultimi 2 giorni
                                         .collect(Collectors.toList());
            }

            model.addAttribute("allTasks", allTasks);
        }

        model.addAttribute("user", user);
        model.addAttribute("userService", userService);
        model.addAttribute("projects", projects);
        model.addAttribute("todayTasks", todayTasks);
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
            model.addAttribute("completedCount", completedCount);
            model.addAttribute("inProgressCount", inProgressCount);
            model.addAttribute("pendingCount", pendingCount);

            Map<String, Long> categoryData = allTasks.stream()
                                                    .filter(task -> task.getCategoria() != null && !task.getCategoria().isEmpty())
                                                    .collect(Collectors.groupingBy(Task::getCategoria, Collectors.counting()));
            model.addAttribute("categoryData", categoryData);
        } else {
            model.addAttribute("completedPercentage", 0);
            model.addAttribute("inProgressPercentage", 0);
            model.addAttribute("pendingPercentage", 0);
        }

        return "index";
    }
}