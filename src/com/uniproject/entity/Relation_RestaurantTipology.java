package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(alias = "r", tableName = "restaurant")
public class Relation_RestaurantTipology {

	// ---------------------------------------------------
	// -- Campi di relazione tra tipologia e ristorante --
	// ---------------------------------------------------

	@AliasFieldDAO(alias = "r", as = "id_restaurant")
	private String id_restaurant;

	@AliasFieldDAO(alias = "r", as = "name")
	private String name;
	
	@AliasFieldDAO(alias = "r", as = "city")
	private String city;
	
	@AliasFieldDAO(alias = "r", as = "address")
	private String address;
	
	@AliasFieldDAO(alias = "r", as = "cap")
	private String cap;
	
	@AliasFieldDAO(alias = "r", as = "phone")
	private String phone;
	
	@AliasFieldDAO(alias = "rt", as = "description")
	private String description;
	
	@AliasFieldDAO(alias = "rt", as = "tipology")
	private String tipology;

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

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return
	 */
	public String getTipology() {
		return tipology;
	}

	/**
	 * 
	 * @param tipology
	 */
	public void setTipology(String tipology) {
		this.tipology = tipology;
	}
	
}
