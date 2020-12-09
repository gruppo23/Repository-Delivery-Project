package com.uniproject.entity;

public class Restaurant_Tipology {

	// Struttura tabella db
	private String tipology;
	private String description;
	
	/**
	 * 
	 * @return
	 */
	public String getType() {
		return tipology;
	}
	
	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.tipology = type;
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
