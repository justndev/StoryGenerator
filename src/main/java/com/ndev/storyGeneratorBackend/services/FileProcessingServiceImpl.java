package com.ndev.storyGeneratorBackend.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class FileProcessingServiceImpl {
    private String altPath = "/home/ubuntu/storygen/StoryGeneratorFlaskScriptApi/temp_ready_videos/";

    private String basePath = "/home/ndev/Desktop/projects/StoryGeneratorFlaskScriptApi/temp_ready_videos/";

    public List<String> fileList() {
        File dir = new File(altPath);
        File[] files = dir.listFiles();

        return files != null ? Arrays.stream(files).map(i -> i.getName()).collect(Collectors.toList()) : null;
    }

    public Resource downloadFile(String fileName) {
        try{
            String normalized_path = Paths.get(fileName).normalize().toString();

            File file = new File(altPath, normalized_path);

            if (file.getCanonicalPath().startsWith(altPath)) {
                File dir = new File(altPath+fileName);
                if(dir.exists()){
                    Resource resource = new UrlResource(dir.toURI());
                    return resource;
                }
            } else {
                throw new Exception();
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

}