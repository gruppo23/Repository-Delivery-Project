package com.uniproject.dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.uniproject.jdbc.PostgreSQL;

public class EngineDAO {

	// Query generata dal motore.
	protected String QUERY;
	
	// Classe generica DAO
	private Object genericDao;
	
	/**
	 * 
	 * @param genericDao
	 */
	public EngineDAO(Object genericDao) {
		this.genericDao = genericDao;
	}
	
	/**
	 * Query di inserimento
	 * @param omittedIndex
	 */
	protected void generateQueryInsert(int ... omittedIndex) {
		
		try {

			// Recupera classe.
			Class<?> genericClass = genericDao.getClass();
			
			// Parte iniziale Query di insert
			QUERY = "INSERT INTO " + ((AliasTableDAO)genericClass.getDeclaredAnnotations()[0]).tableName() + " (";
			
			// Colonne e campi!
			String columns = "";
			String fields  = ") VALUES (";
			
			// Formazione query
			int counter = 0;
			for(Field field : genericClass.getDeclaredFields()) {
				if(omittedIndex.length == 0) {
					field.setAccessible(true);
					columns += field.getName() + ",";
					fields  += field.getType().getSimpleName().equals("String") ? "'" + field.get(genericDao) + "'," : field.get(genericDao) + ",";
				}else {
					if(omittedIndex[0] != counter) {
						field.setAccessible(true);
						columns += field.getName() + ",";
						fields  += field.getType().getSimpleName().equals("String") ? "'" + field.get(genericDao) + "'," : field.get(genericDao) + ",";	
					}
				}
				counter++;
			}
			
			// Completa query
			columns = columns.substring(0, columns.length() - 1);
			fields  = fields.substring(0, fields.length() - 1);
			QUERY  += columns + fields + ")";
			
			System.out.println(QUERY);
			
		}catch(Exception e) { 
			System.out.println("Errore generazione query: " + e); 
		}
		
	}
	
	/**
	 * 
	 * @param omittedIndex
	 */
	protected void generateQueryUpdate(int [] validIndexSet, int [] validIndexWhere) {
		
		try {
			
			// Recupera classe.
			Class<?> genericClass = genericDao.getClass();
			
			// Inizia query
			QUERY = "UPDATE " + genericClass.getSimpleName().toLowerCase() + " SET ";
			
			// Ottieni i campi
			Field[] fields = genericClass.getDeclaredFields();
			
			// Cicla sui campi dichiarati per il set
			String set = "";
			for(int index : validIndexSet) {
				fields[index].setAccessible(true);
				set += fields[index].getName() + " = " + (fields[index].getType().getSimpleName().toString().equals("String") ? "'" + fields[index].get(genericDao) + "'" : fields[index].get(genericDao).toString()) + ",";
			}
			set = set.substring(0, set.length() - 1);
			
			// Cicla per il where
			String where = " WHERE ";
			for(int index : validIndexWhere) {
				fields[index].setAccessible(true);
				where += fields[index].getName() + " = " + (fields[index].getType().getSimpleName().toString().equals("String") ? "'" + fields[index].get(genericDao) + "'" : fields[index].get(genericDao).toString()) + " AND ";
			}
			where = where.substring(0, where.length() - 4);
			
			// Completa query
			QUERY += set + where;
			
			System.out.println(QUERY);
			
		}catch(Exception e) {
			System.out.println("Errore generazione query: " + e); 
		}
		
	}
	
	/**
	 * 
	 * @param validIndex
	 */
	protected void generateQueryDelete(int [] validIndex) {
	
		try {
			
			// Recupera classe.
			Class<?> genericClass = genericDao.getClass();
			
			// Inizia query
			QUERY = "DELETE FROM " + genericClass.getSimpleName().toLowerCase() + " WHERE ";
			
			for(int index : validIndex) {
				Field [] fields = genericClass.getDeclaredFields();
				fields[index].setAccessible(true);
				QUERY += fields[index].getName() + " = "  + (fields[index].getType().getSimpleName().toString().equals("String") ? "'" + fields[index].get(genericDao) + "'" : fields[index].get(genericDao).toString());
				QUERY += " AND";
			}
			QUERY = QUERY.substring(0, QUERY.length() - 3);
			
		}catch(Exception e) {
			System.out.println("Errore generazione query: " + e); 
		}
		
	}
	

	/**
	 * 
	 * @return
	 */
	protected EngineDAO freeQuery(String query) {
		QUERY = query;
		return this;
	}
	
