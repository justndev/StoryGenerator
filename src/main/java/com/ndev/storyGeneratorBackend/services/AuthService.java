package com.ndev.storyGeneratorBackend.services;

import com.ndev.storyGeneratorBackend.dtos.SignupDTO;
import com.ndev.storyGeneratorBackend.models.User;
import com.ndev.storyGeneratorBackend.repositories.UserRepository;
import com.ndev.storyGeneratorBackend.security.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Autowired
    JwtUtil jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;

    public User login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        User usr = userOptional.get();
        usr.setPassword(null);
        usr.setJwt(jwtUtils.generateToken(userDetails.getUsername()));
        return usr;
    }

    public void register(SignupDTO user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Error: Username is already taken!");
        }

        User newUser = User.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(false)
                .verificationCode(utils.generateRandomCode())
                .password(encoder.encode(user.getPassword()))
                .build();
        System.out.println(newUser);
        userRepository.save(newUser);
        this.sendVerificationEmail(newUser);
    }

    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }

    }

    public void sendVerificationEmail(User user)
            throws MessagingException, UnsupportedEncodingException {
        String siteURL = "https://www.storygen.xyz";
        String toAddress = user.getEmail();
        String fromAddress = "ndev.storygenerator@gmail.com";
        String senderName = "Story Generator";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Story Generator.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        String verifyURL = siteURL + "/api/auth/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }
}


