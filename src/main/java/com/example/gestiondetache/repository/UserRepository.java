package com.example.gestiondetache.repository;

import com.example.gestiondetache.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;  // ← Changé

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {  // ← String au lieu de Long
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}