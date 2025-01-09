package com.api.control_tech.services;

import com.api.control_tech.config.JwtTokenProvider;
import com.api.control_tech.models.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String login(LoginDto loginDto) {

        // AuthenticationManager is used to authenticate the user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));

        /* SecurityContextHolder is used to allows the rest of the application to know
        that the user is authenticated and can use user data from Authentication object */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate the token based on username and secret key
        String token = jwtTokenProvider.generateToken(authentication);

        // Return the token to controller
        return token;
    }

    public String getUserEmailFromToken(String token) {
        try {
            String[] parts = token.split("\\.");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid token structure");
            }

            String payload = new String(Base64.getDecoder().decode(parts[1]));

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> claims = objectMapper.readValue(payload, Map.class);

            return (String) claims.get("sub");
        } catch (Exception e) {
            throw new RuntimeException("Error decoding token: " + e.getMessage(), e);
        }
    }
}