package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "delivery_order_product", alias = "_dop")
public class DeliveryOrderQuantity {

	@AliasFieldDAO(alias = "_dop", as = "quantity")
	private double quantity;
	
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
	public double getQuantity() {
		return this.quantity;
	}
	
}
