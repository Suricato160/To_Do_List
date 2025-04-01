package com.webtodolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtodolist.model.Project;
import com.webtodolist.model.User;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByUser(User user);
    
    List<Project> findByUserIdOrderByDataCreationProjectDesc(int userId);
    
    List<Project> findByTitleContainingIgnoreCase(String title);
}
