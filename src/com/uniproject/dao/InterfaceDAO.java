package com.uniproject.dao;

import java.util.List;

import com.uniproject.jdbc.PostgreSQL;

public interface InterfaceDAO<I, U, D, S> {

	/**
	 * 
	 * @param delta
	 * @param psql
	 * @param i
	 * @return
	 */
	public String insert(int delta, PostgreSQL psql, I ... i);
	
	/**
	 * 
	 * @param delta
	 * @param psql
	 * @param u
	 */
	public String update(int delta, PostgreSQL psql, U ... u);
	
	/**
	 * 
	 * @param delta
	 * @param psql
	 * @param d
	 */
	public String delete(int delta, PostgreSQL psql, D ... d);
	
	/**
	 * 
	 * @param delta
	 * @param psql
	 * @param s
	 * @return
	 */
	public List<?> select(int delta, PostgreSQL psql, S ... s);
	
	/**
	 * 
	 * @param delta
	 * @return
	 */
	public String select(int delta);
	
}
