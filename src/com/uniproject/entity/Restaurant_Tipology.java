package com.uniproject.entity;

public class Restaurant_Tipology {

	// Struttura tabella db
	private int 	id_tipology;
	private String 	tipology;
	private String 	description;

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
