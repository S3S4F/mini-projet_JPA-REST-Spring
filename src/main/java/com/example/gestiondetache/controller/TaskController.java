package com.example.gestiondetache.controller;


import com.example.gestiondetache.dto.MessageResponse;
import com.example.gestiondetache.dto.TaskRequest;
import com.example.gestiondetache.dto.TaskResponse;
import com.example.gestiondetache.dto.UpdateStatusRequest;
import com.example.gestiondetache.model.Priority;
import com.example.gestiondetache.model.TaskStatus;
import com.example.gestiondetache.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * POST /api/tasks - Créer une nouvelle tâche
     */
    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest request) {
        try {
            TaskResponse response = taskService.createTask(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * GET /api/tasks - Récupérer toutes les tâches de l'utilisateur connecté
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/{id} - Récupérer une tâche par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        try {
            TaskResponse response = taskService.getTaskById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * GET /api/tasks/status/{status} - Récupérer les tâches par statut
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<TaskResponse> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/priority/{priority} - Récupérer les tâches par priorité
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskResponse>> getTasksByPriority(@PathVariable Priority priority) {
        List<TaskResponse> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/overdue - Récupérer les tâches en retard
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<TaskResponse>> getOverdueTasks() {
        List<TaskResponse> tasks = taskService.getOverdueTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/search?keyword=xxx - Rechercher des tâches
     */
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(@RequestParam String keyword) {
        List<TaskResponse> tasks = taskService.searchTasks(keyword);
        return ResponseEntity.ok(tasks);
    }

    /**
     * PUT /api/tasks/{id} - Mettre à jour une tâche complète
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable String id,
                                        @Valid @RequestBody TaskRequest request) {
        try {
            TaskResponse response = taskService.updateTask(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * PATCH /api/tasks/{id}/status - Changer le statut d'une tâche
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(@PathVariable String id,
                                              @Valid @RequestBody UpdateStatusRequest request) {
        try {
            TaskResponse response = taskService.updateTaskStatus(id, request.getStatus());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * DELETE /api/tasks/{id} - Supprimer une tâche
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok(new MessageResponse("Tâche supprimée avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}