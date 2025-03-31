package com.webtodolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.webtodolist.entity.Task;
import com.webtodolist.entity.Task.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status); // Sostituito findByCompleted
    
    List<Task> findByUserId(Long userId); // Cambio da int a Long
    
    List<Task> findByProjectId(Long projectId); // Cambio da ProjectsId a ProjectId per coerenza
    
    List<Task> findByCategoriaAndUserId(String categoria, Long userId); // Cambio da int a Long
    
    List<Task> findByTitoloContainingIgnoreCase(String titolo);
}