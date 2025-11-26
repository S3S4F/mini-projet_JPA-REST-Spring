package com.example.gestiondetache.repository;

import com.example.gestiondetache.model.Priority;
import com.example.gestiondetache.model.Task;
import com.example.gestiondetache.model.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;  // ✅ Changé de LocalDateTime à LocalDate
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    // Trouver toutes les tâches d'un utilisateur, triées par date de création
    List<Task> findByUserIdOrderByCreatedAtDesc(String userId);

    // Trouver les tâches d'un utilisateur par statut
    List<Task> findByUserIdAndStatus(String userId, TaskStatus status);

    // Trouver les tâches d'un utilisateur par priorité
    List<Task> findByUserIdAndPriority(String userId, Priority priority);

    // Trouver les tâches en retard (dueDate passée et statut != DONE)
    @Query("{ 'user.$id': ?0, 'dueDate': { $lt: ?1 }, 'status': { $ne: 'DONE' } }")
    List<Task> findOverdueTasks(String userId, LocalDate currentDate);  // ✅ Changé à LocalDate

    // Rechercher des tâches par mot-clé dans le titre
    List<Task> findByUserIdAndTitleContainingIgnoreCase(String userId, String keyword);
}