// src/main/java/com/example/quick_hire/service/UserService.java
package com.example.quick_hire.service;

import com.example.quick_hire.dto.UserRegistrationDTO;
import com.example.quick_hire.model.User;

import java.util.List;

/**
 * Service interface for user CRUD operations.
 */
public interface UserService {
    User createUser(UserRegistrationDTO userRegistrationDTO);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, UserRegistrationDTO userRegistrationDTO);
    void deleteUser(Long id);
}
