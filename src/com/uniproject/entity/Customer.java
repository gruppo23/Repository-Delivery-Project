package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "customer", alias = "cs")
public class Customer {

	@AliasFieldDAO(alias = "cs", as = "fiscal_code")
	private String fiscal_code;
	
	@AliasFieldDAO(alias = "cs", as = "name")
	private String name;
	
	@AliasFieldDAO(alias = "cs", as = "surname")
	private String surname;
	
	@AliasFieldDAO(alias = "cs", as = "date_n")
	private String date_n;
	
	@AliasFieldDAO(alias = "cs", as = "city")
	private String city;

	@AliasFieldDAO(alias = "cs", as = "cap")
	private String cap;
	
	@AliasFieldDAO(alias = "cs", as = "address")
	private String address;
	
	@AliasFieldDAO(alias = "cs", as = "phone")
	private String phone;
	
	@AliasFieldDAO(alias = "cs", as = "gender")
	private String gender;

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
	public String getDate() {
		return date_n;
	}

	/**
	 * 
	 * @param date
	 */
	public void setDate(String date) {
		this.date_n = date;
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
	
	/**
	 * 
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getGender() {
		return gender;
	}

	public String getCity() {
		return city;
	}

	/**
	 * 
	 * @param city
	 */
	public void setCap(String cap) {
		this.cap = cap;
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
	
}
