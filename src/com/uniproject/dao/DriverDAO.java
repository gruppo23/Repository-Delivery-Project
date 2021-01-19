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
		
		switch(delta) {
		
			case 0:
				drivers = (List<Driver>)generateQuerySelect().endGenerateSelect(psql, new Driver());
			break;
			
			case 1:
				drivers = (List<Driver>)generateQuerySelect()
							.generateQueryWhere("")
								.generateLike()
									.endGenerateSelect(psql, new Driver());
			break;
			
			case 2:
				generateQuerySelect().generateQueryWhere(" dr.fiscal_code not in ");
				QUERY += select(0).replaceAll("#", "'" + s[0] + "'");
				drivers = (List<Driver>)endGenerateSelect(psql, new Driver());
			break;
		
		}
		
		return drivers;
	}

	@Override
	public String select(int delta) {
		String query =  "(										";
			   query += "select									";
			   query += "id_driver from							";
			   query += "(select								";
			   query += "_do.id_driver,						    ";
			   query += "count(_do.id_driver) as cnt			";
			   query += "from delivery_order as _do				";
			   query += "where _do.status = 0					";
			   query += "group by _do.id_driver					";
			   query += ") as sq where sq.cnt >= 3				";
			   query += ") and dr.transport = #					";
		return query;
	}
	
}
