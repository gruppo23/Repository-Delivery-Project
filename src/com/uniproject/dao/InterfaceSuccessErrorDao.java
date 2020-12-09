package com.uniproject.dao;

public interface InterfaceSuccessErrorDao {

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
