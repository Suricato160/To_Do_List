package com.webtodolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webtodolist.entity.Project;
import com.webtodolist.entity.User;
import com.webtodolist.repository.ProjectRepository;
import com.webtodolist.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Get all projects
    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Get project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new project
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        // Set creation timestamp if not provided
        if (project.getDataCreationProject() == null) {
            project.setDataCreationProject(LocalDateTime.now());
        }
        Project savedProject = projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    // Update a project
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return projectRepository.findById(id)
                .map(project -> {
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
                    
                    Project updatedProject = projectRepository.save(project);
                    return ResponseEntity.ok(updatedProject);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Get projects by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Project> projects = projectRepository.findByUserIdOrderByDataCreationProjectDesc(userId);
        return ResponseEntity.ok(projects);
    }

    // Search projects by title
    @GetMapping("/search")
    public List<Project> searchProjects(@RequestParam String title) {
        return projectRepository.findByTitleContainingIgnoreCase(title);
    }
}
