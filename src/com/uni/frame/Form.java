package com.uni.frame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

public class Form {

	// JTextField da validare
	private List<JTextField> listValidator = new ArrayList<>();
	
	/**
	 * 
	 * @return
	 */
	public boolean validate() {
		
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
	public void addToForm(JTextField field) {
		listValidator.add(field);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean validateValueForm() {
		boolean isValidated = true;
		for(JTextField field : listValidator) {
			if(field.getClientProperty("pattern") != null) {
				if(!field.getText().matches(field.getClientProperty("pattern").toString())) {
					field.setBackground(new Color(255, 51, 51));
					isValidated = false;
				}else {
					field.setBackground(Color.white);
				}
			}
			if(field.getClientProperty("tipology") != null) {
				if(field.getClientProperty("tipology").equals("numeric")) {
					try {
						Double.parseDouble(field.getText().replace(",", "."));
					}catch(Exception e) {
						field.setBackground(new Color(255, 51, 51));
						isValidated = false;
					}
				}else {
					field.setBackground(Color.white);
				}
			}
		}
		return isValidated;
	}
	
	/**
	 * 
	 */
	public void clearField() {
		for(JTextField field : listValidator) {
			field.setText("");
		}
	}
	
	/**
	 * 
	 */
	public void clearForm() {
		listValidator.clear();
	}
	
}
