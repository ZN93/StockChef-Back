package com.stockchef.stockchefback.controller;

import com.stockchef.stockchefback.model.User;
import com.stockchef.stockchefback.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            String role = userService.getUserRole(email, password);
            if (role != null) {
                User user = userService.getUserByEmail(email);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("role", role);
                response.put("user", user);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid credentials"));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "Database error"));
        }
    }

    @GetMapping("/check-permission")
    public ResponseEntity<Map<String, Boolean>> checkPermission(
            @RequestParam String email,
            @RequestParam String requiredRole) {
        boolean hasPermission = userService.hasPermission(email, requiredRole);
        return ResponseEntity.ok(Map.of("hasPermission", hasPermission));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserInfo(@RequestParam String email) {
        try {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
