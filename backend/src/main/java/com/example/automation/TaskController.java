package com.example.automation;
// ^ This tells Java what “folder/package” this file belongs to.
//   All your backend classes live in this same package so they can see each other.
import org.springframework.web.bind.annotation.*;
// ^ Importing Spring Boot annotations that help create REST API routes.
import java.util.List;
// ^ Importing List because GET /tasks returns a list of Task objects.
// -----------------------------------------------------------------------------
// TaskController = API LAYER (Top layer)
//
// This class is responsible for:
// 1. Receiving HTTP requests from the outside world (browser/Postman/frontend)
// 2. Calling the TaskService to DO the work
// 3. Returning JSON back to the client
//
// It does NOT:
// - talk to the database
// - run business logic
//
// It ONLY handles routes.
// -----------------------------------------------------------------------------
@RestController
//Every endpoint in this controller starts with /tasks.
@RequestMapping("/tasks")
// ^ This sets the BASE URL for this controller.
//   So all routes in this class will start with /tasks.
public class TaskController {

    // -------------------------------------------------------------------------
    // This is the SERVICE LAYER object.
    //
    // Why is it here?
    // - The controller should not talk to the database.
    // - The controller should not contain business logic.
    // - So it calls TaskService instead.
    //
    // Spring Boot AUTOMATICALLY creates a TaskService object for us and gives
    // it to this controller through the constructor.
    // -------------------------------------------------------------------------
    private final TaskService taskService;

    // -------------------------------------------------------------------------
    // Constructor Injection
    //
    // Spring Boot sees that this controller needs a TaskService.
    // It AUTOMATICALLY creates a TaskService object and passes it in.
    //
    // You never call "new TaskService()" yourself.
    // Spring Boot handles that (this is called Dependency Injection).
    // -------------------------------------------------------------------------
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // -------------------------------------------------------------------------
    // POST /tasks
    //
    // Creates a new task.
    //
    // @PostMapping = This method will run when someone sends a POST request
    //                to /tasks.
    //
    // @RequestBody = Take the JSON the user sends and turn it into a Task object for me.
    //
    // Flow:
    // frontend → POST /tasks → controller → service → repository → database
    //
    // The returned Task is converted BACK into JSON and sent to client.
    // -------------------------------------------------------------------------
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
        // controller → service
    }

    // -------------------------------------------------------------------------
    // GET /tasks
    //
    // Returns ALL tasks in the database.
    //
    // @GetMapping = This method runs when a GET request hits /tasks.
    //
    // It calls service.getAllTasks() which retrieves data from the database.
    //
    // Returns a List<Task> which Spring Boot automatically converts to JSON array.
    // -------------------------------------------------------------------------
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
        // controller → service → repository → database → back up
    }

    // -------------------------------------------------------------------------
    // GET /tasks/{id}
    //
    // Returns ONE task by ID.
    //
    // @PathVariable = grabs the {id} from the URL (for example: /tasks/5)
    //                 and gives it to this method.
    //
    // Calls service.getTaskById(id), which talks to the repository.
    // -------------------------------------------------------------------------
    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // -------------------------------------------------------------------------
    // PUT /tasks/{id}
    //
    // Updates an existing task.
    //
    // @PutMapping = runs when a PUT request hits /tasks/{id}.
    //
    // @RequestBody = JSON → Task object
    // @PathVariable = URL ID → Long id
    //
    // The service handles:
    // 1. checking if the task exists
    // 2. updating the fields
    // 3. saving it to the database
    //
    // Controller just forwards the data to the service.
    // -------------------------------------------------------------------------
    @PutMapping("/{id}")
    // Setting a task = to an object ID, you get to manipulate the object (Task)
    // Also passing through the updatedTask so you can change the fields
    // You set your current object into the gets of the updatedTask
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }
} 
