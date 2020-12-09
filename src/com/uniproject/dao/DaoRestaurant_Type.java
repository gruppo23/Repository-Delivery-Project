package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.jdbc.PostgreSQL;

public class DaoRestaurant_Type extends DaoEngine implements DaoInterface<Void, Void, Void, Void>{
	
	/**
	 * 
	 * @param restaurant_tipology
	 */
	public DaoRestaurant_Type(Restaurant_Tipology restaurant_tipology) {
		super(restaurant_tipology);
	}
	
	@Override
	public String insert(PostgreSQL psql, Void... i) {
		generateQueryInsert(0);
		System.out.println(QUERY);
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
