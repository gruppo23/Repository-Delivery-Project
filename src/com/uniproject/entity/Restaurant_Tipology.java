package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(alias = "rest_type", tableName = "restaurant_tipology")
public class Restaurant_Tipology {

	// --------------------------------
	// -- Campi tipologia ristorante --
	// --------------------------------
	
	@AliasFieldDAO(alias = "rest_type", as = "id_tipology")
	private int id_tipology;
	
	@AliasFieldDAO(alias = "rest_type", as = "tipology")
	private String tipology;
	
	@AliasFieldDAO(alias = "rest_type", as = "description")
	private String description;

	/**
	 * 
	 * @return
	 */
	public String getTipology() {
		return tipology;
	}

	/**
	 * 
	 * @param tipology
	 */
	public void setTipology(String tipology) {
		this.tipology = tipology;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId_tipology() {
		return id_tipology;
	}

	/**
	 * 
	 * @param id_tipology
	 */
	public void setId_tipology(int id_tipology) {
		this.id_tipology = id_tipology;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
