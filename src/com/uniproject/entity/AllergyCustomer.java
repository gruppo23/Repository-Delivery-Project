package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(alias = "allcus", tableName = "allergycustomer")
public class AllergyCustomer {

	@AliasFieldDAO(alias = "allcus", as = "id_allergen")
	private String id_allergen;
	
	@AliasFieldDAO(alias = "allcus", as = "fiscal_code")
	private String fiscal_code;

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
	public String getFiscal_code() {
		return fiscal_code;
	}

	/**
	 * 
	 * @param id
	 */
	public void setFiscal_code(String fiscal_code) {
		this.fiscal_code = fiscal_code;
	}


}
