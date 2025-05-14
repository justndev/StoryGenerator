package com.ndev.storyGeneratorBackend.controllers;

import com.ndev.storyGeneratorBackend.dtos.StoryRequestDTO;
import com.ndev.storyGeneratorBackend.services.FileProcessingServiceImpl;
import com.ndev.storyGeneratorBackend.services.FlaskApiService;
import com.ndev.storyGeneratorBackend.services.Utils;
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
    @Autowired
    Utils utils;

    @PostMapping("/generate")
    public ResponseEntity<String> generateStory(HttpServletRequest rq, @RequestBody StoryRequestDTO dto) {
        System.out.println(dto);
        try {
            String uid = utils.generateRandomCode();
            String response = flaskApiService.generateShortVideo(dto.getText(), dto.getBgVideoId(), uid, dto.getVoice());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>("Test", HttpStatus.OK);

    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus(HttpServletRequest rq, @RequestParam String uid) {
        String response = flaskApiService.checkStatus(uid);
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
