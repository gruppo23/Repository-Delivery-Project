package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(alias = "allprod", tableName = "allergyproduct")
public class AllergyProduct {

	@AliasFieldDAO(alias = "allprod", as = "id_allergen")
	private String id_allergen;
	
	@AliasFieldDAO(alias = "allprod", as = "id")
	private int id;

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
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}


}
