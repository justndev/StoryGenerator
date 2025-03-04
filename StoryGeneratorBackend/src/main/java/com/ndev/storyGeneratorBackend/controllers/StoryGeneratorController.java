package com.ndev.storyGeneratorBackend.controllers;

import com.ndev.storyGeneratorBackend.dtos.StoryRequestDTO;
import com.ndev.storyGeneratorBackend.services.PythonRunner;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/story")
public class StoryGeneratorController {

    PythonRunner pythonRunner;

    @GetMapping
    public ResponseEntity<String> generateStory(HttpServletRequest rq, @RequestBody StoryRequestDTO dto) {
        String uid = "fddf-fd-df-";
        String path = pythonRunner.runPythonScript(dto.getText(), dto.getBgVideoId(), uid);
        return new ResponseEntity<>(path, HttpStatus.OK);
    }
}
