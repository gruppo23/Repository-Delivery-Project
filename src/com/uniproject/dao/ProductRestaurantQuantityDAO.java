package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.ProductRestaurantQuantity;
import com.uniproject.jdbc.PostgreSQL;

public class ProductRestaurantQuantityDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{

	public ProductRestaurantQuantityDAO(Object model) {
		super(model);
	}
	
	@Override
	public String insert(int delta, PostgreSQL psql, Void... i) {
		// TODO Auto-generated method stub
		return null;
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
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		return (List<ProductRestaurantQuantity>)
			generateQuerySelect()
				.generateJoin(JoinTypes.INNER, " restaurant_product as rp ", " ON p.id = rp.id_product ")
					.generateQueryWhere(" rp.id_restaurant = '" + s[0] +"' AND p.id = "+s[1]+" ")
						.endGenerateSelect(psql, new ProductRestaurantQuantity());		
	}

	@Override
	public String select(int delta) {
		// TODO Auto-generated method stub
		return null;
	}

}
