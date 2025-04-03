package com.webtodolist.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webtodolist.model.Project;
import com.webtodolist.model.User;
import com.webtodolist.service.ProjectService;
import com.webtodolist.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;


    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);







    @GetMapping
    public String getAllProjects(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Ottieni l'utente autenticato
        Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());
        User user = currentUser.orElse(null); // Gestisci il caso in cui l'utente non sia presente

        List<Project> projects = null;
        if (user != null) {
            projects = projectService.findProjectsByUser(user);
        }

        model.addAttribute("user", user); // Passa l'entità User invece di UserDetails
        model.addAttribute("projects", projects);
        model.addAttribute("userService", userService); // Aggiungi userService al modello

        return "projects";
    }

    @GetMapping("/{id}")
    public String getProjectById(@PathVariable("id") Long id, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Richiesta dettaglio progetto: id={}", id);
        Project project = projectService.findById(id);
        if (project == null) {
            logger.warn("Progetto non trovato per id: {}", id);
            return "redirect:/projects?error=Progetto non trovato";
        }
        Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("project", project);
        model.addAttribute("user", currentUser.orElse(null));
        model.addAttribute("userService", userService);
        return "projectDetail";
    }

    @GetMapping("/search")
    public String searchProjects(@RequestParam("query") String query, Model model) {
        List<Project> projects = projectService.searchProjectsByTitle(query);
        model.addAttribute("projects", projects);
        model.addAttribute("searchQuery", query);
        return "projects";
    }

    @GetMapping("/new")
    public String newProjectForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("project", new Project());

        // Ottieni l'utente autenticato
        Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());
        if (currentUser.isPresent()) {
            model.addAttribute("user", currentUser.get());
        }

        return "projectNew";
    }

    @PostMapping("/create")
    public String createProject(Project project, RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // Ottieni l'utente autenticato
            Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());

            if (currentUser.isPresent()) {
                project.setUser(currentUser.get());
                project.setDataCreationProject(LocalDateTime.now());
                projectService.saveProject(project);

                redirectAttributes.addFlashAttribute("successMessage", "Progetto creato con successo!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "Utente non trovato. Impossibile creare il progetto.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Errore durante la creazione del progetto: " + e.getMessage());
        }

        return "redirect:/projects";
    }
}