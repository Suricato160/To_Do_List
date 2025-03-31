package com.webtodolist.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 45)
    private String title;
    
    @Column(length = 45)
    private String description;
    
    @Column(name = "data_creation_project")
    private LocalDateTime dataCreationProject;
    
    @Column(name = "data_update_project")
    private LocalDateTime dataUpdateProject;
    
    @Column(name = "data_deadline")
    private LocalDateTime dataDeadline;
    
    @Column(name = "data_closed_project")
    private LocalDateTime dataClosedProject;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
}
