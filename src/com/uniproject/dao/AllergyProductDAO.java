package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.AllergyProduct;
import com.uniproject.jdbc.PostgreSQL;

public class AllergyProductDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, Integer>{
	
	public AllergyProductDAO(AllergyProduct allergyproduct) {
		super(allergyproduct);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public String insert(int delta, PostgreSQL psql, Void... i) {
		generateQueryInsert();
		return QUERY;
	}

	@Override
	public String update(int delta, PostgreSQL psql, Void... u) {
		// TODO Auto-generated method stub
		return null;		
	}

	@Override
	public String delete(int delta, PostgreSQL psql, Void... d) {
		generateQueryDelete(new int[] {1});
		return QUERY;		
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, Integer... s) {
		List<AllergyProduct> prod = null;
		switch(delta) {
		
			case 0:
				prod = (List<AllergyProduct>)generateQuerySelect().endGenerateSelect(psql, new AllergyProduct());
				break;
				
			case 1:
				prod = (List<AllergyProduct>)generateQuerySelect().generateQueryOrderBy("id", "desc").endGenerateSelect(psql, new AllergyProduct());
				break;
				
			case 2:
				prod = (List<AllergyProduct>)generateQuerySelect()
												.generateQueryWhere("allprod.id = " + s[0])
													.endGenerateSelect(psql, new AllergyProduct());
				break;
		
		}
		
		return prod;
	}
	
	@Override
	public String select(int delta) {
		generateQuerySelect("ap.id", "ap")
			.generateQueryWhere("ap.id_allergen IN");
		return QUERY;
	}

}