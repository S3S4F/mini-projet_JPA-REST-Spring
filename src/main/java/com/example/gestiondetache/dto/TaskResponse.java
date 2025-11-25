package com.example.gestiondetache.dto;



import com.example.gestiondetache.model.Priority;
import com.example.gestiondetache.model.Task;
import com.example.gestiondetache.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private String id;      // ← Changé
    private String userId;  // ← Changé
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;
    private String username;

    // Constructeur pour convertir une Task en TaskResponse
    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
        this.dueDate = task.getDueDate();
        this.userId = task.getUser().getId();
        this.username = task.getUser().getUsername();
    }
}
