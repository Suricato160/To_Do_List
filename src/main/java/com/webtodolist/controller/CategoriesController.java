package com.webtodolist.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webtodolist.model.Task;
import com.webtodolist.model.User;
import com.webtodolist.repository.TaskRepository;
import com.webtodolist.repository.UserRepository;
import com.webtodolist.service.TaskService;
import com.webtodolist.service.UserService;

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TaskService taskService;
    
    @GetMapping
    public String getAllCategories(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Get the authenticated user
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            model.addAttribute("user", user);
            
            // Get categories from directly assigned tasks
            List<String> directCategories = taskRepository.findDistinctCategoriesByUser(user);
            
            // Get categories from tasks in projects where user is involved
            List<String> projectCategories = taskRepository.findDistinctCategoriesByUserProjects(user);
            
            // Merge both lists using a Set to avoid duplicates
            Set<String> allCategories = new HashSet<>();
            allCategories.addAll(directCategories);
            allCategories.addAll(projectCategories);
            
            // Convert back to List for Thymeleaf
            List<String> mergedCategories = new ArrayList<>(allCategories);
            
            // Get both directly assigned tasks and project tasks
            List<Task> directTasks = taskRepository.findByUser(user);
            List<Task> projectTasks = taskRepository.findTasksByUserProjects(user);
            
            // Merge task lists
            List<Task> allTasks = new ArrayList<>();
            allTasks.addAll(directTasks);
            allTasks.addAll(projectTasks);
            
            model.addAttribute("categories", mergedCategories);
            model.addAttribute("taskCounts", countTasksPerCategory(allTasks));
        }
        
        return "categories";
    }
    
    @GetMapping("/tasks")
    public String getTasksByCategory(@RequestParam("category") String categoryName, 
                                    Model model, 
                                    @AuthenticationPrincipal UserDetails userDetails) {
        // Get the authenticated user
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            model.addAttribute("user", user);
            model.addAttribute("userService", userService);
            
            // Get tasks for this category (both directly assigned to user and in user's projects)
            List<Task> directTasks = taskRepository.findByUserAndCategoria(user, categoryName);
            
            // Get tasks for this category from projects where the user is involved
            List<Task> projectTasks = taskRepository.findByUserProjectsAndCategoria(user, categoryName);
            
            // Merge both task lists
            List<Task> allTasks = new ArrayList<>();
            allTasks.addAll(directTasks);
            allTasks.addAll(projectTasks);
            
            // Filter out duplicates if any
            Set<Long> addedTaskIds = new HashSet<>();
            List<Task> uniqueTasks = new ArrayList<>();
            
            for (Task task : allTasks) {
                if (!addedTaskIds.contains(task.getId())) {
                    uniqueTasks.add(task);
                    addedTaskIds.add(task.getId());
                }
            }
            
            model.addAttribute("tasks", uniqueTasks);
            model.addAttribute("categoryName", categoryName);
        } else {
            // Handle case when user is not found
            model.addAttribute("tasks", new ArrayList<>());
            model.addAttribute("categoryName", categoryName);
        }
        
        return "tasks-by-category";
    }
    
    @PostMapping("/add")
    public String addCategory(@RequestParam("categoryName") String categoryName, 
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails userDetails) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Il nome della categoria non può essere vuoto");
            return "redirect:/categories";
        }
        
        // In a real application, you might want to save this to a Category entity
        // For now, we'll just show a success message
        redirectAttributes.addFlashAttribute("successMessage", "Categoria aggiunta con successo!");
        return "redirect:/categories";
    }
    
    @PostMapping("/edit")
    public String updateCategory(@RequestParam("originalCategoryName") String originalCategoryName,
                                 @RequestParam("newCategoryName") String newCategoryName,
                                 RedirectAttributes redirectAttributes) {
        if (newCategoryName == null || newCategoryName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Il nome della categoria non può essere vuoto");
            return "redirect:/categories";
        }
        
        try {
            // Aggiorna tutte le task con questa categoria
            int updatedTasks = taskService.updateTasksCategory(originalCategoryName, newCategoryName);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Categoria '" + originalCategoryName + "' rinominata in '" + newCategoryName + "'. " +
                "Aggiornate " + updatedTasks + " task.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Errore durante l'aggiornamento della categoria: " + e.getMessage());
        }
        
        return "redirect:/categories";
    }
    
    private java.util.Map<String, Integer> countTasksPerCategory(List<Task> tasks) {
        java.util.Map<String, Integer> counts = new java.util.HashMap<>();
        
        for (Task task : tasks) {
            String category = task.getCategoria();
            if (category != null && !category.isEmpty()) {
                counts.put(category, counts.getOrDefault(category, 0) + 1);
            }
        }
        
        return counts;
    }
}
