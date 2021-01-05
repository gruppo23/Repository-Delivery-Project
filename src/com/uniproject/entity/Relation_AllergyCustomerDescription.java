package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "customer", alias = "c")
public class Relation_AllergyCustomerDescription {

	@AliasFieldDAO(alias = "c", as = "fiscal_code")
	private String fiscal_code;
	
	@AliasFieldDAO(alias = "c", as = "name")
	private String name;
	
	@AliasFieldDAO(alias = "c", as = "surname")
	private String surname;
	
	@AliasFieldDAO(alias = "a", as = "name_allergen")
	private String name_allergen;
	
	/**
	 * 
	 * @return
	 */
	public String getFiscal_code() {
		return fiscal_code;
	}

	/**
	 * 
	 * @param fiscal_code
	 */
	public void setFiscal_code(String fiscal_code) {
		this.fiscal_code = fiscal_code;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * 
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
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
