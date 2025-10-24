package com.stockchef.stockchefback.controller;

import com.stockchef.stockchefback.model.Rapport;
import com.stockchef.stockchefback.service.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/rapports")

public class RapportController {
	private final RapportService rapportService;

	public RapportController(RapportService rapportService) {
		this.rapportService = rapportService;
	}

	@GetMapping
	public ResponseEntity<List<Rapport>> getAllRapports() {
		try {
			List<Rapport> rapports = rapportService.getAllRapports();
			return ResponseEntity.ok(rapports);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Rapport> getRapportById(@PathVariable Long id) {
		try {
			Rapport rapport = rapportService.getRapportById(id);
			if (rapport != null) {
				return ResponseEntity.ok(rapport);
			}
			return ResponseEntity.notFound().build();
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	@GetMapping("/search/{name}")
	public ResponseEntity<List<Rapport>> searchRapportsByName(@PathVariable String name) {
		try {
			List<Rapport> rapports = rapportService.searchRapportsByName(name);
			return ResponseEntity.ok(rapports);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}	
	@PostMapping
	public ResponseEntity<Rapport> createRapport(@RequestBody Rapport rapport) {
		try {
			Rapport newRapport = rapportService.createRapport(rapport);
			return ResponseEntity.ok(newRapport);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Rapport> updateRapport(@RequestBody Rapport rapport) {
		try {
			Rapport newRapport = rapportService.updateRapport(rapport);
			return ResponseEntity.ok(newRapport);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Boolean> deleteRapport(@RequestBody Rapport rapport) {
		try {
			boolean deleted = rapportService.deleteRapport(rapport);
			return ResponseEntity.ok(deleted);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
