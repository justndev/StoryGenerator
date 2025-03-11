package com.ndev.storyGeneratorBackend.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileProcessingServiceImpl {
    private String basePath = "/home/n/PycharmProjects/StoryGenerator/temp_ready_videos/";

    public List<String> fileList() {
        File dir = new File(basePath);
        File[] files = dir.listFiles();

        return files != null ? Arrays.stream(files).map(i -> i.getName()).collect(Collectors.toList()) : null;
    }

    public Resource downloadFile(String fileName) {
        File dir = new File(basePath+fileName);
        try{
            if(dir.exists()){
                Resource resource = new UrlResource(dir.toURI());
                return resource;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

}