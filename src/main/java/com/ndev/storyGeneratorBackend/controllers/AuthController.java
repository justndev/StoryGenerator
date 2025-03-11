package com.ndev.storyGeneratorBackend.controllers;

import com.ndev.storyGeneratorBackend.dtos.StoryRequestDTO;
import com.ndev.storyGeneratorBackend.models.User;
import com.ndev.storyGeneratorBackend.repositories.UserRepository;
import com.ndev.storyGeneratorBackend.security.JwtUtil;
import com.ndev.storyGeneratorBackend.services.AuthService;
import com.ndev.storyGeneratorBackend.services.FileProcessingServiceImpl;
import com.ndev.storyGeneratorBackend.services.FlaskApiService;
import com.ndev.storyGeneratorBackend.services.Utils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;

    @Autowired
    AuthService authService;

    @Autowired
    Utils utils;

    @Autowired
    FlaskApiService flaskApiService;
    FileProcessingServiceImpl fileProcessingService;

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }
    @PostMapping("/signup")
    public String registerUser(@RequestBody User user) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Username is already taken!";
        }

        User newUser = User.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(false)
                .verificationCode(utils.generateRandomCode())
                .password(encoder.encode(user.getPassword()))
                .build();
        userRepository.save(newUser);
        authService.sendVerificationEmail(user);
        return "User registered successfully!";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (authService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}