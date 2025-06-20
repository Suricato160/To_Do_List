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
    
    // Nuovo metodo per ottenere il nome completo senza mansione
    public String getSimpleFullName(User user) {
        if (user != null && user.getNome() != null && user.getCognome() != null) {
            return user.getNome() + " " + user.getCognome();
        }
        return user != null && user.getNome() != null ? user.getNome() : "Utente sconosciuto";
    }
    
    // Metodo per evitare di modificare l'oggetto User originale quando si visualizzano i nomi completi
    public User getUserWithFullName(User user) {
        if (user == null) return null;
        
        // Creare una copia per evitare di modificare l'oggetto originale
        User userCopy = new User();
        userCopy.setId(user.getId());
        userCopy.setUsername(user.getUsername());
        userCopy.setEmail(user.getEmail());
        userCopy.setRole(user.getRole());
        userCopy.setMansione(user.getMansione());
        
        // Impostare il campo nome sul nome completo
        userCopy.setNome(getSimpleFullName(user));
        
        return userCopy;
    }
}