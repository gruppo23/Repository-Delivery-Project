package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.DeliveryOrderProduct;
import com.uniproject.jdbc.PostgreSQL;

public class DeliveryOrderProductDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{

	public DeliveryOrderProductDAO(Object model) {
		super(model);
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
		generateQueryDelete(new int[] {0});
		return QUERY;
	}

	@Override
	public String select(int delta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		
		List<DeliveryOrderProduct> orders = null;
		if(delta == 0)
			orders = (List<DeliveryOrderProduct>)generateQuerySelect().endGenerateSelect(psql, new DeliveryOrderProduct());
		else
			orders = (List<DeliveryOrderProduct>)generateQuerySelect()
						.generateQueryWhere(" ")
							.generateLike()
								.endGenerateSelect(psql, new DeliveryOrderProduct());
		
		return orders;
	}
}
