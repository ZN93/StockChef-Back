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
        String nom = credentials.get("nom");
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
			String role = "";
			if (nom != null && nom != "") {
				role = userService.getUserRole(nom, password);
			}
			else if (email != null && email != "") {
				role = userService.getUserRoleEmail(email, password);
			}
            if (role != "") {
                User user = userService.getUserByNom(nom);
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
            @RequestParam String nom,
            @RequestParam String requiredRole) {
        boolean hasPermission = userService.hasPermission(nom, requiredRole);
        return ResponseEntity.ok(Map.of("hasPermission", hasPermission));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserInfo(@RequestParam String nom) {
        try {
            User user = userService.getUserByNom(nom);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    /* BONUS: un moyen de réinitialiser le mot de passe, non demandé, demande trop de setup donc avorté
    @GetMapping("/{id}/reset/{token}")
    public ResponseEntity<Map<String, Object>> resetUserPassword(@PathVariable Long id, @PathVariable String token, @RequestParam String password) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                if (token.equals(user.getToken())) {
                    user.setToken(null);
                    userService.setToken(user, null);
                    userService.updateUserPassword(id, password);
                    return ResponseEntity.ok(Map.of("success", true, "message", "Password reset successfully"));
                } else {
                    return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid token"));
                }
            } else {
                return ResponseEntity.status(401).body(Map.of("success", false, "message", "User not found"));
            }
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "Database error"));
        }
    }
    @GetMapping("/{id}/send-reset-token")
    public ResponseEntity<Map<String, Object>> sendResetToken(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            if (user != null) {
                String token = userService.generateToken(id);
                userService.setToken(user, token);
                return ResponseEntity.ok(Map.of("success", true, "message", "Reset token sent successfully"));
            } else {
                return ResponseEntity.status(401).body(Map.of("success", false, "message", "User not found"));
            }
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body(Map.of("success", false, "message", "Database error"));
        }
    }
    // */
}
