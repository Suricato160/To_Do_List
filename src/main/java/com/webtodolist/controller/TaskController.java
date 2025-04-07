package com.webtodolist.controller;

import com.webtodolist.model.Comment;
import com.webtodolist.model.Project;
import com.webtodolist.model.Task;
import com.webtodolist.model.User;
import com.webtodolist.service.CommentService;
import com.webtodolist.service.CustomUserDetails;
import com.webtodolist.service.ProjectService;
import com.webtodolist.service.TaskService;
import com.webtodolist.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CommentService commentService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping("/task-list")
    public String getTaskList(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());

        List<Task> tasks = null;
        if (currentUser.isPresent()) {
            List<Project> projects = projectService.findProjectsByUser(currentUser.get());
            tasks = projects.stream()
                    .flatMap(project -> project.getTasks().stream())
                    .toList();
            model.addAttribute("user", currentUser.get());
        } else {
            model.addAttribute("user", null);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("userService", userService);
        return "task-list";
    }

    @GetMapping("/new")
    public String newTask(@RequestParam(value = "projectId", required = false) Long projectId, Model model) {
        Task task = new Task();
        Project project = null;

        if (projectId != null) {
            project = projectService.findById(projectId);
            if (project == null) {
                return "redirect:/projects";
            }
            task.setProject(project);
        }

        model.addAttribute("task", task);
        model.addAttribute("project", project);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("projects", projectService.getAllProjects());
        return "taskNew";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute("task") Task task,
            @RequestParam(value = "projectId", required = false) Long projectId, Model model) {
        try {
            // Ottieni CustomUserDetails dal contesto di sicurezza
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            User currentUser = userDetails.getUser(); // Estrai l'entità User

            task.setAssigner(currentUser);
            task.setDataCreatedTask(java.time.LocalDateTime.now());

            if (projectId != null) {
                Project project = projectService.findById(projectId);
                if (project == null) {
                    throw new IllegalArgumentException("Progetto non trovato");
                }
                task.setProject(project);
            }

            taskService.saveTask(task);
            return "redirect:/projects/" + task.getProject().getId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("task", task);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("projects", projectService.getAllProjects());
            model.addAttribute("project", projectId != null ? projectService.findById(projectId) : null);
            return "taskNew";
        }
    }

    @GetMapping("/edit")
    public String editTask(@RequestParam("taskId") Long taskId, Model model) {
        Task task = taskService.findTaskById(taskId);
        if (task == null) {
            return "redirect:/task-list"; // Task non trovata, torna alla lista
        }

        model.addAttribute("task", task);
        model.addAttribute("project", task.getProject()); // Progetto associato, se presente
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("userService", userService); // Per il nome completo nella sidebar
        Optional<User> currentUser = userService
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", currentUser.orElse(null)); // Utente autenticato
        return "taskEdit";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute("task") Task task,
            @RequestParam(value = "projectId", required = false) Long projectId,
            Model model) {
        try {
            // Recupera la task esistente
            Task existingTask = taskService.findTaskById(task.getId());
            if (existingTask == null) {
                throw new IllegalArgumentException("Task non trovata");
            }

            // Aggiorna i campi
            existingTask.setTitolo(task.getTitolo());
            existingTask.setDescrizione(task.getDescrizione());
            existingTask.setDataDeadline(task.getDataDeadline());
            existingTask.setCategoria(task.getCategoria());
            existingTask.setPriority(task.getPriority());
            existingTask.setStatus(task.getStatus());

            // Aggiorna l'utente assegnato
            Optional<User> assignedUser = userService.findById(task.getUser().getId());
            if (assignedUser.isPresent()) {
                existingTask.setUser(assignedUser.get());
            } else {
                throw new IllegalArgumentException("Utente non trovato");
            }

            // Aggiorna il progetto, se specificato
            if (projectId != null) {
                Project project = projectService.findById(projectId);
                if (project == null) {
                    throw new IllegalArgumentException("Progetto non trovato");
                }
                existingTask.setProject(project);
            }

            taskService.saveTask(existingTask);
            return "redirect:/task-list"; // O torna al dettaglio del progetto: /projects/{projectId}
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("task", task);
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("projects", projectService.getAllProjects());
            model.addAttribute("project", projectId != null ? projectService.findById(projectId) : null);
            model.addAttribute("userService", userService);
            Optional<User> currentUser = userService
                    .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("user", currentUser.orElse(null));
            return "taskEdit";
        }
    }

    // Add these methods to TaskController.java

    @GetMapping("/detail")
    public String viewTaskDetail(@RequestParam("taskId") Long taskId, Model model) {
        Task task = taskService.findTaskById(taskId);
        if (task == null) {
            logger.warn("Task non trovata per id={}", taskId);
            return "redirect:/task-list";
        }
        logger.info("Task recuperata: id={}, status={}", task.getId(), task.getStatus());

        model.addAttribute("task", task);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", task.getComments());
        model.addAttribute("userService", userService);
        Optional<User> currentUser = userService
                .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", currentUser.orElse(null));
        return "taskDetail";
    }

    @PostMapping("/status/update")
    public String updateTaskStatus(@RequestParam("taskId") Long taskId,
            @RequestParam("status") String status,
            RedirectAttributes redirectAttributes) {
        logger.info("Aggiornamento stato: taskId={}, status={}", taskId, status);
        Task task = taskService.findTaskById(taskId);
        if (task != null) {
            try {
                Task.TaskStatus newStatus = Task.TaskStatus.valueOf(status.toUpperCase());
                task.setStatus(newStatus);
                taskService.saveTask(task); // Ora è transazionale
                redirectAttributes.addFlashAttribute("successMessage", "Stato aggiornato con successo!");
            } catch (IllegalArgumentException e) {
                logger.error("Errore conversione stato: {}", status, e);
                redirectAttributes.addFlashAttribute("errorMessage", "Stato non valido: " + status);
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Task non trovata.");
        }
        return "redirect:/tasks/detail?taskId=" + taskId;
    }

    @PostMapping("/comments/create")
    public String createComment(@ModelAttribute("comment") Comment comment,
            @RequestParam("taskId") Long taskId) {
        Task task = taskService.findTaskById(taskId);
        if (task != null) {
            User currentUser = userService
                    .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            comment.setTask(task);
            comment.setUser(currentUser);
            comment.setDataComment(LocalDateTime.now());
            commentService.saveComment(comment);
        }
        return "redirect:/tasks/detail?taskId=" + taskId;
    }

    @PostMapping("/comments/update")
    public String updateComment(@RequestParam("commentId") Long commentId,
            @RequestParam("text") String text,
            @RequestParam("taskId") Long taskId) {
        logger.info("Aggiornamento commento: commentId={}, taskId={}, text={}", commentId, taskId, text);
        Comment comment = commentService.findCommentById(commentId);
        if (comment != null) {
            User currentUser = userService
                    .findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            if (comment.getUser().getId().equals(currentUser.getId())) {
                comment.setText(text);
                commentService.saveComment(comment);
                logger.info("Commento aggiornato con successo");
            } else {
                logger.warn("Utente non autorizzato a modificare il commento");
            }
        }
        return "redirect:/tasks/detail?taskId=" + taskId;
    }

    // Nuovo endpoint per la ricerca
    @GetMapping("/search")
    @ResponseBody // Restituisce JSON per AJAX
    public List<Task> searchTasks(@RequestParam("query") String query) {
        logger.info("Ricerca task con query: {}", query);
        return taskService.searchTasksByTitle(query);
    }

    // Endpoint per l'autocomplete
    @GetMapping("/autocomplete")
    @ResponseBody
    public List<AutocompleteResponse> autocompleteTasks(@RequestParam("term") String term) {
        logger.info("Richiesta autocomplete per term: {}", term);
        List<Task> tasks = taskService.searchTasksByTitle(term);
        return tasks.stream()
                .map(task -> new AutocompleteResponse(task.getId(), task.getTitolo()))
                .collect(Collectors.toList());
    }

    // Classe interna per la risposta JSON
    public static class AutocompleteResponse {
        private Long value;
        private String label;

        public AutocompleteResponse(Long value, String label) {
            this.value = value;
            this.label = label;
        }

        public Long getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

    @GetMapping("") // Mappa direttamente /tasks
    public String getTasks(@RequestParam(value = "filter", required = false) String filter,
                           @RequestParam(value = "category", required = false) String categoryName,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> currentUser = userService.findByUsername(userDetails.getUsername());
        List<Task> tasks;

        if (currentUser.isPresent()) {
            List<Project> projects = projectService.findProjectsByUser(currentUser.get());
            tasks = projects.stream()
                           .flatMap(project -> project.getTasks().stream())
                           .collect(Collectors.toList());

            if (filter != null) {
                switch (filter.toLowerCase()) {
                    case "today":
                        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
                        LocalDateTime todayEnd = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
                        tasks = tasks.stream()
                                     .filter(task -> task.getDataDeadline() != null &&
                                                     task.getDataDeadline().isAfter(todayStart) &&
                                                     task.getDataDeadline().isBefore(todayEnd))
                                     .collect(Collectors.toList());
                        break;
                    case "completed":
                        tasks = tasks.stream()
                                     .filter(task -> task.getStatus() == Task.TaskStatus.COMPLETED)
                                     .collect(Collectors.toList());
                        break;
                }
            }

            if (categoryName != null && !categoryName.isEmpty()) {
                tasks = tasks.stream()
                             .filter(task -> categoryName.equals(task.getCategoria()))
                             .collect(Collectors.toList());
            }

            model.addAttribute("user", currentUser.get());
        } else {
            tasks = Collections.emptyList();
            model.addAttribute("user", null);
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("userService", userService);
        model.addAttribute("filter", filter);
        return "task-list";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam("taskId") Long taskId, RedirectAttributes redirectAttributes) {
        try {
            Task task = taskService.findTaskById(taskId);
            if (task == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Task non trovata.");
                return "redirect:/tasks/task-list";
            }
            taskService.deleteTaskById(taskId);
            redirectAttributes.addFlashAttribute("successMessage", "Task eliminata con successo.");
        } catch (Exception e) {
            logger.error("Errore durante l'eliminazione della task con ID {}: {}", taskId, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Errore durante l'eliminazione della task: " + e.getMessage());
        }
        return "redirect:/tasks/task-list";
    }

}
