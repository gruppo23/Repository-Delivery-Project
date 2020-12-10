package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Restaurant;
import com.uniproject.jdbc.PostgreSQL;

public class DaoRestaurant extends DaoEngine implements DaoInterface<String, String, String, String> {

	/**
	 * 
	 * @param restaurant
	 */
	public DaoRestaurant(Restaurant restaurant) {
		super(restaurant);
	}
	
	@Override
	public String insert(PostgreSQL psql, String... i) {
		generateQueryInsert();
		return QUERY;
	}

	@Override
	public void update(PostgreSQL psql, String... u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(PostgreSQL psql, String... d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<?> select(PostgreSQL psql, String... s) {
		// TODO Auto-generated method stub
		return null;
	}

}
