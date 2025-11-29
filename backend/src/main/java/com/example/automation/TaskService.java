package com.example.automation;

import org.springframework.stereotype.Service;
import java.util.List;

// -------------------------------------------------------------
// TaskService = "THE BRAIN" of all task operations.
//
// Why this class exists:
// - The controller should NOT talk to the database directly.
// - The repository should NOT contain business logic.
// - So this service sits in the middle and does the real work.
//
// Flow:
// Controller → TaskService → TaskRepository → Database
//
// The controller calls these functions when the user hits API routes.
// This service then uses TaskRepository to save/get/update tasks.
// -------------------------------------------------------------
@Service
public class TaskService {

    // -------------------------------------------
    // This is the connection to the database layer.
    // Spring automatically creates a TaskRepository object
    // and gives it to us here.
    // -------------------------------------------
    private final TaskRepository taskRepository;

    // Constructor injection:
    // Spring Boot gives us a TaskRepository when starting the app.
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // -----------------------------------------------------
    // CREATE a task
    //
    // Called when someone sends:
    // POST /tasks with JSON {
    //   "title": "...",
    //   "priority": "HIGH"
    // }
    //
    // This method sends it to the database by calling save().
    // -----------------------------------------------------
    public Task createTask(Task task) {
        return taskRepository.save(task); // INSERT into DB
    }

    // -----------------------------------------------------
    // GET ALL tasks
    //
    // Called when someone hits:
    // GET /tasks
    //
    // findAll() is a built-in JPA function from JpaRepository.
    // -----------------------------------------------------
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // SELECT * FROM tasks
    }

    // -----------------------------------------------------
    // GET ONE task by ID
    //
    // Called when someone hits:
    // GET /tasks/{id}
    //
    // If the task does not exist, return null.
    // -----------------------------------------------------
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // -----------------------------------------------------
    // UPDATE a task
    //
    // Called when someone hits:
    // PUT /tasks/{id} with updated JSON fields.
    //
    // Steps:
    // 1. Get the existing task from DB
    // 2. Replace fields with new ones
    // 3. Save back to DB
    //
    // save(existing) will perform an UPDATE.
    // -----------------------------------------------------
    public Task updateTask(Long id, Task updatedTask) {
        // Step 1: find the old task
        // Existing becomes that old ID with those fields and values
        // You aren't making a copy. You're getting the actual object from DB.
        Task existing = getTaskById(id);
        if (existing == null) return null; // task not found

        // Step 2: change fields to match updatedTask
        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setPriority(updatedTask.getPriority());
        existing.setStatus(updatedTask.getStatus());
        existing.setDueDate(updatedTask.getDueDate());

        // Step 3: save (UPDATE)
        return taskRepository.save(existing);
    }
}
