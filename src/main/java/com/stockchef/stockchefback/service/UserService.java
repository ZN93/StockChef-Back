package com.stockchef.stockchefback.service;

import com.stockchef.stockchefback.database.ManageSQL;
import com.stockchef.stockchefback.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final ManageSQL manageSQL;
    private final PasswordEncoder passwordEncoder;

    public UserService(ManageSQL manageSQL) {
        this.manageSQL = manageSQL;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String getUserRole(String nom, String password) throws SQLException {
        // First get the user's hashed password and role from the database
        String query = "SELECT motDePasse, role FROM utilisateur WHERE nom = ?";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, nom);
        
        if (!results.isEmpty()) {
            System.out.println("User found");
            String hashedPassword = (String) results.get(0).get("motDePasse");
            // Verify the provided password against the stored hash
            if (passwordEncoder.matches(password, hashedPassword)) {
                return (String) results.get(0).get("role");
            }
        }
        System.out.println("User not found or invalid credentials");
        return null;
    }

    public String getUserRoleEmail(String email, String password) throws SQLException {
        // First get the user's hashed password and role from the database
        String query = "SELECT motDePasse, role FROM utilisateur WHERE email = ?";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, email);
        
        if (!results.isEmpty()) {
            System.out.println("User found");
            String hashedPassword = (String) results.get(0).get("motDePasse");
            // Verify the provided password against the stored hash
            if (passwordEncoder.matches(password, hashedPassword)) {
                return (String) results.get(0).get("role");
            }
        }
        System.out.println("User not found or invalid credentials");
        return null;
    }

    public User getUserByNom(String nom) throws SQLException {
        String query = "SELECT * FROM utilisateur WHERE nom = ?";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, nom);
        
        if (results.isEmpty()) {
            return null;
        }
		
		if (results.size() > 1) {
			System.out.println("Multiple users found with the same name");
            throw new SQLException("Multiple users found with the same name");
        }
        
        Map<String, Object> row = results.get(0);
        User user = new User();
        user.setId(((Number) row.get("id")).longValue());
        user.setNom((String) row.get("nom"));
        user.setEmail((String) row.get("email"));
        user.setRole((String) row.get("role"));
        
        return user;
    }

    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM utilisateur WHERE email = ?";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, email);
        
        if (results.isEmpty()) {
            return null;
        }
		
		if (results.size() > 1) {
			System.out.println("Multiple users found with the same name");
            throw new SQLException("Multiple users found with the same name");
        }
        
        Map<String, Object> row = results.get(0);
        User user = new User();
        user.setId(((Number) row.get("id")).longValue());
        user.setNom((String) row.get("nom"));
        user.setEmail((String) row.get("email"));
        user.setRole((String) row.get("role"));
        
        return user;
    }
	
    public boolean hasPermission(String nom, String requiredRole) {
        try {
            User user = getUserByNom(nom);
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
	
    public boolean hasPermissionEmail(String email, String requiredRole) {
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
	
	public boolean Login(String nom, String password) {
		String query = "SELECT * FROM utilisateur WHERE nom = ? AND motDePasse = ?";
		List<Map<String, Object>> results;
		try {
			results = manageSQL.executeSelectSql(query, nom, password);
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
		
		if (results.isEmpty()) {
			return false;
		}
		return true;
	}
	public boolean LoginEmail(String email, String password) {
		String query = "SELECT * FROM utilisateur WHERE email = ? AND motDePasse = ?";
		List<Map<String, Object>> results;
		try {
			results = manageSQL.executeSelectSql(query, email, password);
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			return false;
		}
		
		if (results.isEmpty()) {
			return false;
		}
		return true;
	}
}
