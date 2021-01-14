package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.jdbc.PostgreSQL;

public class Restaurant_TypeDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{
	
	/**
	 * 
	 * @param restaurant_tipology
	 */
	public Restaurant_TypeDAO(Restaurant_Tipology restaurant_tipology) {
		super(restaurant_tipology);
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
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		if(delta == 0)
			return generateQuerySelect().endGenerateSelect(psql, new Restaurant_Tipology());
		else
			return generateQuerySelect().generateQueryWhere(" rest_type.tipology =  '" + s[0] + "'").endGenerateSelect(psql, new Restaurant_Tipology());
	}
	
	@Override
	public String select(int delta) {
		return null;
	}

}
