package com.uniproject.dao;

import java.util.List;

import com.uniproject.entity.AllergyCustomer;
import com.uniproject.entity.AllergyProduct;
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
		generateQueryUpdate(new int[] {1, 2, 3, 4, 5}, new int[] {0});
		System.out.println(QUERY);
		return QUERY;
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
			
			case 3:
				prod = (List<Product>)generateQuerySelect()
						.generateJoin(JoinTypes.INNER, " restaurant_product AS rp ", " ON p.id = rp.id_product")
							.generateQueryWhere(" rp.id_restaurant =  '" + s[0] + "'")
								.endGenerateSelect(psql, product);
			break;
			
			case 4:
				
				String first_sub_query 	= select(0);
				String second_sub_query = new AllergyProductDAO(new AllergyProduct()).select(0);
				String third_sub_query 	= new AllergyCustomerDAO(new AllergyCustomer()).select(0) + "'" + s[0] + "'";
				
				prod = (List<Product>)
				generateQuerySelect()
					.generateJoin(JoinTypes.INNER, "restaurant_product as rp", " ON rp.id_product = p.id ")
						.generateQueryWhere("p.id IN (" + 
														first_sub_query 
														+ 
														" EXCEPT "
														+  
														second_sub_query
														+
														"(" 
														+
														third_sub_query
														+
														")"
														+
														" GROUP BY ap.id "
														+
														") AND rp.id_restaurant = '"+s[1]+"' ")
															.endGenerateSelect(psql, product);
				
				/**
				 * La query espressa sopra
				 * è banalmente una rappresentazione 
				 * di questa qui sotto riportata
				 * 
				 * 
				SELECT p.id as id, p.name_product AS name_product,
				p.price AS price, p.vat_number AS vat_number 
				FROM product AS p
				INNER JOIN restaurant_product as rp 
				on rp.id_product = p.id              
				where p.id in (                               		 
			 	select 
				p.id                                         		  
                from product as p                            		  
                inner join allergyproduct as ap              		  
                on p.id = ap.id                              		  
                except                                       		 
                select                                       		  
                ap.id										 		 
                from allergyproduct as ap                    		 
                where ap.id_allergen in (                    		  
                select                                       		 
                ac.id_allergen                               		  
                from                                         		 
                allergycustomer as ac                        		  
                where ac.fiscal_code = 's[0]'            		  
                )                                            		 
                group by ap.id                               		  
                ) and rp.id_restaurant = 's[1]'
                *
                *
                *
                *
				**/
				
				
			break;
			
			case 5:
				prod = (List<Product>)generateQuerySelect()
						.generateJoin(JoinTypes.INNER, " restaurant_product AS rp ", " ON p.id = rp.id_product")
							.generateQueryWhere(" rp.id_restaurant =  '" + s[0] + "'" + " AND p.price between " + s[1] + " and " + s[2])
								.endGenerateSelect(psql, product);
			break;
			
            case 6:
				
				String first_sub_query_2 	= select(0);
				String second_sub_query_2 = new AllergyProductDAO(new AllergyProduct()).select(0);
				String third_sub_query_2 	= new AllergyCustomerDAO(new AllergyCustomer()).select(0) + "'" + s[0] + "'";
				
				prod = (List<Product>)
				generateQuerySelect()
					.generateJoin(JoinTypes.INNER, "restaurant_product as rp", " ON rp.id_product = p.id ")
						.generateQueryWhere("p.id IN (" + 
														first_sub_query_2
														+ 
														" EXCEPT "
														+  
														second_sub_query_2
														+
														"(" 
														+
														third_sub_query_2
														+
														")"
														+
														" GROUP BY ap.id "
														+
														") AND rp.id_restaurant = '"+s[1]+"' " + " AND p.price between " + s[2] + " and " + s[3])
															.endGenerateSelect(psql, product);
        	
		}

		return prod;
	}
	
	@Override
	public String select(int delta) {
		generateQuerySelect("p.id");
			//RIGA RIMOSSA PER PROBLEMI.generateJoin(JoinTypes.INNER, " allergyproduct AS ap ", " ON p.id = ap.id ");
		return QUERY;
	}

}
