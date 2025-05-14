package com.ndev.storyGeneratorBackend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class FlaskApiService {
    public static String flaskIp = "http://localhost:5000/";

    private final RestTemplate restTemplate;

    public FlaskApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Calling Flask's '/generate' endpoint
    public String generateShortVideo(String text, String bgVideoId, String uid, String voice) {
        String url = flaskIp + "generate";

        // JSON payload
        String jsonPayload = String.format("{\"text\":\"%s\", \"bgVideoId\":\"%s\", \"uid\":\"%s\", \"voice\":\"%s\"}", text, bgVideoId, uid, voice);

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

    public String checkStatus(String uid) {
        String baseUrl = "http://localhost:5000/status"; // Flask endpoint

        // Construct URL with query parameter
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("uid", uid)
                .toUriString();

        // Make the GET request to Flask
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Return the response body from Flask API
        return response.getBody();
    }
}
