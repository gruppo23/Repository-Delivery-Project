package com.uniproject.dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.uniproject.jdbc.PostgreSQL;

public class DaoEngine {

	// Query generata dal motore.
	protected String QUERY;
	
	// Classe generica DAO
	private Object genericDao;
	
	/**
	 * 
	 * @param genericDao
	 */
	public DaoEngine(Object genericDao) {
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
	 * @return
	 */
	protected DaoEngine generateQuerySelect() {
		
		try {

			// Recupera classe.
			Class<?> genericClass = genericDao.getClass();
			
			// Parte iniziale Query select
			QUERY = "SELECT ";
			
			// Cicla sui campo
			for(Field field : genericClass.getDeclaredFields()) {
				field.setAccessible(true);
				QUERY += field.getName() + ",";
			}
			
			// Splitta ultimo campo e aggiungi nome tabella
			QUERY  = QUERY.substring(0, QUERY.length() - 1);
			QUERY += " FROM " + genericClass.getSimpleName().toLowerCase();
			
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
	protected DaoEngine generateQueryWhere(String clausola) {
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
	protected DaoEngine generateJoin(JoinTypes type, Class<?> classJoin, String clausola) {
		QUERY += " " + type.toString() + " JOIN " + classJoin.getSimpleName() + " " + clausola;
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
