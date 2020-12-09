package com.uniproject.dao;

import java.util.List;
import com.uniproject.entity.Users;
import com.uniproject.jdbc.PostgreSQL;

public class DaoUsers extends DaoEngine implements DaoInterface<Void, Void, Void, String>{

	/**
	 * 
	 * @param users
	 */
	public DaoUsers(Users users) {
		super(users);
	}
	
	@Override
	public String insert(PostgreSQL psql, Void ... params) {
		return null;
	}

	@Override
	public void update(PostgreSQL psql, Void ... params) {

	}

	@Override
	public void delete(PostgreSQL psql, Void ... params) {
		
	}

	@Override
	public List<?> select(PostgreSQL psql, String ... params) {
		return generateQuerySelect()
				.generateQueryWhere("username = '"+params[0]+"' AND password = crypt('"+params[1]+"', 'password')")
					.endGenerateSelect(psql, new Users());
	}

}
