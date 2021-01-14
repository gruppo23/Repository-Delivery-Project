package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.DeliveryOrder;
import com.uniproject.jdbc.PostgreSQL;

public class DeliveryOrderDao extends EngineDAO implements InterfaceDAO<Void, Void, Void, Void> {

	public DeliveryOrderDao(Object model) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, Void... s) {
		return
			generateQuerySelect()
				.generateQueryOrderBy("_do.id", "desc")
					.generateQueryLimit()
						.endGenerateSelect(psql, new DeliveryOrder());
	}

	@Override
	public String select(int delta) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
