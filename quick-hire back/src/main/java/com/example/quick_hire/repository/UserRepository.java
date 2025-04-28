package com.example.quick_hire.repository;

import com.example.quick_hire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // allow lookup by username for login
    Optional<User> findByUsername(String username);

    // convenience checks
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
