package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Delivery_Order;
import com.uniproject.entity.Delivery_Order_Product;
import com.uniproject.jdbc.PostgreSQL;

public class DeliveryOrderDao extends EngineDAO implements InterfaceDAO<Void, Void, Void, String> {

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
		return QUERY;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, Void... d) {
		generateQueryDelete(new int[] {0});
		return QUERY;
	}
	
	@Override
	public String select(int delta) {
		return null;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		List<Delivery_Order> deliveryOrders = null;
		if(delta == 0)
			deliveryOrders = (List<Delivery_Order>)generateQuerySelect().endGenerateSelect(psql, new Delivery_Order());
		else
			deliveryOrders = (List<Delivery_Order>)generateQuerySelect().generateQueryOrderBy("_do.id", "desc").generateQueryLimit().endGenerateSelect(psql, new Delivery_Order());
		return deliveryOrders;
	}
}
