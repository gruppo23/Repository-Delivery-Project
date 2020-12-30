package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.AllergyProduct;
import com.uniproject.jdbc.PostgreSQL;

public class AllergyProductDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{
	
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
		// TODO Auto-generated method stub
		return null;		
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		List<AllergyProduct> prod = null;
		if(delta == 0)
			 prod = (List<AllergyProduct>)generateQuerySelect().endGenerateSelect(psql, new AllergyProduct());
		else
			 prod = (List<AllergyProduct>)generateQuerySelect().generateQueryOrderBy("id", "desc").endGenerateSelect(psql, new AllergyProduct());
		return prod;
	}

}