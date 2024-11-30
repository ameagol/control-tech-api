package com.api.control_tech.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api/")
public class UserController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("Hello Admin");
    }

    @PreAuthorize("hasRole('USER', 'ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<String> helloUser(){
        return ResponseEntity.ok("Hello User");
    }
}
