package com.stockchef.stockchefback.service;

import com.stockchef.stockchefback.database.ManageSQL;
import com.stockchef.stockchefback.model.Produit;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProduitService {

    private final ManageSQL manageSQL;

    public ProduitService(ManageSQL manageSQL) {
        this.manageSQL = manageSQL;
    }

    public List<Produit> getAllProduits() throws SQLException {
        String query = "SELECT * FROM produit WHERE sys_datesup IS NULL";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query);
        
        List<Produit> produits = new ArrayList<>();
        for (Map<String, Object> row : results) {
            Produit produit = new Produit();
            produit.setId(((Number) row.get("id")).longValue());
            produit.setNom((String) row.get("nom"));
            produit.setQuantite(((Number) row.get("quantite")).floatValue());
            produit.setUnite((String) row.get("unite"));
            produit.setPrixUnitaire(new BigDecimal(row.get("prixUnitaire").toString()));
            produit.setDateEntree((LocalDateTime) row.get("dateEntree"));
            produit.setDatePeremption((LocalDateTime) row.get("datePeremption"));
            produits.add(produit);
        }
        return produits;
    }

    public List<Produit> getAllProduitsTrash(Long id_utilisateur) throws SQLException {
		if (id_utilisateur == null) {
			return null;
		}
		// vérifier les permissions de l'utilisateur
		String sql = "SELECT * FROM utilisateur WHERE id = ?";
		List<Map<String, Object>> retour = manageSQL.executeSelectSql(sql, id_utilisateur);
		
		if (retour.isEmpty()) {
			return null;
		}
		
		String role = (String) retour.get(0).get("role");
		if (!role.equals("ADMINISTRATEUR") || !role.equals("MANAGER")) {
			return null;
		}
		
        String query = "SELECT * FROM produit";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query);
        
        List<Produit> produits = new ArrayList<>();
        for (Map<String, Object> row : results) {
            Produit produit = new Produit();
            produit.setId(((Number) row.get("id")).longValue());
            produit.setNom((String) row.get("nom"));
            produit.setQuantite(((Number) row.get("quantite")).floatValue());
            produit.setUnite((String) row.get("unite"));
            produit.setPrixUnitaire(new BigDecimal(row.get("prixUnitaire").toString()));
            produit.setDateEntree((LocalDateTime) row.get("dateEntree"));
            produit.setDatePeremption((LocalDateTime) row.get("datePeremption"));
            produits.add(produit);
        }
        return produits;
    }

    public Produit getProduitById(Long id) throws SQLException {
        String query = "SELECT * FROM produit WHERE id = ? AND sys_datesup IS NULL";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, id);
        
        if (results.isEmpty()) {
            System.out.println("Aucun produit trouvé pour l'ID : " + id);
            return null;
        }
        
        Map<String, Object> row = results.get(0);
        Produit produit = new Produit();
        produit.setId(((Number) row.get("id")).longValue());
        produit.setNom((String) row.get("nom"));
        produit.setQuantite(((Number) row.get("quantite")).floatValue());
        produit.setUnite((String) row.get("unite"));
        produit.setPrixUnitaire(new BigDecimal(row.get("prixUnitaire").toString()));
        produit.setDateEntree((LocalDateTime) row.get("dateEntree"));
        produit.setDatePeremption((LocalDateTime) row.get("datePeremption"));
        
        return produit;
    }
    
    public Produit getProduitTrashById(Long id, Long id_utilisateur) throws SQLException {
		if (id_utilisateur == null) {
			return null;
		}
		// vérifier les permissions de l'utilisateur
		String sql = "SELECT * FROM utilisateur WHERE id = ?";
		List<Map<String, Object>> retour = manageSQL.executeSelectSql(sql, id_utilisateur);
		
		if (retour.isEmpty()) {
			return null;
		}
		
		String role = (String) retour.get(0).get("role");
		if (!role.equals("ADMINISTRATEUR") || !role.equals("MANAGER")) {
			return null;
		}
		
        String query = "SELECT * FROM produit WHERE id = ?";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, id);
        
        if (results.isEmpty()) {
            System.out.println("Aucun produit trouvé pour l'ID : " + id);
            return null;
        }
        
        Map<String, Object> row = results.get(0);
        Produit produit = new Produit();
        produit.setId(((Number) row.get("id")).longValue());
        produit.setNom((String) row.get("nom"));
        produit.setQuantite(((Number) row.get("quantite")).floatValue());
        produit.setUnite((String) row.get("unite"));
        produit.setPrixUnitaire(new BigDecimal(row.get("prixUnitaire").toString()));
        produit.setDateEntree((LocalDateTime) row.get("dateEntree"));
        produit.setDatePeremption((LocalDateTime) row.get("datePeremption"));
        produit.setSys_datesup((LocalDateTime) row.get("sys_datesup"));
        
        return produit;
    }
    
    public List<Produit> getProduitByPeremption(LocalDateTime datePeremption) throws SQLException {
        return getProduitByPeremption(datePeremption, true);
    }
    
    public List<Produit> getProduitByPeremption(LocalDateTime datePeremption, boolean AvantPeremption) throws SQLException {
        String query;
        if (AvantPeremption) {
            query = "SELECT * FROM produit WHERE datePeremption <= ? AND sys_datesup IS NULL";
        } else {
            query = "SELECT * FROM produit WHERE datePeremption >= ? AND sys_datesup IS NULL";
        }
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, datePeremption);
        
        if (results.isEmpty()) {
            System.out.println("Aucun produit trouvé pour la date de péremption : " + datePeremption);
            return null;
        }
        
        List<Produit> produits = new ArrayList<>();
        for (Map<String, Object> row : results) {
            Produit produit = new Produit();
            produit.setId(((Number) row.get("id")).longValue());
            produit.setNom((String) row.get("nom"));
            produit.setQuantite(((Number) row.get("quantite")).floatValue());
            produit.setUnite((String) row.get("unite"));
            produit.setPrixUnitaire(new BigDecimal(row.get("prixUnitaire").toString()));
            produit.setDateEntree((LocalDateTime) row.get("dateEntree"));
            produit.setDatePeremption((LocalDateTime) row.get("datePeremption"));
            produits.add(produit);
        }
        return produits;
    }

    public Produit createProduit(Produit produit) throws SQLException {
        String query = "INSERT INTO produit (nom, quantite, unite, prixUnitaire, dateEntree, datePeremption) VALUES (?, ?, ?, ?, ?, ?)";
        
        Map<String, Object> result = manageSQL.executeInsertWithGeneratedKeys(
            query,
            produit.getNom(),
            produit.getQuantite(),
            produit.getUnite(),
            produit.getPrixUnitaire(),
            produit.getDateEntree(),
            produit.getDatePeremption()
        );
        
        if (result.containsKey("generatedKey")) {
            Long generatedId = ((Number) result.get("generatedKey")).longValue();
            return getProduitById(generatedId);
        }
        
        return null;
    }

    public boolean deleteProduit(Long id) throws SQLException {
        String query = "UPDATE produit SET sys_datesup=now() WHERE id = ? AND sys_datesup IS NULL";
        // String query = "DELETE FROM produit WHERE id = ?";
        int rowsAffected = manageSQL.executeUpdateSql(query, id);
        return rowsAffected > 0;
    }

	public List<Produit> searchProduitsByName(String name) throws SQLException {
        String query = "SELECT * FROM produit WHERE LOWER(nom) LIKE LOWER(?) AND sys_datesup IS NULL";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, "%" + name + "%");
        
        if (results.isEmpty()) {
            System.out.println("Aucun produit trouvé pour le nom : " + name);
            return null;
        }
        
        List<Produit> produits = new ArrayList<>();
        for (Map<String, Object> row : results) {
            Produit produit = new Produit();
            produit.setId(((Number) row.get("id")).longValue());
            produit.setNom((String) row.get("nom"));
            produit.setQuantite(((Number) row.get("quantite")).floatValue());
            produit.setUnite((String) row.get("unite"));
            produit.setPrixUnitaire(new BigDecimal(row.get("prixUnitaire").toString()));
            produit.setDateEntree((LocalDateTime) row.get("dateEntree"));
            produit.setDatePeremption((LocalDateTime) row.get("datePeremption"));
            produit.setSys_datesup((LocalDateTime) row.get("sys_datesup"));
            produits.add(produit);
        }
        return produits;
    }

    public List<Produit> searchProduitsByNameTrash(String name, Long id_utilisateur) throws SQLException {
		if (id_utilisateur == null) {
			return null;
		}
		// vérifier les permissions de l'utilisateur
		String sql = "SELECT * FROM utilisateur WHERE id = ?";
		List<Map<String, Object>> retour = manageSQL.executeSelectSql(sql, id_utilisateur);
		
		if (retour.isEmpty()) {
			return null;
		}
		
		String role = (String) retour.get(0).get("role");
		if (!role.equals("ADMINISTRATEUR") || !role.equals("MANAGER")) {
			return null;
		}
		
        String query = "SELECT * FROM produit WHERE LOWER(nom) LIKE LOWER(?)";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, "%" + name + "%");
        
        if (results.isEmpty()) {
            System.out.println("Aucun produit trouvé pour le nom : " + name);
            return null;
        }
        
        List<Produit> produits = new ArrayList<>();
        for (Map<String, Object> row : results) {
            Produit produit = new Produit();
            produit.setId(((Number) row.get("id")).longValue());
            produit.setNom((String) row.get("nom"));
            produit.setQuantite(((Number) row.get("quantite")).floatValue());
            produit.setUnite((String) row.get("unite"));
            produit.setPrixUnitaire(new BigDecimal(row.get("prixUnitaire").toString()));
            produit.setDateEntree((LocalDateTime) row.get("dateEntree"));
            produit.setDatePeremption((LocalDateTime) row.get("datePeremption"));
            produit.setSys_datesup((LocalDateTime) row.get("sys_datesup"));
            produits.add(produit);
        }
        return produits;
    }
    
    public boolean retablireProduit(Long id, Long id_utilisateur) throws SQLException {
		if (id_utilisateur == null) {
			return false;
		}
		// vérifier les permissions de l'utilisateur
		String sql = "SELECT * FROM utilisateur WHERE id = ?";
		List<Map<String, Object>> retour = manageSQL.executeSelectSql(sql, id_utilisateur);
		
		if (retour.isEmpty()) {
			return false;
		}
		
		String role = (String) retour.get(0).get("role");
		if (!role.equals("ADMINISTRATEUR") || !role.equals("MANAGER")) {
			return false;
		}
		
        String query = "UPDATE produit SET sys_datesup=null WHERE id = ? AND sys_datesup IS NOT NULL";
        int rowsAffected = manageSQL.executeUpdateSql(query, id);
        return rowsAffected > 0;
    }
	
	public boolean DeleteProduitDefinitivement(Long id, Long id_utilisateur) throws SQLException {
		if (id_utilisateur == null) {
			return false;
		}
		// vérifier les permissions de l'utilisateur
		String sql = "SELECT * FROM utilisateur WHERE id = ?";
		List<Map<String, Object>> retour = manageSQL.executeSelectSql(sql, id_utilisateur);
		
		if (retour.isEmpty()) {
			return false;
		}
		
		String role = (String) retour.get(0).get("role");
		if (!role.equals("ADMINISTRATEUR") || !role.equals("MANAGER")) {
			return false;
		}
		
        String query = "DELETE FROM produit WHERE id = ?";
		int rowsAffected = manageSQL.executeUpdateSql(query, id);
		return rowsAffected > 0;
	}
}
