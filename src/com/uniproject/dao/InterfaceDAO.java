package com.uniproject.dao;

import java.util.List;

import com.uniproject.jdbc.PostgreSQL;

public interface InterfaceDAO<I, U, D, S> {

	/**
	 * 
	 * @param psql
	 * @param i
	 */
	public String insert(PostgreSQL psql, I ... i);
	
	/**
	 * 
	 * @param psql
	 * @param u
	 */
	public void update(PostgreSQL psql, U ... u);
	
	/**
	 * 
	 * @param psql
	 * @param d
	 */
	public void delete(PostgreSQL psql, D ... d);
	
	/**
	 * 
	 * @param psql
	 * @param s
	 */
	public List<?> select(PostgreSQL psql, S ... s);
	
}