	protected EngineDAO generateQuerySelect(String fields, String alias, boolean ... isDistinct) {
		
		// Recupera classe.
		Class<?> genericClass = genericDao.getClass();
		
		// Parte iniziale Query select
		QUERY = "SELECT ";
		if(isDistinct.length > 0) {
			if(isDistinct[0])
				QUERY += "DISTINCT ";
		}
		QUERY += fields;
		QUERY += " FROM " + ((AliasTableDAO)genericClass.getDeclaredAnnotations()[0]).tableName() + " AS " + alias + " ";
		
		return this;
	}
	
	
	protected EngineDAO generateQuerySelect(String fields, boolean ... isDistinct) {
		
		// Recupera classe.
		Class<?> genericClass = genericDao.getClass();
		
		// Parte iniziale Query select
		QUERY = "SELECT ";
		if(isDistinct.length > 0) {
			if(isDistinct[0])
				QUERY += "DISTINCT ";
		}
		QUERY += fields;
		QUERY += " FROM " + ((AliasTableDAO)genericClass.getDeclaredAnnotations()[0]).tableName() + " AS " + ((AliasTableDAO)genericClass.getDeclaredAnnotations()[0]).alias() + " ";
		
		return this;
	}
	
	/**
	 * 
	 * @param isDistinct
	 * @return
	 */
	protected EngineDAO generateQuerySelect(boolean ... isDistinct) {
		
		try {

			// Recupera classe.
			Class<?> genericClass = genericDao.getClass();
			
			// Parte iniziale Query select
			QUERY = "SELECT ";
			if(isDistinct.length > 0) {
				if(isDistinct[0])
					QUERY += "DISTINCT ";
			}
				
			// Cicla sui campo
			for(Field field : genericClass.getDeclaredFields()) {
				field.setAccessible(true);
				QUERY += ((AliasFieldDAO)field.getDeclaredAnnotations()[0]).alias() + "." + field.getName() + " AS " + ((AliasFieldDAO)field.getDeclaredAnnotations()[0]).as() + ",";
			}
			
			// Splitta ultimo campo e aggiungi nome tabella
			QUERY  = QUERY.substring(0, QUERY.length() - 1);
			QUERY += " FROM " + ((AliasTableDAO)genericClass.getDeclaredAnnotations()[0]).tableName() + " AS " + ((AliasTableDAO)genericClass.getDeclaredAnnotations()[0]).alias() + " ";
			
		}catch(Exception e) {
			System.out.println("Errore generazione query: " + e); 
		}
		
		return this;
	}
	
	protected EngineDAO generateQueryLimit(int ... limit) {
		QUERY += " LIMIT " + (limit.length == 0 ? 1 : limit[0]);
		return this;
	}
	
	/**
	 * 
	 * @param clausola
	 * @return
	 */
	protected EngineDAO generateQueryWhere(String clausola) {
		QUERY += " WHERE " + clausola;
		return this;
	}
	
	/**
	 * 
	 * @param clausola
	 * @return
	 */
	protected EngineDAO generateQueryGroupBy(String clausola) {
		QUERY += " GROUP BY " + clausola;
		return this;
	}
	
	/**
	 * 
	 * @param fields
	 * @param type
	 * @return
	 */
	protected EngineDAO generateQueryOrderBy(String fields, String type) {
		QUERY += " ORDER BY " + fields + " " + type;
		return this;
	}
	
	/**
	 * 
	 * @param type
	 * @param classJoin
	 * @param clausola
	 * @return
	 */
	protected EngineDAO generateJoin(JoinTypes type, String classJoin, String clausola) {
		QUERY += " " + type.toString() + " JOIN " + classJoin + " " + clausola;
		return this;
	}
	
	/**
	 * 
	 * @param modelClass
	 * @return
	 */
	protected EngineDAO generateLike() {
		
		try {
			for(Field field : genericDao.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				QUERY += "(" + field.getName() + "::text LIKE '%"+ field.get(genericDao) +"%') OR";
			}
			QUERY = QUERY.substring(0, QUERY.length() - 2);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Si è verificato un errore in fase di selezione da parte del motore DaoEngine.java: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return this;
	}
	
	/**
	 * 
	 * @param psql
	 * @param modelClass
	 * @return
	 */
	protected List<?> endGenerateSelect(PostgreSQL psql, Object modelClass) {
		
		// Lista genera da costruire
		List<Object> generatedList = new ArrayList<>();
		
		try {
			
			// Esegui da postgre la query
			ResultSet rs = psql.selectQuery(QUERY);
			
			// Cicla sulle righe
			while(rs.next()) {
				
				Object model = modelClass.getClass().getDeclaredConstructor().newInstance();
				
				for(Field field : model.getClass().getDeclaredFields()) {
					try {
						field.setAccessible(true);
						field.set(model, rs.getObject(field.getName()));	
					}catch(Exception e) {
						System.out.println("Errore:" + e);
					}
				}
				generatedList.add(model);
				
			}
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Si è verificato un errore in fase di selezione da parte del motore DaoEngine.java: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return generatedList;
	}
	
}
