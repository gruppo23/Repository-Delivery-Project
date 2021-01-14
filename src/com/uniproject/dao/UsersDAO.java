package com.uniproject.dao;

import java.util.List;
import com.uniproject.entity.Users;
import com.uniproject.jdbc.PostgreSQL;

public class UsersDAO extends EngineDAO implements InterfaceDAO<Void, Void, Void, String>{

	/**
	 * 
	 * @param users
	 */
	public UsersDAO(Users users) {
		super(users);
	}
	
	@Override
	public String insert(int delta, PostgreSQL psql, Void ... params) {
		return null;
	}

	@Override
	public String update(int delta, PostgreSQL psql, Void ... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, Void ... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String ... params) {
		return generateQuerySelect()
				.generateQueryWhere("username = '"+params[0]+"' AND password = crypt('"+params[1]+"', password)")
					.endGenerateSelect(psql, new Users());
	}

	@Override
	public String select(int delta) {
		return null;
	}
	
}
