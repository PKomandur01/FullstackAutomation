package com.example.automation;

import org.springframework.data.jpa.repository.JpaRepository;

// TaskRepository
// - This tells Spring Boot to create a DB layer for Task.
// - You get SQL commands like save(), findAll(), findById() for free.
public interface TaskRepository extends JpaRepository<Task, Long> {
    // You can add custom query methods here if needed
    // For example: List<Task> findByStatus(String status);
}