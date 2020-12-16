package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Relation_RestaurantProduct;
import com.uniproject.jdbc.PostgreSQL;

public class RelationRestaurantProductDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, Void> {

	/**
	 * 
	 * @param relationRestaurantProduct
	 */
	public RelationRestaurantProductDAO(Relation_RestaurantProduct relationRestaurantProduct) {
		super(relationRestaurantProduct);
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
	public List<?> select(int delta, PostgreSQL psql, Void... s) {
		return generateQuerySelect()
				.generateJoin(JoinTypes.INNER, " restaurant_product AS rp ", " ON p.id = rp.id_product ")
					.generateJoin(JoinTypes.INNER, " restaurant AS r ", " ON r.id_restaurant = rp.id_restaurant ")
						.endGenerateSelect(psql, new Relation_RestaurantProduct());
	}

}
