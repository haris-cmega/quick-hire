// src/main/java/com/example/quick_hire/repository/UserRepository.java
package com.example.quick_hire.repository;

import com.example.quick_hire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for User entity operations.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
