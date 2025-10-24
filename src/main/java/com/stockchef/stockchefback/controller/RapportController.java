package com.stockchef.stockchefback.controller;

import com.stockchef.stockchefback.model.Rapport;
import com.stockchef.stockchefback.service.RapportService;
// import org.springframework.beans.factory.annotation.Autowired;
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
	@GetMapping("/search/debut/{dateDebut}")
	public ResponseEntity<List<Rapport>> searchRapportsByDateD(@PathVariable String dateDebut) {
		try {
			List<Rapport> rapports = rapportService.searchRapportsByDate(dateDebut, "");
			return ResponseEntity.ok(rapports);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}	
	@GetMapping("/search/fin/{dateFin}")
	public ResponseEntity<List<Rapport>> searchRapportsByDateF(@PathVariable String dateFin) {
		try {
			List<Rapport> rapports = rapportService.searchRapportsByDate("", dateFin);
			return ResponseEntity.ok(rapports);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Boolean> createRapport(@RequestBody Rapport rapport) {
		try {
			Boolean newRapport = rapportService.createRapport(rapport);
			return ResponseEntity.ok(newRapport);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Boolean> updateRapport(@RequestBody Rapport rapport) {
		try {
			Boolean newRapport = rapportService.updateRapport(rapport);
			return ResponseEntity.ok(newRapport);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Boolean> deleteRapport(@PathVariable Long id) {
		try {
			boolean deleted = rapportService.deleteRapport(id);
			return ResponseEntity.ok(deleted);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/admin/{id_utilisateur}")
	public ResponseEntity<List<Rapport>> getAllRapportTrash(@PathVariable Long id_utilisateur) {
		try {
			List<Rapport> rapports = rapportService.getAllRapportsTrash(id_utilisateur);
			return ResponseEntity.ok(rapports);
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
	@GetMapping("/admin/{id_utilisateur}/{id}")
	public ResponseEntity<Rapport> getRapportTrash(@PathVariable Long id_utilisateur, @PathVariable Long id) {
		try {
			Rapport rapport = rapportService.getRapportTrashById(id_utilisateur, id);
			if (rapport != null) {
				return ResponseEntity.ok(rapport);
			}
			return ResponseEntity.notFound().build();
		} catch (SQLException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
    
    @PostMapping("/admin/{id_utilisateur}/retablire/{id}")
    public ResponseEntity<Void> retablireProduit(@PathVariable Long id_utilisateur, @PathVariable Long id) {
        try {
            boolean retablire = rapportService.retablireRapport(id_utilisateur, id);
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
            boolean retablire = rapportService.DeleteRapportDefinitivement(id_utilisateur, id);
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
