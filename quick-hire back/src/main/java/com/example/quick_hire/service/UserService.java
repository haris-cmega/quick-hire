package com.example.quick_hire.service;

import com.example.quick_hire.model.User;


import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    Optional<User> getUserById(long id);
    void deleteUserById(long id);
    List<User> getAllUsers();
}
