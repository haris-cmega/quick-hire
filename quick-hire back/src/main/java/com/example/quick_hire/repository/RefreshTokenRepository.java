// src/main/java/com/example/quick_hire/repository/RefreshTokenRepository.java
package com.example.quick_hire.repository;

import com.example.quick_hire.model.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for RefreshToken entity operations.
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    void deleteByUserId(Long userId);
}
