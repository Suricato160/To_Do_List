package com.webtodolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webtodolist.model.User;
import com.webtodolist.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getFullName(User user) {
        if (user != null && user.getNome() != null && user.getCognome() != null) {
            return user.getNome() + " " + user.getCognome() + " ("+ user.getMansione() + ")";
        }
        return user != null && user.getNome() != null ? user.getNome() : "Utente sconosciuto";
    }
}