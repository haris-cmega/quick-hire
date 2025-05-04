// src/main/java/com/example/quick_hire/service/RefreshTokenService.java
package com.example.quick_hire.service;

import com.example.quick_hire.model.RefreshToken;

import java.util.Optional;

/**
 * Service interface for managing refresh tokens.
 */
public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteByUserId(Long userId);
}
