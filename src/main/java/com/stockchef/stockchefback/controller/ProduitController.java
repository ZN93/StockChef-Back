package com.stockchef.stockchefback.controller;

import com.stockchef.stockchefback.model.Produit;
import com.stockchef.stockchefback.service.ProduitService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        try {
            List<Produit> produits = produitService.getAllProduits();
            return ResponseEntity.ok(produits);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Produit>> searchProduitsByName(@PathVariable String name) {
        try {
            List<Produit> produits = produitService.searchProduitsByName(name);
            return ResponseEntity.ok(produits);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/trash/search/{name}")
    public ResponseEntity<List<Produit>> searchProduitsByNameTrash(@PathVariable String name) {
        try {
            List<Produit> produits = produitService.searchProduitsByNameTrash(name);
            return ResponseEntity.ok(produits);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        try {
            Produit produit = produitService.getProduitById(id);
            if (produit != null) {
                return ResponseEntity.ok(produit);
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        try {
            Produit createdProduit = produitService.createProduit(produit);
            if (createdProduit != null) {
                return ResponseEntity.ok(createdProduit);
            }
            return ResponseEntity.badRequest().build();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        try {
            boolean deleted = produitService.deleteProduit(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
