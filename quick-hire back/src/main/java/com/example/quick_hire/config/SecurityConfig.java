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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationManager authManager) throws Exception {

        // 1) your custom login filter
        JwtAuthenticationFilter authFilter =
                new JwtAuthenticationFilter(authManager, jwtUtils, refreshTokenService);
        authFilter.setFilterProcessesUrl("/api/auth/login");

        // 2) your JWT‐authorization filter needs all three constructor args
        JwtAuthorizationFilter authorizationFilter =
                new JwtAuthorizationFilter(authManager, jwtUtils, userDetailsService);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/jobs/").hasRole("CLIENT")
                        .anyRequest().authenticated()
                )
                // register both filters
                .addFilter(authFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
