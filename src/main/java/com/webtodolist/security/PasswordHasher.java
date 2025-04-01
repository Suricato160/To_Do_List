package com.webtodolist.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // istanza per criptare
        String rawPassword = "pass123"; // Sostituisci con la password che desideri
        String hashedPassword = encoder.encode(rawPassword); // variabile in cui cripto, chiamo oggetto encoder, metodo e passo la mia pass in chiaro
        System.out.println("Hash: " + hashedPassword);  // printo il risultato
    }
}

