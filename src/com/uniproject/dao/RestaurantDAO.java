package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Restaurant;
import com.uniproject.jdbc.PostgreSQL;

public class RestaurantDAO extends EngineDAO implements InterfaceDAO<String, String, String, String> {

	/**
	 * 
	 * @param restaurant
	 */
	public RestaurantDAO(Restaurant restaurant) {
		super(restaurant);
	}
	
	@Override
	public String insert(int delta, PostgreSQL psql, String... i) {
		generateQueryInsert();
		return QUERY;
	}

	@Override
	public String update(int delta, PostgreSQL psql, String... u) {
		generateQueryUpdate();
		return QUERY;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, String... d) {
		generateQueryDelete(-1); // Valori interi da escludere!
		return QUERY;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		return generateQuerySelect().endGenerateSelect(psql, new Restaurant());
	}

}
