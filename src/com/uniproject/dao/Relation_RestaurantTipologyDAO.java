package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Relation_RestaurantTipology;
import com.uniproject.jdbc.PostgreSQL;

public class Relation_RestaurantTipologyDAO extends EngineDAO implements InterfaceDAO<String, String, String, String>{

	public Relation_RestaurantTipologyDAO(Relation_RestaurantTipology relation_RestaurantTipology) {
		super(relation_RestaurantTipology);
	}

	@Override
	public String insert(PostgreSQL psql, String... i) {
		// TODO Auto-generated method stub
		return null;
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
		return generateQuerySelect()
				.generateJoin(JoinTypes.INNER, "restaurant_tipology as rt", "ON r.id_tipology = rt.id_tipology")
					.endGenerateSelect(psql, new Relation_RestaurantTipology());
	}

}
