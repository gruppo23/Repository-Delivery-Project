package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "restaurant_product", alias = "rp")
public class Restaurant_Product {

	@AliasFieldDAO(alias = "rp", as = "id_relation")
	private int id_relation;
	
	@AliasFieldDAO(alias = "rp", as = "id_product")
	private int id_product;
	
	@AliasFieldDAO(alias = "rp", as = "id_restaurant")
	private String id_restaurant;
	
	@AliasFieldDAO(alias = "rp", as = "quantity")
	private int quantity;

	/**
	 * 
	 * @return
	 */
	public int getId_relation() {
		return id_relation;
	}

	/**
	 * 
	 * @param id_relation
	 */
	public void setId_relation(int id_relation) {
		this.id_relation = id_relation;
	}

	/**
	 * 
	 * @return
	 */
	public int getId_product() {
		return id_product;
	}

	/**
	 * 
	 * @param id_product
	 */
	public void setId_product(int id_product) {
		this.id_product = id_product;
	}

	/**
	 * 
	 * @return
	 */
	public String getId_restaurant() {
		return id_restaurant;
	}

	/**
	 * 
	 * @param idRestaurant
	 */
	public void setId_restaurant(String idRestaurant) {
		this.id_restaurant = idRestaurant;
	}

	/**
	 * 
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
