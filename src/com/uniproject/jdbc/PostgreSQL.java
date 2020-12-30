package com.uniproject.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JOptionPane;

import com.sun.jdi.Type;
import com.uniproject.dao.InterfaceSuccessErrorDAO;

public class PostgreSQL {

	// Variabile di connessione.
	private Connection CONNECTION_JDBC;
	
	// Attributi di connessione
	private String host;
	private String port;
	private String db;
	private String username;
	private String password;
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param db
	 * @param username
	 * @param password
	 */
	public PostgreSQL(String host, 
					  String port, 
					  String db, 
					  String username, 
					  String password) {
		
		this.host 		= host;
		this.port 		= port;
		this.db   		= db;
		this.username 	= username;
		this.password 	= password;
		
	}
	
	/**
	 * Apertura connessione.
	 * @return
	 */
	public PostgreSQL openConnection() {
		
		try {
			
			// Apertura della connessione!
			Class.forName("org.postgresql.Driver");
			CONNECTION_JDBC = DriverManager
							  .getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db, 
										     username, 
											 password);
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Errore di connessione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return this;
	}
	
	/**
	 * Chiusura connessione!
	 */
	public void closeConnection() {
		try {
			if(CONNECTION_JDBC != null) {
				if(!CONNECTION_JDBC.isClosed()) {
					CONNECTION_JDBC.close();
				}
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Errore di chiusura: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isClosedConnection() {
		try {
			return CONNECTION_JDBC.isClosed();
		}catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public PostgreSQL insertQuery(String query, InterfaceSuccessErrorDAO interfaceSuccessErrorDao) {
		
		try {
			Statement stm = CONNECTION_JDBC.createStatement();
			stm.executeUpdate(query);
			interfaceSuccessErrorDao.ok();
		}catch(Exception e) {
			interfaceSuccessErrorDao.err(e.getMessage());
		}
		
		return this;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public PostgreSQL updateQuery(String query, InterfaceSuccessErrorDAO interfaceSuccessErrorDao) {
		
		try {
			Statement stm = CONNECTION_JDBC.createStatement();
			stm.executeUpdate(query);
			interfaceSuccessErrorDao.ok();
		}catch(Exception e) {
			interfaceSuccessErrorDao.err(e.getMessage());
		}
		
		return this;
	}	
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public PostgreSQL deleteQuery(String query, InterfaceSuccessErrorDAO interfaceSuccessErrorDao) {
		
		try {
			Statement stm = CONNECTION_JDBC.createStatement();
			stm.executeUpdate(query);
			interfaceSuccessErrorDao.ok();
		}catch(Exception e) {
			interfaceSuccessErrorDao.err(e.getMessage());
		}
		
		return this;
	}	
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResultSet selectQuery(String query) {
		
		// Oggetto che ospita risultato query
		ResultSet rs = null;
		
		try {
			Statement stm = CONNECTION_JDBC.createStatement();
			rs = stm.executeQuery(query);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Errore selezione: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
		return rs;
	}
	
	/**
	 * 
	 * @param procedureName
	 * @param parameters
	 * @return
	 */
	public CallableStatement callProcedure(String procedureName, String ... parameters) {
		
		CallableStatement proc = null;
		try {
			
			String paramsList = "";
			if(parameters.length > 0) {
				for(String p : parameters) {
					paramsList += p + ",";
				}
				paramsList = paramsList.substring(0, paramsList.length() - 1);
			}
			
			proc = CONNECTION_JDBC.prepareCall("CALL " + procedureName + "(" + paramsList + ")");
			
		}catch(Exception e) {
			
		}
		
		return proc;
	}
	
}
