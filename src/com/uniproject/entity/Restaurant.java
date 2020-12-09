package com.uniproject.entity;

public class Restaurant {

	// Campi del ristorante
	private String 	id_restaurant;
	private String 	name;
	private int 	tipology;
	private String 	city;
	private String 	address;
	private String 	cap;
	private String 	phone;
	
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
		return tipology;
	}
	
	/**
	 * 
	 * @param tipology
	 */
	public void setTipology(int tipology) {
		this.tipology = tipology;
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
