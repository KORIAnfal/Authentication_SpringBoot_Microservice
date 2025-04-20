package com.example.auth.controller;

import com.example.auth.model.User;
import com.example.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
    String email = credentials.get("email");
    String password = credentials.get("password");

    return userService.findByEmail(email).map(user -> {
        String hashedInput = Base64.getEncoder().encodeToString((password + user.getSalt()).getBytes());

        if (user.getPassword().equals(hashedInput)) {
            userService.updateLoginInfo(user);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }).orElse(ResponseEntity.status(404).body("User not found"));
}


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String email) {
        return userService.findByEmail(email).map(user -> {
            userService.updateLogoutInfo(user);
            return ResponseEntity.ok("Logout successful");
        }).orElse(ResponseEntity.status(404).body("User not found"));
    }
}