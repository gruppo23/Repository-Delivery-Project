package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Driver;
import com.uniproject.jdbc.PostgreSQL;

public class DriverDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String> {

	/**
	 * 
	 * @param driver
	 */
	public DriverDAO(Driver driver) {
		super(driver);
	}
	
	@Override
	public String insert(int delta, PostgreSQL psql, Void... i) {
		generateQueryInsert();
		return QUERY;
	}

	@Override
	public String update(int delta, PostgreSQL psql, Void... u) {
		generateQueryUpdate(new int[] {1, 2, 3, 4, 5, 6}, new int[] {0});
		System.out.println(QUERY);
		return QUERY;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, Void... d) {
		generateQueryDelete(new int[] {0});
		return QUERY;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		List<Driver> drivers = null;
		
		if(delta == 0)
			drivers = (List<Driver>)generateQuerySelect().endGenerateSelect(psql, new Driver());
		else
			drivers = (List<Driver>)generateQuerySelect()
						.generateQueryWhere("")
							.generateLike()
								.endGenerateSelect(psql, new Driver());
		
		return drivers;
	}

}
