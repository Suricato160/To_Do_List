package com.webtodolist.service;

import com.webtodolist.model.Task;
import com.webtodolist.model.Task.TaskStatus;
import com.webtodolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> findTasksByDeadlineBetween(LocalDateTime start, LocalDateTime end) {
        return taskRepository.findByDataDeadlineBetween(start, end);
    }

    public List<Task> findTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    @Transactional
    public Task saveTask(Task task) {
        // Verifica che i campi obbligatori siano valorizzati
        if (task.getTitolo() == null || task.getTitolo().isEmpty()) {
            throw new IllegalArgumentException("Il titolo della task è obbligatorio");
        }
        if (task.getStatus() == null) {
            task.setStatus(Task.TaskStatus.PENDING); // Valore di default
        }
        if (task.getPriority() == null) {
            task.setPriority(Task.TaskPriority.LOW); // Valore di default
        }
        if (task.getProject() == null) {
            throw new IllegalArgumentException("Il progetto è obbligatorio");
        }
        if (task.getUser() == null) {
            throw new IllegalArgumentException("L'utente assegnato è obbligatorio");
        }
        if (task.getAssigner() == null) {
            throw new IllegalArgumentException("L'assigner è obbligatorio");
        }

        // Salva la task nel database e restituisci l'entità aggiornata
        return taskRepository.save(task);
    }

    public Task findTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    @Transactional
    public void deleteTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task con ID " + id + " non trovata"));
        taskRepository.delete(task); // Elimina la task e i commenti
    }

    // Nuovo metodo per la ricerca
    public List<Task> searchTasksByTitle(String query) {
        return taskRepository.findByTitoloContainingIgnoreCase(query);
    }

    /**
     * Aggiorna il nome di una categoria in tutte le task associate
     * 
     * @param oldCategoryName il nome attuale della categoria
     * @param newCategoryName il nuovo nome della categoria
     * @return il numero di task aggiornate
     */
    @Transactional
    public int updateTasksCategory(String oldCategoryName, String newCategoryName) {
        if (oldCategoryName == null || newCategoryName == null) {
            throw new IllegalArgumentException("I nomi delle categorie non possono essere nulli");
        }
        return taskRepository.updateTasksCategory(oldCategoryName, newCategoryName);
    }
}