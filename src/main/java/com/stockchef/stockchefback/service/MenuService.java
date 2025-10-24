package com.stockchef.stockchefback.service;

import com.stockchef.stockchefback.database.ManageSQL;
import com.stockchef.stockchefback.model.Menu;
import com.stockchef.stockchefback.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MenuService {
    @Autowired
    private ManageSQL manageSQL;

    public List<Menu> getAllMenus() throws SQLException {
        String query = "SELECT * FROM menu WHERE sys_datesup IS NULL";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query);
        
        if (results.isEmpty()) {
            return null;
        }
        
        List<Menu> menus = new ArrayList<>();
        for (Map<String, Object> row : results) {
            Menu menu = new Menu();
            menu.setId(((Number) row.get("id")).longValue());
            menu.setNom((String) row.get("nom"));
            menu.setDateMenu((LocalDateTime) row.get("dateMenu"));
            menu.setIngredients((String) row.get("ingredients"));
            menu.setCoutTotal(new BigDecimal(row.get("coutTotal").toString()));
            menus.add(menu);
        }
        return menus;
    }

    public Menu getMenuById(Long id) throws SQLException {
        String query = "SELECT * FROM menu WHERE id = ? AND sys_datesup IS NULL";
        List<Map<String, Object>> results = manageSQL.executeSelectSql(query, id);
        
        if (results.isEmpty()) {
            System.out.println("Aucun menu trouvé pour l'ID : " + id);
            return null;
        }
        
        Map<String, Object> row = results.get(0);
        Menu menu = new Menu();
        menu.setId(((Number) row.get("id")).longValue());
        menu.setNom((String) row.get("nom"));
        menu.setDateMenu((LocalDateTime) row.get("dateMenu"));
        menu.setIngredients((String) row.get("ingredients"));
        menu.setCoutTotal(new BigDecimal(row.get("coutTotal").toString()));
        
        return menu;
    }
	
	public List<Produit> getIngredientsByMenu(Long id) throws SQLException {
		String query = "SELECT * FROM ingredientmenu WHERE menu = ? AND sys_datesup IS NULL";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(query, id);
		
		if (results.isEmpty()) {
			System.out.println("Aucun ingredient trouvé pour le menu : " + id);
			return null;
		}
		
		List<Produit> produits = new ArrayList<>();
		for (Map<String, Object> row : results) {
			Produit produit = new Produit();
			produit.setId(((Number) row.get("id")).longValue());
			produit.setNom((String) row.get("produit"));
			produit.setQuantite(((Number) row.get("quantiteUtilisee")).floatValue());
			produit.setUnite((String) row.get("unite"));
			produits.add(produit);
		}
		return produits;
	}
	
	public Menu createMenu(Menu menu) throws SQLException {
		String query = "INSERT INTO menu (nom, dateMenu, ingredients, coutTotal) VALUES (?, ?, ?, ?)";
		
		Map<String, Object> result = manageSQL.executeInsertWithGeneratedKeys(
			query,
			menu.getNom(),
			menu.getDateMenu(),
			menu.getIngredients(),
			menu.getCoutTotal()
		);
		
		if (result.containsKey("generatedKey")) {
			Long generatedId = ((Number) result.get("generatedKey")).longValue();
			return getMenuById(generatedId);
		}
		
		return null;
	}

	public boolean deleteMenu(Long id) throws SQLException {
		String query = "UPDATE menu SET sys_datesup=now() WHERE id = ? AND sys_datesup IS NULL";
		int rowsAffected = manageSQL.executeUpdateSql(query, id);
		return rowsAffected > 0;
	}

	public List<Menu> searchMenusByName(String name) throws SQLException {
		String query = "SELECT * FROM menu WHERE LOWER(nom) LIKE LOWER(?) AND sys_datesup IS NULL";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(query, "%" + name + "%");
		
		if (results.isEmpty()) {
			System.out.println("Aucun menu trouvé pour le nom : " + name);
			return null;
		}
		
		List<Menu> menus = new ArrayList<>();
		for (Map<String, Object> row : results) {
			Menu menu = new Menu();
			menu.setId(((Number) row.get("id")).longValue());
			menu.setNom((String) row.get("nom"));
			menu.setDateMenu((LocalDateTime) row.get("dateMenu"));
			menu.setIngredients((String) row.get("ingredients"));
			menu.setCoutTotal(new BigDecimal(row.get("coutTotal").toString()));
			menus.add(menu);
		}
		return menus;
	}

	public List<Menu> searchMenusByNameTrash(String name) throws SQLException {
		String query = "SELECT * FROM menu WHERE LOWER(nom) LIKE LOWER(?) AND sys_datesup IS NOT NULL";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(query, "%" + name + "%");
		
		if (results.isEmpty()) {
			System.out.println("Aucun menu trouvé pour le nom : " + name);
			return null;
		}
		
		List<Menu> menus = new ArrayList<>();
		for (Map<String, Object> row : results) {
			Menu menu = new Menu();
			menu.setId(((Number) row.get("id")).longValue());
			menu.setNom((String) row.get("nom"));
			menu.setDateMenu((LocalDateTime) row.get("dateMenu"));
			menu.setIngredients((String) row.get("ingredients"));
			menu.setCoutTotal(new BigDecimal(row.get("coutTotal").toString()));
			menus.add(menu);
		}
		return menus;
	}
}
