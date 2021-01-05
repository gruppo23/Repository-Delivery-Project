package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Relation_AllergyCustomerDescription;
import com.uniproject.jdbc.PostgreSQL;

public class AllergyCustomer_RelationDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{

	public AllergyCustomer_RelationDAO(Relation_AllergyCustomerDescription relation_AllergyCustomerDescription) {
		super(relation_AllergyCustomerDescription);
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
		return (List<Relation_AllergyCustomerDescription>) 
				generateQuerySelect()
					.generateJoin(JoinTypes.LEFT, "allergycustomer AS ac", " ON ac.fiscal_code = c.fiscal_code ")
						.generateJoin(JoinTypes.LEFT, "allergen AS a", " ON a.id_allergen = ac.id_allergen ")
							.generateQueryWhere(" c.fiscal_code =  '" + s[0] + "'")
								.endGenerateSelect(psql, new Relation_AllergyCustomerDescription());
	}

}
