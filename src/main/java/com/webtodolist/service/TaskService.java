package com.webtodolist.service;

import com.webtodolist.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findByCategoriaAndUserId(String categoria, Long userId);

    List<Task> findByStatus(Task.TaskStatus status);

    List<Task> findByProjectId(Long projectId);

    List<Task> findByTitoloContainingIgnoreCase(String titolo);

    void deleteTask(Long id);

    List<Task> findAllTasks();

    Optional<Task> findById(Long id);

    List<Task> findByUserId(Long userId);

    Task saveTask(Task task);

    void deleteById(Long id);

    List<Task> findAll(); // Added the missing method declaration
}