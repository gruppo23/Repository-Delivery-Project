package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Allergen;
import com.uniproject.jdbc.PostgreSQL;

public class AllergenDAO extends EngineDAO implements InterfaceDAO<String, Void, Integer, String> {

	/**
	 * 
	 * @param allergen
	 */
	public AllergenDAO (Allergen allergen) {
		super(allergen);
	}
	
	@Override
	public String insert(int delta, PostgreSQL psql, String... i) {
		return null;
	}

	@Override
	public String update(int delta, PostgreSQL psql, Void... u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, Integer... d) {	
		return null;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		List<Allergen> allerg = null;
		if(delta == 0)
			 allerg = (List<Allergen>)generateQuerySelect().endGenerateSelect(psql, new Allergen());
		else
			 allerg = (List<Allergen>)generateQuerySelect().generateQueryOrderBy("id_allergen", "asc").endGenerateSelect(psql, new Allergen());
		return allerg;
		
	}

}
