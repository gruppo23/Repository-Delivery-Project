package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.DriverCounterDelivery;
import com.uniproject.jdbc.PostgreSQL;

public class DriverCounterDeliveryDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{
	
	public DriverCounterDeliveryDAO(Object model) {
		super(model);
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
		generateQuerySelect()
			.generateJoin(JoinTypes.LEFT, "delivery_order AS _do", " ON _dr.fiscal_code = _do.id_driver ")
				.generateQueryWhere(" _dr.transport = '" + s[0] + "' AND _do.status = 0 ")
					.generateQueryGroupBy(" _dr.fiscal_code ");
		QUERY = QUERY.replace("_dr.ordini AS ordini", "(COUNT(_dr.fiscal_code) - 1) AS ordini");
		System.out.println(QUERY);
		return ((List<DriverCounterDelivery>) endGenerateSelect(psql, new DriverCounterDelivery()));
	}

	@Override
	public String select(int delta) {
		return "";
	}

	
	
}
