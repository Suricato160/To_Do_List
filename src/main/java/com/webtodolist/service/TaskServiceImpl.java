package com.webtodolist.service;

import com.webtodolist.entity.Task;
import com.webtodolist.entity.Task.TaskStatus;
import com.webtodolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Override
    public List<Task> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public List<Task> findByCategoriaAndUserId(String categoria, Long userId) {
        return taskRepository.findByCategoriaAndUserId(categoria, userId);
    }

    @Override
    public List<Task> findByTitoloContainingIgnoreCase(String titolo) {
        return taskRepository.findByTitoloContainingIgnoreCase(titolo);
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}