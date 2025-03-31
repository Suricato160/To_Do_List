package com.webtodolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webtodolist.entity.Task;
import com.webtodolist.repository.TaskRepository;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Ottieni tutte le attività
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Crea una nuova attività
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

   

  
}
