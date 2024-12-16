package com.api.control_tech.controllers;

import com.api.control_tech.models.AuthResponseDto;
import com.api.control_tech.models.LoginDto;
import com.api.control_tech.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        return Optional.ofNullable(authService.login(loginDto))
                .map(token -> ResponseEntity.ok(new AuthResponseDto(token)))
                .orElseThrow(() -> new BadCredentialsException("User Not Found"));
    }
}
