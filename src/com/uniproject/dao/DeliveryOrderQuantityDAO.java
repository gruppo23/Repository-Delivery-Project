package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.DeliveryOrderQuantity;
import com.uniproject.jdbc.PostgreSQL;

public class DeliveryOrderQuantityDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{

	public DeliveryOrderQuantityDAO(Object model) {
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
		QUERY = select(0).replace("@", s[0]);
		return (List<DeliveryOrderQuantity>) endGenerateSelect(psql, new DeliveryOrderQuantity());
	}

	@Override
	public String select(int delta) {
		return "SELECT SUM(_dop.quantity) AS quantity FROM delivery_order_product AS _dop WHERE _dop.id_order = @";
	}

}
