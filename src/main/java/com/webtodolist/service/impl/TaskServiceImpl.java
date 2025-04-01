package com.webtodolist.service.impl;

import com.webtodolist.entity.Task;
import com.webtodolist.repository.TaskRepository;
import com.webtodolist.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Override
    public List<Task> findByCategoriaAndUserId(String categoria, Long userId) {
        return taskRepository.findByCategoriaAndUserId(categoria, userId);
    }

    @Override
    public List<Task> findByStatus(Task.TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    public List<Task> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public List<Task> findByTitoloContainingIgnoreCase(String titolo) {
        return taskRepository.findByTitoloContainingIgnoreCase(titolo);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long id) {
        // Implementing the required method to delete a task by ID
        taskRepository.deleteById(id);
    }
}
   