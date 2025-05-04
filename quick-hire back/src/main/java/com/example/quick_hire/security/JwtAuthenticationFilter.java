package com.example.quick_hire.security;

import com.example.quick_hire.model.RefreshToken;
import com.example.quick_hire.service.RefreshTokenService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

/**
 * Authenticates /api/auth/login requests and issues both
 * an access JWT and a refresh JWT.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationFilter(AuthenticationManager authMgr,
                                   JwtUtils jwtUtils,
                                   RefreshTokenService refreshTokenService) {
        super(authMgr);
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            Map<String,String> creds = new ObjectMapper()
                    .readValue(req.getInputStream(), new TypeReference<>(){});

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            creds.get("username"),
                            creds.get("password")
                    );
            return this.getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse login request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth)
            throws IOException, ServletException {
        // 1) Cast principal back to your CustomUserDetails
        CustomUserDetails principal = (CustomUserDetails) auth.getPrincipal();

        // 2) Unwrap the JPA User entity
        var user = principal.getUser();

        // 3) Generate access‐token using the User
        String accessToken = jwtUtils.generateAccessToken(user);

        // 4) Create & persist a refresh‐token for that same User ID
        RefreshToken refresh = refreshTokenService
                .createRefreshToken(user.getId());

        Map<String,String> tokens = Map.of(
                "token",         accessToken,
                "refresh_token", refresh.getToken()
        );

        res.setContentType("application/json");
        new ObjectMapper().writeValue(res.getWriter(), tokens);
    }
}
