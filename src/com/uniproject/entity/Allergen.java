package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;
import com.uniproject.dao.Key;

@AliasTableDAO(alias = "al", tableName = "allergen")
public class Allergen {

	@AliasFieldDAO(alias = "al", as = "id_allergen")
	@Key()
	private String id_allergen;
	
	@AliasFieldDAO(alias = "al", as = "name_allergen")
	private String name_allergen;

	/**
	 * 
	 * @return
	 */
	public String getId_allergen() {
		return id_allergen;
	}

	/**
	 * 
	 * @param id_allergen
	 */
	public void setId_allergen(String id_allergen) {
		this.id_allergen = id_allergen;
	}

	/**
	 * 
	 * @return
	 */
	public String getName_allergen() {
		return name_allergen;
	}

	/**
	 * 
	 * @param name_allergen
	 */
	public void setName_allergen(String name_allergen) {
		this.name_allergen = name_allergen;
	}
	
}
