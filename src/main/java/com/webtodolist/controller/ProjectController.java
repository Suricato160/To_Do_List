package com.webtodolist.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webtodolist.model.Project;
import com.webtodolist.model.User;
import com.webtodolist.repository.ProjectRepository;
import com.webtodolist.repository.UserRepository;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public String getAllProjects(Model model) {
        // In a real application with authentication, you would get the current user
        // For now, let's use a hardcoded user ID (1) or get the first user as example
        Optional<User> currentUser = userRepository.findById(1L);
        
        List<Project> projects;
        if (currentUser.isPresent()) {
            projects = projectRepository.findByUser(currentUser.get());
            model.addAttribute("user", currentUser.get());
        } else {
            projects = projectRepository.findAll();
        }
        
        model.addAttribute("projects", projects);
        return "projects";
    }
    
    @GetMapping("/{id}")
    public String getProjectById(@PathVariable("id") Long id, Model model) {
        Optional<Project> project = projectRepository.findById(id);
        
        if (project.isPresent()) {
            model.addAttribute("project", project.get());
            model.addAttribute("user", project.get().getUser());
            return "project-detail";  // This would be a new template to create
        } else {
            return "redirect:/projects";
        }
    }
    
    @GetMapping("/search")
    public String searchProjects(@RequestParam("query") String query, Model model) {
        List<Project> projects = projectRepository.findByTitleContainingIgnoreCase(query);
        model.addAttribute("projects", projects);
        model.addAttribute("searchQuery", query);
        return "projects";
    }
    
    @GetMapping("/new")
    public String newProjectForm(Model model) {
        model.addAttribute("project", new Project());
        
        // In a real application, get the current user from authentication
        Optional<User> currentUser = userRepository.findById(1L);
        if (currentUser.isPresent()) {
            model.addAttribute("user", currentUser.get());
        }
        
        return "projectNew";  // Changed from "project-form" to match the actual template name
    }
    
    @PostMapping("/create")
    public String createProject(Project project, RedirectAttributes redirectAttributes) {
        try {
            // In a real application, get the current user from authentication
            Optional<User> currentUser = userRepository.findById(1L);
            
            if (currentUser.isPresent()) {
                project.setUser(currentUser.get());
                project.setDataCreationProject(LocalDateTime.now());
                projectRepository.save(project);
                
                redirectAttributes.addFlashAttribute("successMessage", "Progetto creato con successo!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Utente non trovato. Impossibile creare il progetto.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Errore durante la creazione del progetto: " + e.getMessage());
        }
        
        return "redirect:/projects";
    }
    
    // Debug endpoint to check form handling
    @GetMapping("/debug")
    @ResponseBody
    public String debugProject() {
        return "Project form debug endpoint - Controller is working";
    }
}