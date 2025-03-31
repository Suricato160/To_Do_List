package com.webtodolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webtodolist.entity.Project;
import com.webtodolist.entity.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);
    
    List<Project> findByUserIdOrderByDataCreationProjectDesc(Long userId);
    
    List<Project> findByTitleContainingIgnoreCase(String title);
}
