// src/main/java/com/example/quick_hire/controller/UserController.java
package com.example.quick_hire.controller;

import com.example.quick_hire.dto.UserRegistrationDTO;
import com.example.quick_hire.dto.UserResponseDTO;
import com.example.quick_hire.model.User;
import com.example.quick_hire.mapper.UserMapper;
import com.example.quick_hire.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        User createdUser = userService.createUser(userRegistrationDTO);
        UserResponseDTO responseDTO = UserMapper.toUserResponseDTO(createdUser);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponseDTO responseDTO = UserMapper.toUserResponseDTO(user);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> responseDTOs = users.stream()
                .map(UserMapper::toUserResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        User updatedUser = userService.updateUser(id, userRegistrationDTO);
        UserResponseDTO responseDTO = UserMapper.toUserResponseDTO(updatedUser);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
