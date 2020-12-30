package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "allergyproduct", alias = "ap")
public class Relation_AllergyProduct {

	@AliasFieldDAO(alias = "p", as = "id")
	private int id;
	
	@AliasFieldDAO(alias = "p", as = "name_product")
	private String name_product;
	
	@AliasFieldDAO(alias = "p", as = "price")
	private double price;
	
	@AliasFieldDAO(alias = "p", as = "vat_number")
	private double vat_number;
	
	@AliasFieldDAO(alias = "al", as = "name_allergen")
	private String name_allergen;
	
	/**
	 * 
	 * @return
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * 
	 * @return
	 */
	public double getVat_number() {
		return vat_number;
	}

	/**
	 * 
	 * @param vat_number
	 */
	public void setVat_number(double vat_number) {
		this.vat_number = vat_number;
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

	/**
	 * 
	 * @return
	 */
	public String getName_product() {
		return name_product;
	}

	/**
	 * 
	 * @param name_product
	 */
	public void setName_product(String name_product) {
		this.name_product = name_product;
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
