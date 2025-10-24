package com.stockchef.stockchefback.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Produit {
    private Long id;
    private String nom;
    private Float quantite;
    private String unite;
    private BigDecimal prixUnitaire;
    private LocalDateTime dateEntree;
    private LocalDateTime datePeremption;
    private LocalDateTime sys_datesup;
}
