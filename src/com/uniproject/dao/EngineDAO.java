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
			QUERY = "INSERT INTO " + genericClass.getSimpleName().toLowerCase() + " (";
			
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
			
		}catch(Exception e) { 
			System.out.println("Errore generazione query: " + e); 
		}
		
	}
	
	/**
	 * 
	 * @param omittedIndex
	 */
	protected void generateQueryUpdate(int ... omittedIndex) {
		
		try {
			
			// Recupera classe.
			Class<?> genericClass = genericDao.getClass();
			
			// Inizia query
			QUERY = "UPDATE " + genericClass.getSimpleName().toLowerCase() + " SET ";
			
			// Cicla sui campi dichiarati
			String key = "";
			for(Field field : genericClass.getDeclaredFields()) {
				field.setAccessible(true);
				if(field.getDeclaredAnnotations().length == 1)
					QUERY += field.getName().toLowerCase() + " = " + (field.getType().getSimpleName().toString().equals("String") ? "'" + field.get(genericDao) + "'" : field.get(genericDao).toString()) + ",";
				else
					key = field.getName().toLowerCase() + " = " + (field.getType().getSimpleName().toString().equals("String") ? "'" + field.get(genericDao) + "'" : field.get(genericDao).toString());
			}
			QUERY = QUERY.substring(0, QUERY.length() - 1) + " WHERE " + key;
			
		}catch(Exception e) {
			System.out.println("Errore generazione query: " + e); 
		}
		
	}
	
	/**
	 * 
	 * @param valueExclusion
	 */
	protected void generateQueryDelete(int valueExclusion) {
	
		try {
			
			// Recupera classe.
			Class<?> genericClass = genericDao.getClass();
			
			// Inizia query
			QUERY = "DELETE FROM " + genericClass.getSimpleName().toLowerCase() + " WHERE ";
			for(Field field : genericClass.getDeclaredFields()) {
				field.setAccessible(true);
				if(field.get(genericDao) != null) {
					if(!field.getType().getSimpleName().equals("int")) {
						QUERY += field.getName().toLowerCase() + " = " + (field.getType().getSimpleName().toString().equals("String") ? "'" + field.get(genericDao) + "'" : field.get(genericDao).toString()) + " AND ";
					}else {
						if(field.getInt(genericDao) != valueExclusion) {
							QUERY += field.getName().toLowerCase() + " = " + (field.getType().getSimpleName().toString().equals("String") ? "'" + field.get(genericDao) + "'" : field.get(genericDao).toString()) + " AND ";
						}
					}
				}
			}
			QUERY = QUERY.substring(0, QUERY.length() - 4);
			System.out.println(QUERY);
			
		}catch(Exception e) {
			System.out.println("Errore generazione query: " + e); 
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	protected EngineDAO generateQuerySelect() {
		
		try {

			// Recupera classe.
			Class<?> genericClass = genericDao.getClass();
			
			// Parte iniziale Query select
			QUERY = "SELECT ";
			
			// Cicla sui campo
			for(Field field : genericClass.getDeclaredFields()) {
				field.setAccessible(true);
				QUERY += ((AliasFieldDAO)field.getDeclaredAnnotations()[0]).alias() + "." + field.getName() + " AS " + ((AliasFieldDAO)field.getDeclaredAnnotations()[0]).as() + ",";
			}
			
			// Splitta ultimo campo e aggiungi nome tabella
			QUERY  = QUERY.substring(0, QUERY.length() - 1);
			QUERY += " FROM " + ((AliasTableDAO)genericClass.getDeclaredAnnotations()[0]).tableName() + " AS " + ((AliasTableDAO)genericClass.getDeclaredAnnotations()[0]).alias() + " ";
			
			System.out.println(QUERY);
			
		}catch(Exception e) {
			System.out.println("Errore generazione query: " + e); 
		}
		
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
	 * @param psql
	 * @param modelClass
	 * @return
	 */
	protected List<?> endGenerateSelect(PostgreSQL psql, Object modelClass) {
		
		// Lista genera da costruire
		List<Object> generatedList = new ArrayList<>();
		
		try {
			
			System.out.println(QUERY);
			
			// Esegui da postgre la query
			ResultSet rs = psql.selectQuery(QUERY);
			
			// Cicla sulle righe
			while(rs.next()) {
				
				Object model = modelClass.getClass().getDeclaredConstructor().newInstance();
				
				for(Field field : model.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					field.set(model, rs.getObject(field.getName()));
				}
				generatedList.add(model);
				
			}
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Si è verificato un errore in fase di selezione da parte del motore DaoEngine.java: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return generatedList;
	}
	
}
