package com.webtodolist.service;

import com.webtodolist.entity.Project;
import com.webtodolist.entity.User;
import com.webtodolist.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findByUser(User user) {
        return projectRepository.findByUser(user);
    }

    @Override
    public List<Project> findByUserIdOrderByDataCreationProjectDesc(Long userId) {
        return projectRepository.findByUserIdOrderByDataCreationProjectDesc(userId);
    }

    @Override
    public List<Project> findByTitleContainingIgnoreCase(String title) {
        return projectRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }
}