package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Restaurant_Product;
import com.uniproject.jdbc.PostgreSQL;

public class Restaurant_ProductDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, Integer> {

	public Restaurant_ProductDAO(Restaurant_Product restaurant_Product) {
		super(restaurant_Product);
	}
	
	@Override
	public String insert(int delta, PostgreSQL psql, Void... i) {
		generateQueryInsert(0);
		return QUERY;
	}

	@Override
	public String update(int delta, PostgreSQL psql, Void... u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, Void... d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, Integer... s) {
		return generateQuerySelect().generateQueryWhere(" rp.id_product =  " + s[0]).endGenerateSelect(psql, new Restaurant_Product());
	}

}
