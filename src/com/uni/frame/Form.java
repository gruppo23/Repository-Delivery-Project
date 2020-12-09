package com.uni.frame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

public class Form {

	// JTextField da validare
	private static List<JTextField> listValidator = new ArrayList<>();
	
	/**
	 * 
	 * @return
	 */
	public static boolean validate() {
		
		// Variabile che gestisce la validazione
		boolean isValidated = true;
		
		// Controlla validazione
		for(JTextField field : listValidator) {
			if(field.getText().isEmpty()) {
				field.setBackground(new Color(255, 51, 51));
				isValidated = false;
			}else {
				field.setBackground(Color.white);
			}
		}
		
		return isValidated;
	}
	
	/**
	 * 
	 * @param field
	 */
	public static void addToForm(JTextField field) {
		listValidator.add(field);
	}
	
	/**
	 * 
	 */
	public static void clearForm() {
		listValidator.clear();
	}
	
}
