package com.stockchef.stockchefback.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String nom;
    private String email;
    private String motDePasse;
    private String role; // CUISINIER, GESTIONNAIRE, MANAGER, ADMINISTRATEUR
    // private String token; // pour la réinitialisation du mot de passe, non implémenté pour cause de trop de setup côté serveur
}
