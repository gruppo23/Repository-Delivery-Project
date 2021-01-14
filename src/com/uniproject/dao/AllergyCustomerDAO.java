package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.AllergyCustomer;
import com.uniproject.jdbc.PostgreSQL;

public class AllergyCustomerDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{
	
	public AllergyCustomerDAO(AllergyCustomer allergycustomer) {
		super(allergycustomer);
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
		List<AllergyCustomer> prod = null;
		if(delta == 0)
			 prod = (List<AllergyCustomer>)generateQuerySelect().endGenerateSelect(psql, new AllergyCustomer());
		return prod;
	}
	
	@Override
	public String select(int delta) {
		generateQuerySelect("ac.id_allergen", "ac")
			.generateQueryWhere("ac.fiscal_code = ");
		return QUERY;
	}

}