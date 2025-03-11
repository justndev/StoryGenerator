package com.ndev.storyGeneratorBackend.controllers;

import com.ndev.storyGeneratorBackend.dtos.StoryRequestDTO;
import com.ndev.storyGeneratorBackend.services.FileProcessingServiceImpl;
import com.ndev.storyGeneratorBackend.services.FlaskApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/story")
public class StoryGeneratorController {

    @Autowired
    FlaskApiService flaskApiService;
    @Autowired
    FileProcessingServiceImpl fileProcessingService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateStory(HttpServletRequest rq, @RequestBody StoryRequestDTO dto) {
        String uid = "fddf-fd-df-";
        String response = flaskApiService.generateShortVideo(dto.getText(), dto.getBgVideoId(), uid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(HttpServletRequest rq, @RequestParam String fileName) {
        Resource file = fileProcessingService.downloadFile(fileName);

        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(file);
        }
    }
}
