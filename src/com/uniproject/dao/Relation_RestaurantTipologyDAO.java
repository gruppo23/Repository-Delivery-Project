package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Relation_RestaurantTipology;
import com.uniproject.jdbc.PostgreSQL;

public class Relation_RestaurantTipologyDAO extends EngineDAO implements InterfaceDAO<String, String, String, String>{

	public Relation_RestaurantTipologyDAO(Relation_RestaurantTipology relation_RestaurantTipology) {
		super(relation_RestaurantTipology);
	}

	@Override
	public String insert(int delta, PostgreSQL psql, String... i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(int delta, PostgreSQL psql, String... u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, String... d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		if(delta == 0) {
			return generateQuerySelect()
					.generateJoin(JoinTypes.INNER, "restaurant_tipology as rt", "ON r.id_tipology = rt.id_tipology")
						.endGenerateSelect(psql, new Relation_RestaurantTipology());
		}else {
			return generateQuerySelect()
					.generateJoin(JoinTypes.INNER, "restaurant_tipology as rt", "ON r.id_tipology = rt.id_tipology")
						.generateQueryWhere("")
							.generateLike()
								.endGenerateSelect(psql, new Relation_RestaurantTipology());																			
		}
	}
	
	@Override
	public String select(int delta) {
		return null;
	}

}
