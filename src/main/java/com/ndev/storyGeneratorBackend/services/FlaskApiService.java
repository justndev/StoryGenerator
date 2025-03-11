package com.ndev.storyGeneratorBackend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Service
public class FlaskApiService {

    private final RestTemplate restTemplate;

    public FlaskApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Calling Flask's '/generate' endpoint
    public String generateShortVideo(String text, String bgVideoId, String uid) {
        String url = "http://localhost:5000/generate"; // Flask endpoint

        // JSON payload
        String jsonPayload = String.format("{\"text\":\"%s\", \"bgVideoId\":\"%s\", \"uid\":\"%s\"}", text, bgVideoId, uid);

        // Create HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create HTTP entity with payload and headers
        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        // Make the POST request to Flask
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Return response from Flask API
        return response.getBody();
    }

    // Calling Flask's '/confirm' endpoint
    public String confirmDeletion(String uid) {
        String url = "http://localhost:5000/confirm"; // Flask endpoint

        // JSON payload
        String jsonPayload = String.format("{\"uid\":\"%s\"}", uid);

        // Create HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create HTTP entity with payload and headers
        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        // Make the POST request to Flask
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Return response from Flask API
        return response.getBody();
    }
}
