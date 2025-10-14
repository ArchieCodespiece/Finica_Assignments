package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Jornalentry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_description", nullable = false)
    private String taskDescription;

    // Default constructor
    public Jornalentry() {}

    // Constructor with taskDescription
    public Jornalentry(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
