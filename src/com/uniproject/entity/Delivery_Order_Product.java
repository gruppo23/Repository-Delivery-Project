package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "delivery_order_product", alias = "dpo")
public class Delivery_Order_Product {

	@AliasFieldDAO(alias = "dpo", as = "id")
	private int id;
	
	@AliasFieldDAO(alias = "dpo", as = "id_product")
	private int id_product;
	
	@AliasFieldDAO(alias = "dpo", as = "id_order")
	private int id_order;

	@AliasFieldDAO(alias = "dpo", as = "quantity")
	private double quantity;
	
	/**
	 * 
	 * @return
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * 
	 * @param quantity
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
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
	public int getId_order() {
		return id_order;
	}

	/**
	 * 
	 * @param id_order
	 */
	public void setId_order(int id_order) {
		this.id_order = id_order;
	}
	
}
