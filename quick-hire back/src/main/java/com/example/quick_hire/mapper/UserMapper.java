// src/main/java/com/example/quick_hire/mapper/UserMapper.java
package com.example.quick_hire.mapper;

import com.example.quick_hire.dto.UserRegistrationDTO;
import com.example.quick_hire.dto.UserResponseDTO;
import com.example.quick_hire.enums.UserRole;
import com.example.quick_hire.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Utility class to convert between User entity and DTOs.
 */
public class UserMapper {

    // src/main/java/com/example/quick_hire/mapper/UserMapper.java
    public static User toUser(UserRegistrationDTO dto, PasswordEncoder encoder) {
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setPassword(encoder.encode(dto.getPassword()));
        // **HERE** convert the String → enum
        u.setRole(UserRole.valueOf(dto.getRole().toUpperCase()));
        return u;
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO out = new UserResponseDTO();
        out.setId(user.getId());
        out.setUsername(user.getUsername());
        out.setEmail(user.getEmail());
        // this is safe now because user.getRole() can’t be null
        out.setRole(user.getRole().name());
        return out;
    }

}