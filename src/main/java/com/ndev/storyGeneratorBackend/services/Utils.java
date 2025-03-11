package com.ndev.storyGeneratorBackend.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class Utils {
    public String generateRandomCode() {
        byte[] randomBytes = new byte[48]; // 48 bytes will give a Base64 string of 64 characters
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).substring(0, 64);
    }
}
