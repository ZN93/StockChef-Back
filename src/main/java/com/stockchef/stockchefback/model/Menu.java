package com.stockchef.stockchefback.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
public class Menu {
    private Long id;
    private String nom;
    private List<String> ingredients;
    private BigDecimal coutTotal;
	private LocalDateTime dateMenu;
	private LocalDateTime sys_datesup;
	
	public Object getDateMenu() {
		return this.dateMenu;
	}
    public void setDateMenu(LocalDateTime dateMenu) {
        if (dateMenu == null) {
            this.dateMenu = LocalDateTime.now();
        } else {
            this.dateMenu = dateMenu;
        }
    }
	public void setIngredients(String ingredients) {
		String[] list = ingredients.split(",");
		this.ingredients = Arrays.asList(list);
	}
    public void setSys_datesup(LocalDateTime sys_datesup) {
		this.sys_datesup = sys_datesup;
	}
}
