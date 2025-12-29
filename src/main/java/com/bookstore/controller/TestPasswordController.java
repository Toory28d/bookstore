package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestPasswordController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/check-password")
    public String checkPassword(@RequestParam String raw, @RequestParam String encoded) {
        boolean matches = passwordEncoder.matches(raw, encoded);
        return "Raw: " + raw + "\n" +
               "Encoded: " + encoded + "\n" +
               "Matches: " + matches + "\n" +
               "Encoder class: " + passwordEncoder.getClass().getName();
    }
    
    @GetMapping("/encode")
    public String encodePassword(@RequestParam String password) {
        String encoded = passwordEncoder.encode(password);
        return "Password: " + password + "\n" +
               "Encoded: " + encoded + "\n" +
               "Length: " + encoded.length();
    }
}