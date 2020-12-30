package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Relation_AllergyProduct;
import com.uniproject.jdbc.PostgreSQL;

public class Relation_AllergyProductDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, Void> {

	/**
	 * 
	 * @param relationRestaurantProduct
	 */
	public Relation_AllergyProductDAO(Relation_AllergyProduct relationAllergyProduct) {
		super(relationAllergyProduct);
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
				.generateJoin(JoinTypes.INNER, "product AS p", "ON ap.id = p.id")
					.generateJoin(JoinTypes.INNER, "allergen as al", "ON ap.id_allergen = al.id_allergen")
						.endGenerateSelect(psql, new Relation_AllergyProduct());
	}

}
