package com.example.gestiondetache.service;


import com.example.gestiondetache.dto.TaskRequest;
import com.example.gestiondetache.dto.TaskResponse;
import com.example.gestiondetache.model.Priority;
import com.example.gestiondetache.model.Task;
import com.example.gestiondetache.model.TaskStatus;
import com.example.gestiondetache.model.User;
import com.example.gestiondetache.repository.TaskRepository;
import com.example.gestiondetache.repository.UserRepository;
import com.example.gestiondetache.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    /**
     * Récupère l'utilisateur connecté
     */
    private User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    /**
     * Créer une nouvelle tâche
     */
    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        User currentUser = getCurrentUser();

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO);
        task.setPriority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM);
        task.setDueDate(request.getDueDate());
        task.setUser(currentUser);

        Task savedTask = taskRepository.save(task);
        return new TaskResponse(savedTask);
    }

    /**
     * Récupérer toutes les tâches de l'utilisateur connecté
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        User currentUser = getCurrentUser();
        List<Task> tasks = taskRepository.findByUserIdOrderByCreatedAtDesc(currentUser.getId());
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer une tâche par son ID
     */
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(String taskId) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

        // Vérifier que la tâche appartient à l'utilisateur connecté
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Accès non autorisé à cette tâche");
        }

        return new TaskResponse(task);
    }

    /**
     * Récupérer les tâches par statut
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        User currentUser = getCurrentUser();
        List<Task> tasks = taskRepository.findByUserIdAndStatus(currentUser.getId(), status);
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les tâches par priorité
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByPriority(Priority priority) {
        User currentUser = getCurrentUser();
        List<Task> tasks = taskRepository.findByUserIdAndPriority(currentUser.getId(), priority);
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les tâches en retard
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getOverdueTasks() {
        User currentUser = getCurrentUser();
        List<Task> tasks = taskRepository.findOverdueTasks(currentUser.getId(), LocalDateTime.now());
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Mettre à jour une tâche
     */
    @Transactional
    public TaskResponse updateTask(String taskId, TaskRequest request) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

        // Vérifier que la tâche appartient à l'utilisateur connecté
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Accès non autorisé à cette tâche");
        }

        // Mettre à jour les champs
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        task.setDueDate(request.getDueDate());

        Task updatedTask = taskRepository.save(task);
        return new TaskResponse(updatedTask);
    }

    /**
     * Changer le statut d'une tâche
     */
    @Transactional
    public TaskResponse updateTaskStatus(String taskId, TaskStatus newStatus) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

        // Vérifier que la tâche appartient à l'utilisateur connecté
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Accès non autorisé à cette tâche");
        }

        task.setStatus(newStatus);
        Task updatedTask = taskRepository.save(task);
        return new TaskResponse(updatedTask);
    }

    /**
     * Supprimer une tâche
     */
    @Transactional
    public void deleteTask(String taskId) {
        User currentUser = getCurrentUser();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

        // Vérifier que la tâche appartient à l'utilisateur connecté
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Accès non autorisé à cette tâche");
        }

        taskRepository.delete(task);
    }

    /**
     * Rechercher des tâches par mot-clé dans le titre
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> searchTasks(String keyword) {
        User currentUser = getCurrentUser();
        List<Task> tasks = taskRepository.findByUserIdAndTitleContainingIgnoreCase(
                currentUser.getId(),
                keyword
        );
        return tasks.stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }
}
