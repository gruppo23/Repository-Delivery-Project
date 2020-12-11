package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.jdbc.PostgreSQL;

public class Restaurant_TypeDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, Void>{
	
	/**
	 * 
	 * @param restaurant_tipology
	 */
	public Restaurant_TypeDAO(Restaurant_Tipology restaurant_tipology) {
		super(restaurant_tipology);
	}
	
	@Override
	public String insert(PostgreSQL psql, Void... i) {
		generateQueryInsert(0);
		return QUERY;
	}

	@Override
	public void update(PostgreSQL psql, Void... u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(PostgreSQL psql, Void... d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<?> select(PostgreSQL psql, Void... s) {
		return generateQuerySelect().endGenerateSelect(psql, new Restaurant_Tipology());
	}

}
