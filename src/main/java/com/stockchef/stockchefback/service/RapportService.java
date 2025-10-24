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
		String query = "SELECT * FROM rapport WHERE sys_datesup IS NULL";
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

	public List<Rapport> getAllRapportsTrash(Long id_utilisateur) throws SQLException {
		if (id_utilisateur == null) {
			return null;
		}
		// vérifier les permissions de l'utilisateur
		String query = "SELECT * FROM utilisateur WHERE id = ?";
		List<Map<String, Object>> retour = manageSQL.executeSelectSql(query, id_utilisateur);
		
		if (retour.isEmpty()) {
			return null;
		}
		
		String role = (String) retour.get(0).get("role");
		if (!role.equals("ADMINISTRATEUR") || !role.equals("MANAGER")) {
			return null;
		}
		
		String sql = "SELECT * FROM rapport";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(sql);
		
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
			if (row.get("sys_datesup") != null) {
				rapport.setSys_datesup((LocalDateTime) row.get("sys_datesup"));
			} else {
				rapport.setSys_datesup(null);
			}
			rapports.add(rapport);
		}
		return rapports;
	}

	public Rapport getRapportById(Long id) throws SQLException {
		String query = "SELECT * FROM rapport WHERE id = ? AND sys_datesup IS NULL";
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
	
	public Rapport getRapportTrashById(Long id_utilisateur, Long id) throws SQLException {
		if (id_utilisateur == null) {
			return null;
		}
		// vérifier les permissions de l'utilisateur
		String query = "SELECT * FROM utilisateur WHERE id = ?";
		List<Map<String, Object>> retour = manageSQL.executeSelectSql(query, id_utilisateur);
		
		if (retour.isEmpty()) {
			return null;
		}
		
		String role = (String) retour.get(0).get("role");
		if (!role.equals("ADMINISTRATEUR") || !role.equals("MANAGER")) {
			return null;
		}
		
		String sql = "SELECT * FROM rapport WHERE id = ?";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(sql, id);
		
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
		if (row.get("sys_datesup") != null) {
			rapport.setSys_datesup((LocalDateTime) row.get("sys_datesup"));
		} else {
			rapport.setSys_datesup(null);
		}
		return rapport;
	}
	
	public List<Rapport> getRapportsByUser(Long id) throws SQLException {
		String query = "SELECT * FROM rapport WHERE utilisateur = ?";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(query, id);
		
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
	
	public List<Rapport> searchRapportsByDate(String dateDebut, String dateFin) throws SQLException {
		String query = "SELECT * FROM rapport WHERE (dateDebut = ? OR dateFin = ?) AND sys_datesup IS NULL";
		List<Map<String, Object>> results = manageSQL.executeSelectSql(query, dateDebut, dateFin);
		
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
	
	public boolean createRapport(Rapport rapport) throws SQLException {
		String query = "INSERT INTO rapport (dateDebut, dateFin, coutMoyenRepas, menusInclus, utilisateur) VALUES (?, ?, ?, ?, ?)";
		int rowsAffected = manageSQL.executeUpdateSql(query, rapport.getDateDebut(), rapport.getDateFin(), rapport.getCoutMoyenRepas(), rapport.getMenusInclus(), rapport.getUtilisateur());
		return rowsAffected > 0;
	}

	public boolean updateRapport(Rapport rapport) throws SQLException {
		String query = "UPDATE rapport SET dateDebut = ?, dateFin = ?, coutMoyenRepas = ?, menusInclus = ?, utilisateur = ? WHERE id = ?";
		int rowsAffected = manageSQL.executeUpdateSql(query, rapport.getDateDebut(), rapport.getDateFin(), rapport.getCoutMoyenRepas(), rapport.getMenusInclus(), rapport.getUtilisateur(), rapport.getId());
		return rowsAffected > 0;
	}

	public boolean deleteRapport(Long id) throws SQLException {
		String query = "UPDATE rapport SET sys_datesup=now() WHERE id = ?";
		int rowsAffected = manageSQL.executeUpdateSql(query, id);
		return rowsAffected > 0;
	}
	public boolean DeleteRapportDefinitivement(Long id, Long id_utilisateur) throws SQLException {
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
		String query = "DELETE FROM rapport WHERE id = ?";
		int rowsAffected = manageSQL.executeUpdateSql(query, id);
		return rowsAffected > 0;
	}
	public boolean retablireRapport(Long id, Long id_utilisateur) throws SQLException {
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
		String query = "UPDATE rapport SET sys_datesup=null WHERE id = ? AND sys_datesup IS NOT NULL";
		int rowsAffected = manageSQL.executeUpdateSql(query, id);
		return rowsAffected > 0;
	}
}
