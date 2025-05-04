package com.example.quick_hire.service.impl;

import com.example.quick_hire.exception.ResourceNotFoundException;
import com.example.quick_hire.model.RefreshToken;
import com.example.quick_hire.model.User;
import com.example.quick_hire.repository.RefreshTokenRepository;
import com.example.quick_hire.repository.UserRepository;
import com.example.quick_hire.security.JwtUtils;
import com.example.quick_hire.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   UserRepository userRepository,
                                   JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        // remove any old tokens
        refreshTokenRepository.deleteByUserId(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        // generate the actual JWT refresh‐token string
        rt.setToken(jwtUtils.generateRefreshToken(user));

        return refreshTokenRepository.save(rt);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new ResourceNotFoundException("Refresh token expired; please login again");
        }
        return token;
    }

    @Override
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
