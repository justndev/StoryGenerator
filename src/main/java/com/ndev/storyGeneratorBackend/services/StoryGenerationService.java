package com.ndev.storyGeneratorBackend.services;

import org.springframework.stereotype.Service;

@Service
public class StoryGenerationService {
    public String generateStory(String bg_video_id, String text) {
        String storyPath = "";
        // magic happens here


        return storyPath + bg_video_id + text;
    }
}
