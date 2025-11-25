package com.example.gestiondetache.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")  // ← Changé
public class User {

    @Id  // ← MongoDB génère automatiquement un ObjectId
    private String id;  // ← Changé de Long à String

    @Indexed(unique = true)  // ← Changé
    private String username;

    @Indexed(unique = true)  // ← Changé
    private String email;

    private String password;

    private Role role = Role.USER;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @DBRef  // ← Changé (référence vers d'autres documents)
    private List<Task> tasks = new ArrayList<>();

    // Méthodes inchangées
    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setUser(null);
    }
}
