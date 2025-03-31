package com.webtodolist.service;

import com.webtodolist.entity.Task;
import com.webtodolist.entity.Task.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> findById(Long id);
    
    List<Task> findByStatus(TaskStatus status);
    
    List<Task> findByUserId(Long userId);
    
    List<Task> findByProjectId(Long projectId);
    
    List<Task> findByCategoriaAndUserId(String categoria, Long userId);
    
    List<Task> findByTitoloContainingIgnoreCase(String titolo);
    
    Task saveTask(Task task);
    
    void deleteTask(Long id);
    
    List<Task> findAllTasks();
}