package com.webtodolist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private String status;

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


    @Column(name = "projects_id")
    private int projectsId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "assigner_id")
    private int assignerId;









    private boolean completed;
}
