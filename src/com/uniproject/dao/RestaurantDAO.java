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
		generateQueryUpdate(new int[] {1, 2, 3, 4, 5, 6}, new int[] {0});
		return QUERY;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, String... d) {
		generateQueryDelete(new int[] {0}); // Valori interi da escludere!
		return QUERY;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		
		List<Restaurant> restaurant = null;
		
		switch(delta) {
			
			case 0:
				restaurant = (List<Restaurant>)generateQuerySelect().endGenerateSelect(psql, new Restaurant());
			break;
			
			case 1:
				restaurant = (List<Restaurant>)
								generateQuerySelect()
									.generateQueryOrderBy("id_restaurant", "desc")
										.generateQueryLimit()
											.endGenerateSelect(psql, new Restaurant());
			break;
		
		}
		
		return restaurant;
	}
	
	@Override
	public String select(int delta) {
		return null;
	}

}
