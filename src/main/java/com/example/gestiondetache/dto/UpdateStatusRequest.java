package com.example.gestiondetache.dto;


import com.example.gestiondetache.model.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusRequest {

    @NotNull(message = "Le statut est obligatoire")
    private TaskStatus status;
}
