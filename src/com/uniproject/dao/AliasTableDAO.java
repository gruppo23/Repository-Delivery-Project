package com.uniproject.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AliasTableDAO {

	/**
	 * 
	 * @return
	 */
	public String alias();
	
	/**
	 * 
	 * @return
	 */
	public String tableName();
	
}
