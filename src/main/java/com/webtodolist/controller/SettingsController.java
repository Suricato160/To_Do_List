package com.webtodolist.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webtodolist.model.User;
import com.webtodolist.repository.UserRepository;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public String showSettings(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Get the authenticated user
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        
        if (currentUser.isPresent()) {
            model.addAttribute("user", currentUser.get());
        }
        
        return "settings";
    }
}
