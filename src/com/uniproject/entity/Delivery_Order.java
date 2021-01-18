package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(alias = "_do", tableName = "delivery_order")
public class Delivery_Order {

	@AliasFieldDAO(alias = "_do", as = "id")
	private int id;
	
	@AliasFieldDAO(alias = "_do", as = "id_customer")
	private String id_customer;
	
	@AliasFieldDAO(alias = "_do", as = "id_driver")
	private String id_driver;
	
	@AliasFieldDAO(alias = "_do", as = "id_restaurant")
	private String id_restaurant;
	
	@AliasFieldDAO(alias = "_do", as = "status")
	private int status;
	
	@AliasFieldDAO(alias = "_do", as = "totale")
	private double totale;

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
	public String getId_customer() {
		return id_customer;
	}

	/**
	 * 
	 * @param id_customer
	 */
	public void setId_customer(String id_customer) {
		this.id_customer = id_customer;
	}

	/**
	 * 
	 * @return
	 */
	public String getId_driver() {
		return id_driver;
	}

	/**
	 * 
	 * @param id_driver
	 */
	public void setId_driver(String id_driver) {
		this.id_driver = id_driver;
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
	 * @param id_restaurant
	 */
	public void setId_restaurant(String id_restaurant) {
		this.id_restaurant = id_restaurant;
	}

	/**
	 * 
	 * @return
	 */
	public int isStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 
	 * @return
	 */
	public double getTotale() {
		return totale;
	}

	/**
	 * 
	 * @param totale
	 */
	public void setTotale(double totale) {
		this.totale = totale;
	}
	
	
}
