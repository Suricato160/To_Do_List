package com.webtodolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webtodolist.model.User;
import com.webtodolist.model.User.UserRole;
import com.webtodolist.repository.UserRepository;
import com.webtodolist.security.PasswordHasher;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordHasher passwordHasher;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Create a new user object for the form
        User user = new User();
        user.setRole(UserRole.USER); // Default role for new users
        model.addAttribute("user", user);
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, 
                              @RequestParam("confirmPassword") String confirmPassword,
                              RedirectAttributes redirectAttributes) {
        
        // Validate that passwords match
        if (!user.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Le password non corrispondono");
            return "redirect:/register";
        }
        
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Nome utente già in uso");
            redirectAttributes.addFlashAttribute("user", user); // Keep form data
            return "redirect:/register";
        }
        
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email già in uso");
            redirectAttributes.addFlashAttribute("user", user); // Keep form data
            return "redirect:/register";
        }
        
        try {
            // Hash the password before saving
            String hashedPassword = passwordHasher.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            
            // Set default role if not specified
            if (user.getRole() == null) {
                user.setRole(UserRole.USER);
            }
            
            // Save the user
            userRepository.save(user);
            
            // Set success message and redirect to login
            redirectAttributes.addFlashAttribute("success", "Registrazione completata con successo! Ora puoi accedere.");
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Errore durante la registrazione: " + e.getMessage());
            return "redirect:/register";
        }
    }
}