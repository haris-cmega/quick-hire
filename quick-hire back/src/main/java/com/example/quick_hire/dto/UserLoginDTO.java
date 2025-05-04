// src/main/java/com/example/quick_hire/dto/UserLoginDTO.java
package com.example.quick_hire.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for user login requests.
 */
public class UserLoginDTO {
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}