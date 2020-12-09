package com.uniproject.entity;

public class Users {

	// Campi utente
	private int 	id_user;
	private String 	name_user;
	private String 	surname_user;
	private String 	username;
	private String 	password;
	
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
