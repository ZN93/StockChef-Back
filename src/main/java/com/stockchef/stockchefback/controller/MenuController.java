package com.stockchef.stockchefback.controller;

import com.stockchef.stockchefback.model.Menu;
import com.stockchef.stockchefback.model.Produit;
import com.stockchef.stockchefback.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/menus")

public class MenuController {
	@Autowired
	private MenuService menuService;

	@GetMapping
	public ResponseEntity<List<Menu>> getAllMenus() {
		try {
			List<Menu> menus = menuService.getAllMenus();
			if (menus != null) {
				return ResponseEntity.ok(menus);
			}
			return ResponseEntity.notFound().build();
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/{id}/ingredients")
	public ResponseEntity<List<Produit>> getIngredientsByMenu(@PathVariable Long id) {
		try {
			List<Produit> menu = menuService.getIngredientsByMenu(id);
			if (menu != null) {
				return ResponseEntity.ok(menu);
			}
			return ResponseEntity.notFound().build();
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
		try {
			Menu menu = menuService.getMenuById(id);
			if (menu != null) {
				return ResponseEntity.ok(menu);
			}
			return ResponseEntity.notFound().build();
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping
	public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
		try {
			Menu createdMenu = menuService.createMenu(menu);
			if (createdMenu != null) {
				return ResponseEntity.ok(createdMenu);
			}
			return ResponseEntity.badRequest().build();
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
		try {
			boolean deleted = menuService.deleteMenu(id);
			if (deleted) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.notFound().build();
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}