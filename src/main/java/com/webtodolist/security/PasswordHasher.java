package com.webtodolist.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHasher {
    private final BCryptPasswordEncoder encoder;
    
    public PasswordHasher() {
        this.encoder = new BCryptPasswordEncoder();
    }
    
    /**
     * Hashes a password using BCrypt
     * @param rawPassword The plain text password to hash
     * @return The hashed password
     */
    public String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    /**
     * Verifies if a raw password matches a hashed password
     * @param rawPassword The plain text password to check
     * @param hashedPassword The hashed password to compare against
     * @return true if the passwords match, false otherwise
     */
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}

// public static void main(String[] args) {
//     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // istanza per criptare
//     String rawPassword = "pass123"; // Sostituisci con la password che desideri
//     String hashedPassword = encoder.encode(rawPassword); // variabile in cui cripto, chiamo oggetto encoder, metodo e passo la mia pass in chiaro
//     System.out.println("Hash: " + hashedPassword);  // printo il risultato



