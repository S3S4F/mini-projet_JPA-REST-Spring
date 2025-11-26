package com.example.gestiondetache.dto;

import com.example.gestiondetache.model.Priority;
import com.example.gestiondetache.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;  // ✅ Changé

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100, message = "Le titre doit contenir entre 3 et 100 caractères")
    private String title;

    private String description;

    private TaskStatus status;

    private Priority priority;

    private LocalDate dueDate;  // ✅ Changé de LocalDateTime à LocalDate
}