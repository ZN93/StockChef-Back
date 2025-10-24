package com.stockchef.stockchefback.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Rapport {
    private Long id;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private BigDecimal coutMoyenRepas;
    private String menusInclus;
    private Long utilisateur;
    private LocalDateTime sys_datesup;
    
    public Object getDateDebut() {
        return this.dateDebut;
    }
    public void setDateDebut(LocalDateTime dateDebut) {
        if (dateDebut == null) {
            this.dateDebut = LocalDateTime.now();
        } else {
            this.dateDebut = dateDebut;
        }
    }
    public Object getDateFin() {
        return this.dateFin;
    }
    public void setDateFin(LocalDateTime dateFin) {
        if (dateFin == null) {
            this.dateFin = LocalDateTime.now();
        } else {
            this.dateFin = dateFin;
        }
    }
    public Object getCoutMoyenRepas() {
        return this.coutMoyenRepas;
    }
    public void setCoutMoyenRepas(BigDecimal coutMoyenRepas) {
        if (coutMoyenRepas == null) {
            this.coutMoyenRepas = BigDecimal.ZERO;
        } else {
            this.coutMoyenRepas = coutMoyenRepas;
        }
    }
    public Object getMenusInclus() {
        return this.menusInclus;
    }
    public void setMenusInclus(String menusInclus) {
        this.menusInclus = menusInclus;
    }
    public Object getUtilisateur() {
        return this.utilisateur;
    }
    public void setUtilisateur(Long utilisateur) {
        this.utilisateur = utilisateur;
    }
    public Object getSys_datesup() {
        return this.sys_datesup;
    }
    public void setSys_datesup(LocalDateTime sys_datesup) {
        this.sys_datesup = sys_datesup;
    }
}
