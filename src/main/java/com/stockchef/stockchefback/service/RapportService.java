package com.stockchef.stockchefback.service;

import com.stockchef.stockchefback.database.ManageSQL;
import com.stockchef.stockchefback.model.Rapport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service

public class RapportService {
	@Autowired
	private ManageSQL manageSQL;

	public List<Rapport> getAllRapports() throws SQLException {
		String query = "SELECT * FROM rapport";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(query);
		
		if (results.isEmpty()) {
			return null;
		}
		
		List<Rapport> rapports = new ArrayList<>();
		for (Map<String, Object> row : results) {
			Rapport rapport = new Rapport();
			rapport.setId(((Number) row.get("id")).longValue());
			rapport.setDateDebut((LocalDateTime) row.get("dateDebut"));
			rapport.setDateFin((LocalDateTime) row.get("dateFin"));
			rapport.setCoutMoyenRepas(new BigDecimal(row.get("coutMoyenRepas").toString()));
			rapport.setMenusInclus((String) row.get("menusInclus"));
			rapport.setUtilisateur(((Number) row.get("utilisateur")).longValue());
			rapports.add(rapport);
		}
		return rapports;
	}

	public Rapport getRapportById(Long id) throws SQLException {
		String query = "SELECT * FROM rapport WHERE id = ?";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(query, id);
		
		if (results.isEmpty()) {
			return null;
		}
		
		Map<String, Object> row = results.get(0);
		Rapport rapport = new Rapport();
		rapport.setId(((Number) row.get("id")).longValue());
		rapport.setDateDebut((LocalDateTime) row.get("dateDebut"));
		rapport.setDateFin((LocalDateTime) row.get("dateFin"));
		rapport.setCoutMoyenRepas(new BigDecimal(row.get("coutMoyenRepas").toString()));
		rapport.setMenusInclus((String) row.get("menusInclus"));
		rapport.setUtilisateur(((Number) row.get("utilisateur")).longValue());
		return rapport;
	}

	public boolean deleteRapport(Long id) throws SQLException {
		String query = "UPDATE rapport SET sys_datesup=now() WHERE id = ?";
		int rowsAffected = manageSQL.executeUpdateSql(query, id);
		return rowsAffected > 0;
	}
}
