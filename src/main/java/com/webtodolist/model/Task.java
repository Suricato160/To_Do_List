package com.webtodolist.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titolo")
    private String titolo;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "status")
    @Enumerated(EnumType.STRING) // Salva il valore dell'enum come stringa nel DB
    private TaskStatus status;
    
    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "data_pending")
    private LocalDateTime dataPending;

    @Column(name = "data_started")
    private LocalDateTime dataStarted;

    @Column(name = "data_progress")
    private LocalDateTime dataProgress;

    @Column(name = "data_completed")
    private LocalDateTime dataCompleted;

    @Column(name = "data_deadline")
    private LocalDateTime dataDeadline;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "reminder")
    private String reminder;   // questo potremmo averlo in enum 

    @Column(name = "notes")
    private String notes;

    @Column(name = "repeat_task")
    private String repeatTask;    // questo potremmo averlo in enum

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "data_created_task")
    private LocalDateTime dataCreatedTask;

    @Column(name = "data_updated_task")
    private LocalDateTime dataUpdatedTask;




    // ================== Relazioni ===================

    @ManyToOne
    @JoinColumn(name = "projects_id") 
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER) // Ensure assigner is eagerly fetched
    @JoinColumn(name = "assigner_id")
    private User assigner;

    // =========== ENUM ============

    public enum TaskStatus {
        PENDING,
        STARTED,
        WORK_IN_PROGRESS,
        COMPLETED
    }

    public enum TaskPriority { // Nuovo enum
        LOW, MEDIUM, HIGH
    }
}
