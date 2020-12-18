package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.Product;
import com.uniproject.jdbc.PostgreSQL;

public class ProductDAO extends EngineDAO implements InterfaceDAO<String, Void, Integer, String> {

	/**
	 * 
	 * @param product
	 */
	public ProductDAO(Product product) {
		super(product);
	}
	
	@Override
	public String insert(int delta, PostgreSQL psql, String... i) {
		generateQueryInsert(0);
		System.out.println(QUERY);
		return QUERY;
	}

	@Override
	public String update(int delta, PostgreSQL psql, Void... u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(int delta, PostgreSQL psql, Integer... d) {	
		generateQueryDelete(new int[] {0});
		return QUERY;
	}

	@Override
	public List<?> select(int delta, PostgreSQL psql, String... s) {
		
		Product product = new Product();
		List<Product> prod = null;
		
		switch(delta) {
			
			case 0:
				prod = (List<Product>)generateQuerySelect().endGenerateSelect(psql, product);
			break;
			
			case 1:
				prod = (List<Product>)generateQuerySelect().generateQueryOrderBy("id", "desc").endGenerateSelect(psql, product);
			break;
			
			case 2:
				prod = (List<Product>)generateQuerySelect().generateQueryWhere("id = " + s[0]).endGenerateSelect(psql, product);
			break;
			
		}

		return prod;
	}

}
