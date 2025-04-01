package com.webtodolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webtodolist.entity.Project;
import com.webtodolist.entity.User;
import com.webtodolist.repository.ProjectRepository;
import com.webtodolist.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Get all projects
    @GetMapping
    public String getAllProjects(Model model) {
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);
        return "projects/list";
    }

    // Get project by ID
    @GetMapping("/{id}")
    public String getProjectById(@PathVariable Long id, Model model) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            model.addAttribute("project", project.get());
            return "projects/detail";
        } else {
            return "redirect:/projects";
        }
    }

    // Display form to create a new project
    @GetMapping("/new")
    public String showNewProjectForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("users", userRepository.findAll());
        return "projects/create";
    }

    // Create a new project
    @PostMapping
    public String createProject(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
        // Set creation timestamp if not provided
        if (project.getDataCreationProject() == null) {
            project.setDataCreationProject(LocalDateTime.now());
        }
        projectRepository.save(project);
        redirectAttributes.addFlashAttribute("successMessage", "Project created successfully!");
        return "redirect:/projects";
    }

    // Display form to edit a project
    @GetMapping("/{id}/edit")
    public String showEditProjectForm(@PathVariable Long id, Model model) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            model.addAttribute("project", project.get());
            model.addAttribute("users", userRepository.findAll());
            return "projects/edit";
        } else {
            return "redirect:/projects";
        }
    }

    // Update a project
    @PostMapping("/{id}")
    public String updateProject(@PathVariable Long id, @ModelAttribute Project projectDetails, 
                               RedirectAttributes redirectAttributes) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            
            // Update timestamp
            project.setDataUpdateProject(LocalDateTime.now());
            
            // Update provided fields
            if (projectDetails.getTitle() != null) {
                project.setTitle(projectDetails.getTitle());
            }
            if (projectDetails.getDescription() != null) {
                project.setDescription(projectDetails.getDescription());
            }
            if (projectDetails.getDataDeadline() != null) {
                project.setDataDeadline(projectDetails.getDataDeadline());
            }
            
            // Handle project close/completion
            if (projectDetails.getDataClosedProject() != null && 
                project.getDataClosedProject() == null) {
                project.setDataClosedProject(LocalDateTime.now());
            }
            
            projectRepository.save(project);
            redirectAttributes.addFlashAttribute("successMessage", "Project updated successfully!");
        }
        return "redirect:/projects";
    }

    // Delete a project
    @GetMapping("/{id}/delete")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            projectRepository.delete(project.get());
            redirectAttributes.addFlashAttribute("successMessage", "Project deleted successfully!");
        }
        return "redirect:/projects";
    }

    // Get projects by user
    @GetMapping("/user/{userId}")
    public String getProjectsByUserId(@PathVariable Long userId, Model model) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Project> projects = projectRepository.findByUserIdOrderByDataCreationProjectDesc(userId);
            model.addAttribute("projects", projects);
            model.addAttribute("user", user.get());
            return "projects/user-projects";
        } else {
            return "redirect:/projects";
        }
    }

    // Search projects by title
    @GetMapping("/search")
    public String searchProjects(@RequestParam String title, Model model) {
        List<Project> projects = projectRepository.findByTitleContainingIgnoreCase(title);
        model.addAttribute("projects", projects);
        model.addAttribute("searchTerm", title);
        return "projects/search-results";
    }
}
