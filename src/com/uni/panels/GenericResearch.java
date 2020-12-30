package com.uni.panels;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;


public class GenericResearch {

	private Object model;
	
	public GenericResearch(Object model) {
		this.model = model;
	}
	
	public void appendElement(JPanel panel, int x, int y, IGet iGet) {
		JTextField fieldResearch = new JTextField();
		fieldResearch.setSize(new Dimension(300, 40));
		fieldResearch.setLocation(x, y);
		fieldResearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				iGet.put(fieldResearch.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				iGet.put(fieldResearch.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				iGet.put(fieldResearch.getText());
			}
		});
		panel.add(fieldResearch);
	}
	
	/**
	 * 
	 * @param table
	 * @param columns
	 * @param rows
	 * @return
	 */
	public void getNewModel(JTable table, Object [] columns, Object[][] rows) {
		DefaultTableModel dfm = new DefaultTableModel(rows, columns);
		table.setModel(dfm);
	}
	
	public Object invokeSet(String value) throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		for(Method method : model.getClass().getDeclaredMethods()) {
			if(method.getName().toLowerCase().startsWith("set")) {
				if(!method.getGenericParameterTypes()[0].getTypeName().contains("String")) {
					switch(method.getGenericParameterTypes()[0].getTypeName().split(".")[2].toLowerCase()) {
						
						// int
						case "int":
							method.invoke(model, Integer.parseInt(value));
						break;
						
						// double
						case "double":
							method.invoke(model, Double.parseDouble(value));
						break;
						
						// boolean
						case "boolean":
							method.invoke(model, Boolean.parseBoolean(value));
						break;
						
					}
				}else {
					method.invoke(model, value);
				}
			}
		}
		
		return model;
	}

	
}
