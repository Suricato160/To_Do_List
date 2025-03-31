package com.webtodolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.webtodolist.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompleted(boolean completed);
    
    List<Task> findByUserId(int userId);
    
    List<Task> findByProjectsId(int projectId);
    
    List<Task> findByCategoriaAndUserId(String categoria, int userId);
    
    List<Task> findByTitoloContainingIgnoreCase(String titolo);
}
