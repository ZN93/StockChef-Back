package com.stockchef.stockchefback.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Menu {
    private Long id;
    private String nom;
    private List<String> ingredients;
    private BigDecimal coutTotal;
	public Object getDateMenu() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getDateMenu'");
	}
    public void setDateMenu(LocalDateTime localDateTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDateMenu'");
    }
	public void setIngredients(String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setIngredients'");
	}
}
