package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;
import com.uniproject.dao.Key;

@AliasTableDAO(alias = "rest", tableName = "restaurant")
public class Restaurant {


	// ----------------------
	// -- Campi ristorante --
	// ----------------------
	
	@AliasFieldDAO(alias = "rest", as = "id_restaurant")
	@Key()
	private String id_restaurant;
	
	@AliasFieldDAO(alias = "rest", as = "name")
	private String name;
	
	@AliasFieldDAO(alias = "rest", as = "id_tipology")
	private int id_tipology;
	
	@AliasFieldDAO(alias = "rest", as = "city")
	private String city;
	
	@AliasFieldDAO(alias = "rest", as = "address")
	private String address;
	
	@AliasFieldDAO(alias = "rest", as = "cap")
	private String cap;
	
	@AliasFieldDAO(alias = "rest", as = "phone")
	private String phone;
	
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
	public int getTipology() {
		return id_tipology;
	}
	
	/**
	 * 
	 * @param tipology
	 */
	public void setTipology(int tipology) {
		this.id_tipology = tipology;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCap() {
		return cap;
	}
	
	/**
	 * 
	 * @param cap
	 */
	public void setCap(String cap) {
		this.cap = cap;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
