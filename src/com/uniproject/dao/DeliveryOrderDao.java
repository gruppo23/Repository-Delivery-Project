package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Delivery_Order;
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
		generateQueryUpdate(new int[] {4}, new int[] {0});
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
		
		switch(delta) {
		
			case 0:
				deliveryOrders = (List<Delivery_Order>)generateQuerySelect().generateQueryWhere("_do.status = 0").endGenerateSelect(psql, new Delivery_Order());
			break;
		
			case 1:
				deliveryOrders = (List<Delivery_Order>)generateQuerySelect().generateQueryOrderBy("_do.id", "desc").generateQueryLimit().endGenerateSelect(psql, new Delivery_Order());
			break;
			
			case 2:
				generateQuerySelect();
				generateQueryWhere(" ");
				QUERY += "(";
				generateLike();
				QUERY += ") AND _do.status = 0";
				QUERY = QUERY.replace("OR(status::text LIKE '%"+s[0]+"%')", "");
				System.out.println(QUERY);
				deliveryOrders = (List<Delivery_Order>) endGenerateSelect(psql, new Delivery_Order());
			break;
			
			
			//Case 3 e 4 per gli ordini confermati
			case 3:
				deliveryOrders = (List<Delivery_Order>)generateQuerySelect().generateQueryWhere("_do.status = 1").endGenerateSelect(psql, new Delivery_Order());
			break;
			
			case 4:
				generateQuerySelect();
				generateQueryWhere(" ");
				QUERY += "(";
				generateLike();
				QUERY += ") AND _do.status = 1";
				QUERY = QUERY.replace("OR(status::text LIKE '%"+s[0]+"%')", "");
				System.out.println(QUERY);
				deliveryOrders = (List<Delivery_Order>) endGenerateSelect(psql, new Delivery_Order());
			break;
		}
		

		return deliveryOrders;
	}
}
