package com.webtodolist.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titolo;
    private String descrizione;
    private String status;
    private Boolean completed;
    private LocalDateTime deadline;
    private String categoria;
    private String assegnatoA;
    private LocalDateTime createdAt;
}
