package com.example.gestiondetache.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")  // ← Changé
public class Task {

    @Id
    private String id;  // ← Changé de Long à String

    private String title;

    private String description;

    private TaskStatus status = TaskStatus.TODO;

    private Priority priority = Priority.MEDIUM;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime dueDate;

    @DBRef  // ← Changé
    private User user;
}