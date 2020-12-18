package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "drivers", alias = "dr")
public class Drivers {

	@AliasFieldDAO(alias = "dr", as = "fiscal_code")
	private String fiscal_code;
	
	@AliasFieldDAO(alias = "dr", as = "name")
	private String name;
	
	@AliasFieldDAO(alias = "dr", as = "surname")
	private String surname;
	
	@AliasFieldDAO(alias = "dr", as = "city")
	private String city;
	
	@AliasFieldDAO(alias = "dr", as = "cap")
	private String cap;
	
	@AliasFieldDAO(alias = "dr", as = "address")
	private String address;
	
	@AliasFieldDAO(alias = "dr", as = "phone")
	private String phone;

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
