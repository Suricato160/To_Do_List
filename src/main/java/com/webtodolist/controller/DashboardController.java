package com.webtodolist.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import com.webtodolist.model.Task;
import com.webtodolist.model.Task.TaskStatus;
import com.webtodolist.model.User;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webtodolist.model.Project;
import com.webtodolist.repository.ProjectRepository;
import com.webtodolist.repository.TaskRepository;
import com.webtodolist.repository.UserRepository;

@Controller
public class DashboardController {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    
    @GetMapping({"/", "/index"})
    public String dashboard(Model model,
                            @AuthenticationPrincipal UserDetails userDetails) {
        // Ottieni tutte le task
        List<Task> allTasks = taskRepository.findAll();
        model.addAttribute("tasks", allTasks);

        // Ottieni tutti i progetti
        // Ottieni l'utente autenticato
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        
        List<Project> projects;
        if (currentUser.isPresent()) {
            projects = projectRepository.findByUser(currentUser.get());
        } else {
            projects = null;
        }

        // Ottieni l'utente autenticato
        model.addAttribute("user", userDetails);
        model.addAttribute("projects", projects);
        
        
        
        
        // Task di oggi (con deadline oggi)
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        List<Task> todayTasks = taskRepository.findByDataDeadlineBetween(todayStart, todayEnd);
        if (todayTasks == null) {
            todayTasks = new ArrayList<>();
        }
        model.addAttribute("todayTasks", todayTasks);
        
        // Task completate
        List<Task> completedTasks = taskRepository.findByStatus(TaskStatus.COMPLETED);
        if (completedTasks == null) {
            completedTasks = new ArrayList<>();
        }
        model.addAttribute("completedTasks", completedTasks);
        
        // Calcola le percentuali per i grafici
        int totalTasks = allTasks.size();
        if (totalTasks > 0) {
            int completedCount = (int) allTasks.stream().filter(t -> t.getStatus() == TaskStatus.COMPLETED).count();
            int inProgressCount = (int) allTasks.stream().filter(t -> t.getStatus() == TaskStatus.WORK_IN_PROGRESS).count();
            int pendingCount = (int) allTasks.stream().filter(t -> t.getStatus() == TaskStatus.PENDING || t.getStatus() == TaskStatus.STARTED).count();
            
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