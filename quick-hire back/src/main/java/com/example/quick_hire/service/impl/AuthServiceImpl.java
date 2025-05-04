package com.example.quick_hire.service.impl;

import com.example.quick_hire.dto.UserLoginDTO;
import com.example.quick_hire.dto.UserRegistrationDTO;
import com.example.quick_hire.exception.InvalidCredentialsException;
import com.example.quick_hire.exception.ResourceNotFoundException;
import com.example.quick_hire.exception.UserAlreadyExistsException;
import com.example.quick_hire.mapper.UserMapper;
import com.example.quick_hire.model.RefreshToken;
import com.example.quick_hire.model.User;
import com.example.quick_hire.repository.UserRepository;
import com.example.quick_hire.security.JwtUtils;
import com.example.quick_hire.service.AuthService;
import com.example.quick_hire.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository        userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils              jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder       passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils,
                           RefreshTokenService refreshTokenService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository        = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils              = jwtUtils;
        this.refreshTokenService   = refreshTokenService;
        this.passwordEncoder       = passwordEncoder;
    }

    @Override
    public User register(UserRegistrationDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email is already taken");
        }
        User user = UserMapper.toUser(dto, passwordEncoder);
        return userRepository.save(user);
    }

    @Override
    public Map<String,String> authenticate(UserLoginDTO dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        User user = (User) auth.getPrincipal();  // now a JPA User
        String accessToken = jwtUtils.generateAccessToken(user);
        RefreshToken rt    = refreshTokenService.createRefreshToken(user.getId());

        return Map.of(
                "token",        accessToken,
                "refreshToken", rt.getToken()
        );
    }

    @Override
    public String refreshToken(String token) {
        RefreshToken stored = refreshTokenService.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));
        refreshTokenService.verifyExpiration(stored);
        // unwrap back to User
        return jwtUtils.generateAccessToken(stored.getUser());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
