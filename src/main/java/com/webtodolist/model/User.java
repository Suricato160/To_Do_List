package com.webtodolist.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="username", nullable = false, unique = true, length = 45)
    private String username;

    @Column(name = "password",nullable = false, length = 45)
    private String password;

    @Column(name = "email",nullable = false, unique = true, length = 45)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "nome", length = 45)
    private String nome;

    @Column(name= "cognome", length = 45)
    private String cognome;

    @Column(name = "mansione", length = 45)
    private String mansione;



    // ================ relazioni ================

    @OneToMany(mappedBy = "users")
    private List<Project> projects;

    @OneToMany(mappedBy = "users")
    private List<Task> tasks;

    @OneToMany(mappedBy = "users")
    private List<Comment> comments;

    // Enum for user roles
    public enum UserRole {
        ADMIN, USER, MANAGER
    }
}
