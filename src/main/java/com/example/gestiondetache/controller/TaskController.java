package com.example.gestiondetache.controller;

import com.example.gestiondetache.dto.TaskRequest;
import com.example.gestiondetache.dto.TaskResponse;
import com.example.gestiondetache.model.Priority;
import com.example.gestiondetache.model.TaskStatus;
import com.example.gestiondetache.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j // ✅ Pour les logs
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
public class TaskController {

    private final TaskService taskService;

    /**
     * GET /api/tasks - Récupérer toutes les tâches de l'utilisateur connecté
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        log.info("📋 GET /api/tasks - Récupération de toutes les tâches");
        List<TaskResponse> tasks = taskService.getAllTasks();
        log.info("✅ {} tâches récupérées", tasks.size());
        return ResponseEntity.ok(tasks);
    }

    /**
     * POST /api/tasks - Créer une nouvelle tâche
     */
    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest request) {
        try {
            log.info("📝 POST /api/tasks - Création d'une tâche: {}", request.getTitle());
            TaskResponse response = taskService.createTask(request);
            log.info("✅ Tâche créée avec ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("❌ Erreur création tâche: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * GET /api/tasks/{id} - Récupérer une tâche par son ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        try {
            log.info("🔍 GET /api/tasks/{} - Récupération d'une tâche", id);
            TaskResponse response = taskService.getTaskById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("❌ Erreur récupération tâche {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * GET /api/tasks/status/{status} - Récupérer les tâches par statut
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable TaskStatus status) {
        log.info("📊 GET /api/tasks/status/{} - Récupération par statut", status);
        List<TaskResponse> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/priority/{priority} - Récupérer les tâches par priorité
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TaskResponse>> getTasksByPriority(@PathVariable Priority priority) {
        log.info("📊 GET /api/tasks/priority/{} - Récupération par priorité", priority);
        List<TaskResponse> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/overdue - Récupérer les tâches en retard
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<TaskResponse>> getOverdueTasks() {
        log.info("⚠️ GET /api/tasks/overdue - Récupération des tâches en retard");
        List<TaskResponse> tasks = taskService.getOverdueTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * GET /api/tasks/search - Rechercher des tâches
     */
    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(@RequestParam String keyword) {
        log.info("🔍 GET /api/tasks/search?keyword={}", keyword);
        List<TaskResponse> tasks = taskService.searchTasks(keyword);
        return ResponseEntity.ok(tasks);
    }

    /**
     * PUT /api/tasks/{id} - Mettre à jour une tâche
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable String id,
            @Valid @RequestBody TaskRequest request) {
        try {
            log.info("✏️ PUT /api/tasks/{} - Mise à jour de la tâche", id);
            TaskResponse response = taskService.updateTask(id, request);
            log.info("✅ Tâche {} mise à jour", id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("❌ Erreur mise à jour tâche {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * PATCH /api/tasks/{id}/status - Changer le statut d'une tâche
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTaskStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        try {
            String statusStr = body.get("status");
            log.info("🔄 PATCH /api/tasks/{}/status - Nouveau statut: {}", id, statusStr);

            TaskStatus status = TaskStatus.valueOf(statusStr);
            TaskResponse response = taskService.updateTaskStatus(id, status);
            log.info("✅ Statut de la tâche {} changé à {}", id, status);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("❌ Statut invalide: {}", body.get("status"));
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Statut invalide"));
        } catch (RuntimeException e) {
            log.error("❌ Erreur changement statut tâche {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * DELETE /api/tasks/{id} - Supprimer une tâche
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        try {
            log.info("🗑️ DELETE /api/tasks/{} - Suppression de la tâche", id);
            taskService.deleteTask(id);
            log.info("✅ Tâche {} supprimée", id);
            return ResponseEntity.ok(Map.of("message", "Tâche supprimée avec succès"));
        } catch (RuntimeException e) {
            log.error("❌ Erreur suppression tâche {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}