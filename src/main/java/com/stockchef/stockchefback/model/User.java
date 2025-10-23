package com.stockchef.stockchefback.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String nom;
    private String email;
    private String role; // CUISINIER, GESTIONNAIRE, MANAGER, ADMINISTRATEUR
}
