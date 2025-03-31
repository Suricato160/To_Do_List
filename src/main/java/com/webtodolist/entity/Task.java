package com.webtodolist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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
    
    @Column(name = "completed")
    private boolean completed;

    @Column(name = "data_pending")
    private Data dataPending;

    @Column(name = "data_started")
    private Data dataStarted;

    @Column(name = "data_progress")
    private Data dataProgress;

    @Column(name = "data_completed")
    private Data dataCompleted;

    @Column(name = "data_deadline")
    private Data dataDeadline;

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
    private Data dataCreatedTask;

    @Column(name = "data_updated_task")
    private Data dataUpdatedTask;




    // ================== Relazioni ===================


    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "assigner_id")
    private User assigner;



    // =========== ENUM ============

    public enum TaskStatus {
        PENDING,
        STARTED,
        WORK_IN_PROGRESS,
        COMPLETED
    }


}
