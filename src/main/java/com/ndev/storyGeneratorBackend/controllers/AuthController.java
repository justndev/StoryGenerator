package com.ndev.storyGeneratorBackend.controllers;

import com.ndev.storyGeneratorBackend.dtos.SignupDTO;
import com.ndev.storyGeneratorBackend.models.User;
import com.ndev.storyGeneratorBackend.services.AuthService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody User user) {
        try {
            User usr = authService.login(user);
            return ResponseEntity.ok(usr);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupDTO dto) throws MessagingException, UnsupportedEncodingException {
        try {
            authService.register(dto, "siteURL");
            return ResponseEntity.ok("success");
        } catch (AuthService.UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@Param("code") String code) {
        if (authService.verify(code)) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> check() {
            return ResponseEntity.ok("success");


    }
}