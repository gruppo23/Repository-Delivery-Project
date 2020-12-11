package com.uniproject.dao;

public interface InterfaceSuccessErrorDAO {

	/**
	 * Successo della query
	 */
	public void ok();
	
	/**
	 * Query fallita!
	 * @param e
	 */
	public void err(String e);
	
}
