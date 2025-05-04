// src/main/java/com/example/quick_hire/controller/AuthController.java
package com.example.quick_hire.controller;

import com.example.quick_hire.dto.UserLoginDTO;
import com.example.quick_hire.dto.UserRegistrationDTO;
import com.example.quick_hire.dto.UserResponseDTO;
import com.example.quick_hire.mapper.UserMapper;
import com.example.quick_hire.model.User;
import com.example.quick_hire.service.impl.AuthServiceImpl;
import com.example.quick_hire.service.impl.RefreshTokenServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl    authService;
    private final RefreshTokenServiceImpl refreshService;

    public AuthController(AuthServiceImpl authService,
                          RefreshTokenServiceImpl refreshService) {
        this.authService   = authService;
        this.refreshService = refreshService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @RequestBody @Valid UserRegistrationDTO dto) {
        User u = authService.register(dto);
        return ResponseEntity.ok(UserMapper.toUserResponseDTO(u));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(
            @RequestBody @Valid UserLoginDTO dto) {
        Map<String,String> tokens = authService.authenticate(dto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String,String>> refresh(
            @RequestBody Map<String,String> body) {
        String rt = body.get("refreshToken");
        String token = authService.refreshToken(rt);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
