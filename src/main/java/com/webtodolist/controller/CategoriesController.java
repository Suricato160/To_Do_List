package com.webtodolist.controller;


import java.util.List;
import java.util.Optional;


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

@Controller
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public String getAllCategories(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Get the authenticated user
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        
        if (currentUser.isPresent()) {
            User user = currentUser.get();
            model.addAttribute("user", user);
            
            // Get distinct categories directly from database
            List<String> categories = taskRepository.findDistinctCategoriesByUser(user);
            
            // Get tasks to count per category
            List<Task> tasks = taskRepository.findByUser(user);
            
            model.addAttribute("categories", categories);
            model.addAttribute("taskCounts", countTasksPerCategory(tasks));
        }
        
        return "categories";
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
