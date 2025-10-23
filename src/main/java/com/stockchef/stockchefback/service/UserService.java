package com.stockchef.stockchefback.service;

import com.stockchef.stockchefback.database.ManageSQL;
import com.stockchef.stockchefback.model.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final ManageSQL manageSQL;

    public UserService(ManageSQL manageSQL) {
        this.manageSQL = manageSQL;
    }

    public String getUserRole(String email, String password) throws SQLException {
        String query = "SELECT role FROM utilisateur WHERE email = ? AND motDePasse = ?";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, email, password);
        
        if (!results.isEmpty()) {
            return (String) results.get(0).get("role");
        }
        return null;
    }

    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM utilisateur WHERE email = ?";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, email);
        
        if (results.isEmpty()) {
            return null;
        }
        
        Map<String, Object> row = results.get(0);
        User user = new User();
        user.setId(((Number) row.get("id")).longValue());
        user.setNom((String) row.get("nom"));
        user.setEmail((String) row.get("email"));
        user.setRole((String) row.get("role"));
        
        return user;
    }

    public boolean hasPermission(String email, String requiredRole) {
        try {
            User user = getUserByEmail(email);
            if (user == null) return false;
            
            // Simple role-based access control (you can expand this)
            switch (requiredRole) {
                case "ADMINISTRATEUR":
                    return user.getRole().equals("ADMINISTRATEUR");
                case "MANAGER":
                    return user.getRole().equals("ADMINISTRATEUR") || 
                           user.getRole().equals("MANAGER");
                case "GESTIONNAIRE":
                    return !user.getRole().equals("CUISINIER");
                case "CUISINIER":
                    return true; // Everyone has at least CUISINIER access
                default:
                    return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
