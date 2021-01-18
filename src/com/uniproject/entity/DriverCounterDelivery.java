package com.uniproject.entity;

import com.uniproject.dao.AliasFieldDAO;
import com.uniproject.dao.AliasTableDAO;

@AliasTableDAO(tableName = "driver", alias = "_dr")
public class DriverCounterDelivery {

	@AliasFieldDAO(alias = "_dr", as = "fiscal_code")
	private String fiscal_code;

	@AliasFieldDAO(alias = "_dr", as = "name")
	private String name;
	
	@AliasFieldDAO(alias = "_dr", as = "surname")
	private String surname;
	
	@AliasFieldDAO(alias = "_dr", as = "ordini")
	private long ordini;

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
	 * @param fiscal_code
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
	 * @param fiscal_code
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * 
	 * @return
	 */
	public long getOrdini() {
		return this.ordini;
	}

	/**
	 * 
	 * @param num_delivery
	 */
	public void setOrdini(long ordini) {
		this.ordini = ordini;
	}
	
	
}
