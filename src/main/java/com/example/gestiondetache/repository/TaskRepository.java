package com.example.gestiondetache.repository;

import com.example.gestiondetache.model.Task;
import com.example.gestiondetache.model.TaskStatus;
import com.example.gestiondetache.model.Priority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    // Trouver toutes les tâches d'un utilisateur (triées par date)
    @Query(value = "{ 'user.$id': ?0 }", sort = "{ 'createdAt': -1 }")
    List<Task> findByUserIdOrderByCreatedAtDesc(String userId);

    // Filtrer par statut
    @Query("{ 'user.$id': ?0, 'status': ?1 }")
    List<Task> findByUserIdAndStatus(String userId, TaskStatus status);

    // Filtrer par priorité
    @Query("{ 'user.$id': ?0, 'priority': ?1 }")
    List<Task> findByUserIdAndPriority(String userId, Priority priority);

    // Tâches en retard
    @Query("{ 'user.$id': ?0, 'dueDate': { $lt: ?1 }, 'status': { $ne: 'DONE' } }")
    List<Task> findOverdueTasks(String userId, LocalDateTime now);

    // Recherche par titre (insensible à la casse)
    @Query("{ 'user.$id': ?0, 'title': { $regex: ?1, $options: 'i' } }")
    List<Task> findByUserIdAndTitleContainingIgnoreCase(String userId, String keyword);

    // Compter les tâches par statut
    @Query(value = "{ 'user.$id': ?0, 'status': ?1 }", count = true)
    long countByUserIdAndStatus(String userId, TaskStatus status);
}