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
    
    @GetMapping("/admin/{id_utilisateur}")
    public ResponseEntity<List<Produit>> getAllProduitsTrash(@PathVariable Long id_utilisateur) {
        try {
            List<Produit> produits = produitService.getAllProduitsTrash(id_utilisateur);
            if (produits != null) {
                return ResponseEntity.ok(produits);
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/admin/{id_utilisateur}/{id}")
    public ResponseEntity<Produit> getProduitTrashById(@PathVariable Long id_utilisateur, @PathVariable Long id) {
        try {
            Produit produit = produitService.getProduitTrashById(id_utilisateur, id);
            if (produit != null) {
                return ResponseEntity.ok(produit);
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/admin/{id_utilisateur}/search/{search}")
    public ResponseEntity<List<Produit>> searchProduitsByNameTrash(@PathVariable Long id_utilisateur, @PathVariable String search) {
        try {
            List<Produit> produits = produitService.searchProduitsByNameTrash(search, id_utilisateur);
            if (produits != null) {
                return ResponseEntity.ok(produits);
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/admin/{id_utilisateur}/retablire/{id}")
    public ResponseEntity<Void> retablireProduit(@PathVariable Long id_utilisateur, @PathVariable Long id) {
        try {
            boolean retablire = produitService.retablireProduit(id_utilisateur, id);
            if (retablire) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("/admin/{id_utilisateur}/delete/{id}")
    public ResponseEntity<Void> DeleteProduitDefinitivement(@PathVariable Long id_utilisateur, @PathVariable Long id) {
        try {
            boolean retablire = produitService.retablireProduit(id_utilisateur, id);
            if (retablire) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
