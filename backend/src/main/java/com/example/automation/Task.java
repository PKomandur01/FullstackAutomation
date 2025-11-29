package com.example.automation; 
// This tells Java what "package" (folder group) this file belongs to.
// Spring Boot finds classes based on package structure so this MUST match your folder layout.

import jakarta.persistence.*; 
// This imports JPA (Java Persistence API) annotations like @Entity, @Id, @Column.
// JPA is what turns your Java class into a real database table.

import java.time.LocalDateTime; 
// Import Java’s date/time class to store timestamps (due dates, creation dates, etc.)


// ---------------------------------------------------------------------------
// @Entity tells Spring Boot + JPA:
// "This class should become a table in the database."
// Each Task object becomes a *row* in that table.
// Each variable below becomes a *column*.
// ---------------------------------------------------------------------------
@Entity
public class Task {

    // @Id marks this field as the PRIMARY KEY of the table.
    // Every row needs a unique identifier.
    @Id
    // @GeneratedValue tells the database to auto-increment the ID.
    // Example: 1, 2, 3, 4...
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // These are basic columns in the Task table.
    // JPA sees simple types like String → creates VARCHAR columns.
    private String title;        // Column: title
    private String description;  // Column: description

    // @Enumerated tells JPA how to store enum values.
    // STRING means it stores "LOW", "MEDIUM", "HIGH" instead of numbers 0,1,2.
    @Enumerated(EnumType.STRING)
    private Priority priority;   // Column: priority

    @Enumerated(EnumType.STRING)
    private Status status;       // Column: status


    // Dates for due date + automatic timestamps.
    private LocalDateTime dueDate;    // When the task must be done
    private LocalDateTime createdAt;  // When the task was first created
    private LocalDateTime updatedAt;  // Last time the task was modified


    // ---------------------------------------------------------------------------
    // @PrePersist runs ONLY when a task is being created for the first time.
    //
    // Why?
    // Because we want the database row to AUTOMATICALLY have timestamps.
    //
    // When you call service.createTask(), before saving to DB,
    // this method fills in createdAt and updatedAt.
    // ---------------------------------------------------------------------------
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }


    // ---------------------------------------------------------------------------
    // @PreUpdate runs ONLY when an existing task is being updated.
    //
    // Why?
    // We want updatedAt to change every time someone modifies the task.
    //
    // When service.updateTask() is called → before saving → updatedAt refreshes.
    // ---------------------------------------------------------------------------
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    // ---------------------------------------------------------------------------
    // Two enums:
    // Enums are "fixed options" the variable can be.
    // These become VARCHAR columns in the database.
    //
    // Example:
    // priority = HIGH
    // status = ESCALATED
    // ---------------------------------------------------------------------------
    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum Status {
        TODO,
        IN_PROGRESS,
        DONE,
        ESCALATED
    }


    // ---------------------------------------------------------------------------
    // Getters and Setters
    //
    // These allow the REST API layer to RECEIVE and RETURN data properly.
    // Spring Boot uses these methods when converting JSON → Java and Java → JSON.
    //
    // Example:
    // JSON input:
    // { "title": "Do homework", "priority": "HIGH" }
    //
    // Spring calls setTitle("Do homework") behind the scenes.
    // ---------------------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
