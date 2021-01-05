package com.uni.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class SingletonComuni {

	private static SingletonComuni singletonComuni;
	private static HashMap<String, String> comuneCodiceResidenziale;
	private static HashMap<String, String> comuneCap;
	
	private SingletonComuni() {
		comuneCodiceResidenziale = new HashMap<>();
		comuneCap                = new HashMap<>();
	}
	
	/**
	 * 
	 * @return
	 */
	public static SingletonComuni  getInstance() {
		if(singletonComuni == null) {
			singletonComuni = new SingletonComuni();
		}
		return singletonComuni;
	}
	
	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getComuneCodiceResidenziale(){
		return comuneCodiceResidenziale;
	}
	
	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getComuneCap(){
		return comuneCap;
	}
	
	/**
	 * 
	 */
	public void loadComuni() {
		
		BufferedReader br = null;
		try {
			
			File fileComuni   = new File(getClass().getClassLoader().getResource("listacomuni.txt").getPath());
			br 				  = new BufferedReader(new FileReader(fileComuni));
			
			String line = br.readLine();
			while(line != null) {
				comuneCodiceResidenziale.put(line.split(";")[0], line.split(";")[2]);
				comuneCap.put(line.split(";")[0], line.split(";")[1]);
				line = br.readLine();
			}
			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Errore caricamento comuni: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
		}finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {}
		}
		
	}
	
}
