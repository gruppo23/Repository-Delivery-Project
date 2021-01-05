package com.uniproject.dao;

import java.util.List;
import com.uniproject.entity.Customer;
import com.uniproject.entity.Driver;
import com.uniproject.jdbc.PostgreSQL;

public class CustomerDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String> {

	/**
	 * 
	 * @param customer
	 */
	public CustomerDAO(Customer customer) {
		super(customer);
	}
	
	@Override
	public String insert(int delta, PostgreSQL psql, Void... i) {
		generateQueryInsert();
		return QUERY;
	}

	@Override
	public String update(int delta, PostgreSQL psql, Void... u) {
		generateQueryUpdate(new int[] {1, 2, 3, 4, 5, 6, 7}, new int[] {0});
		return QUERY;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, Void... d) {
		generateQueryDelete(new int[] {0});
		return QUERY;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		List<Customer> customers = null;
		
		if(delta == 0)
			customers = (List<Customer>)generateQuerySelect().endGenerateSelect(psql, new Customer());
		else
			customers = (List<Customer>)generateQuerySelect()
						.generateQueryWhere("")
							.generateLike()
								.endGenerateSelect(psql, new Customer());
		
		return customers;
	}

}
