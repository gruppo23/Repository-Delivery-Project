package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(alias = "usr", tableName = "users")
public class Users {

	// ------------------
	// -- Campi utente --
	// ------------------
	
	@AliasFieldDAO(alias = "usr", as = "id_user")
	private int id_user;
	
	@AliasFieldDAO(alias = "usr", as = "name_user")
	private String name_user;
	
	@AliasFieldDAO(alias = "usr", as = "surname_user")
	private String surname_user;
	
	@AliasFieldDAO(alias = "usr", as = "username")
	private String username;
	
	@AliasFieldDAO(alias = "usr", as = "password")
	private String password;
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id_user;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id_user = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name_user;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name_user = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getSurname() {
		return surname_user;
	}

	/**
	 * 
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname_user = surname;
	}

	/**
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
