package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "product", alias = "p")
public class ProductRestaurantQuantity {

	@AliasFieldDAO(alias = "p", as = "manage_quantity")
	private boolean manage_quantity;

	@AliasFieldDAO(alias = "rp", as = "quantity")
	private int quantity;
	
	/**
	 * 
	 * @return
	 */
	public boolean isManage_quantity() {
		return manage_quantity;
	}

	/**
	 * 
	 * @param manage_quantity
	 */
	public void setManage_quantity(boolean manage_quantity) {
		this.manage_quantity = manage_quantity;
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
