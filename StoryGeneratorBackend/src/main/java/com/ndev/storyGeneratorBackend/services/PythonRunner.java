package com.ndev.storyGeneratorBackend.services;

import java.io.*;

public class PythonRunner {
    public String runPythonScript(String bgVideoId, String text, String uid) {
        try {
            String pythonScriptPath = "StoryGenerator/main.py";

            ProcessBuilder pb = new ProcessBuilder(
                    "python", pythonScriptPath,
                    text,
                    bgVideoId,
                    uid
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for process to finish
            int exitCode = process.waitFor();
            System.out.println("Python script exited with code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "StoryGenerator/temp_ready_videos/" + uid + ".mp4";
    }
}
