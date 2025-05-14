package com.ndev.storyGeneratorBackend.controllers;

import com.ndev.storyGeneratorBackend.services.FlaskApiService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/test")
public class TestController {
    private final FlaskApiService flaskApiService;

    public TestController(FlaskApiService flaskApiService) {
        this.flaskApiService = flaskApiService;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }
    @GetMapping("/user")
    public String userAccess() {
        return "User Content.";
    }
    @GetMapping("/flask")
    public String flaskAccess() {
        return flaskApiService.checkStatus("hey");
    }
}