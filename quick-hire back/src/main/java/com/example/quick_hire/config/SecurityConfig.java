// src/main/java/com/example/quick_hire/config/SecurityConfig.java
package com.example.quick_hire.config;

import com.example.quick_hire.security.JwtAuthenticationFilter;
import com.example.quick_hire.security.JwtAuthorizationFilter;
import com.example.quick_hire.security.JwtUtils;
import com.example.quick_hire.service.impl.RefreshTokenServiceImpl;
import com.example.quick_hire.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtUtils jwtUtils,
                          RefreshTokenServiceImpl refreshTokenService,
                          UserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)
            throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationManager authMgr) throws Exception {

        JwtAuthenticationFilter authFilter =
                new JwtAuthenticationFilter(authMgr, jwtUtils, refreshTokenService);
        authFilter.setFilterProcessesUrl("/api/auth/login");

        JwtAuthorizationFilter authorizationFilter =
                new JwtAuthorizationFilter(authMgr, jwtUtils, userDetailsService);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // auth endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        // jobs: clients only for mutating operations
                        .requestMatchers(HttpMethod.POST,   "/api/jobs").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.PUT,    "/api/jobs/**").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/jobs/**").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.POST, "/api/proposals").hasRole("FREELANCER")
                        .requestMatchers(HttpMethod.POST,   "/api/jobs/*/proposals").hasRole("FREELANCER")
                        .requestMatchers(HttpMethod.GET,    "/api/jobs/*/proposals").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET,    "/api/proposals").hasRole("FREELANCER")
                        .requestMatchers(HttpMethod.PATCH,  "/api/proposals/*").hasRole("CLIENT")
                        // any authenticated user may view
                        .requestMatchers(HttpMethod.GET,    "/api/jobs/**").authenticated()
                        // everything else must be authenticated
                        .anyRequest().authenticated()
                )
                .addFilter(authFilter)
                .addFilterBefore(authorizationFilter,
                        JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
