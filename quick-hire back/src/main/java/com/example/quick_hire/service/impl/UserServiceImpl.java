// src/main/java/com/example/quick_hire/service/impl/UserServiceImpl.java
package com.example.quick_hire.service.impl;

import com.example.quick_hire.dto.UserRegistrationDTO;
import com.example.quick_hire.exception.ResourceNotFoundException;
import com.example.quick_hire.mapper.UserMapper;
import com.example.quick_hire.model.User;
import com.example.quick_hire.repository.UserRepository;
import com.example.quick_hire.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for user CRUD operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserRegistrationDTO userRegistrationDTO) {
        User user = UserMapper.toUser(userRegistrationDTO, passwordEncoder);
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, UserRegistrationDTO userRegistrationDTO) {
        User existingUser = getUserById(id);
        existingUser.setUsername(userRegistrationDTO.getUsername());
        existingUser.setEmail(userRegistrationDTO.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        // Role remains unchanged on update
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = getUserById(id);
        userRepository.delete(existingUser);
    }
}
