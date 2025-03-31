package com.webtodolist.service;

import com.webtodolist.entity.Project;
import com.webtodolist.entity.User;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Optional<Project> findById(Long id);
    
    List<Project> findByUser(User user);
    
    List<Project> findByUserIdOrderByDataCreationProjectDesc(Long userId);
    
    List<Project> findByTitleContainingIgnoreCase(String title);
    
    Project saveProject(Project project);
    
    void deleteProject(Long id);
    
    List<Project> findAllProjects();
}