package com.webtodolist.service;


import com.webtodolist.model.Project; // Assumi che esista
import com.webtodolist.model.User;

import java.util.List;
import com.webtodolist.repository.ProjectRepository; // Assumi che esista
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class ProjectService {






    @Autowired
    private ProjectRepository projectRepository; // Repository per accedere ai dati dei progetti

   
    public List<Project> getAllProjects() {
        return projectRepository.findAll(); // Metodo per ottenere tutti i progetti
    }


    public Project findById(Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId.intValue());
        return project.orElse(null); // Return the project if found, otherwise return null
    }

    public List<Project> findProjectsByUser(User user) {
        return projectRepository.findByUser(user);
    }
}
