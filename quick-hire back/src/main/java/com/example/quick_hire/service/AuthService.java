// src/main/java/com/example/quick_hire/service/AuthService.java
package com.example.quick_hire.service;

import com.example.quick_hire.dto.UserLoginDTO;
import com.example.quick_hire.dto.UserRegistrationDTO;
import com.example.quick_hire.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface AuthService {
    User register(UserRegistrationDTO userRegistrationDTO);
    Map<String, String> authenticate(UserLoginDTO userLoginDTO);
    String refreshToken(String refreshToken);
    UserDetails loadUserByUsername(String username);
}
