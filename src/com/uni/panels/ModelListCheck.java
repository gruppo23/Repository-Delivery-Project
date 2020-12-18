package com.uni.panels;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ModelListCheck {
	
	// Lista di checkbox
	private List<JCheckBox> boxes;
	
	// Dimensione pannello
	private int width;
	
	// Incremento di y
	private int y = 0;
	
	// Proprietà generica elemento
	private String genericProp;
	
	/**
	 * 
	 * @param width
	 */
	public ModelListCheck(int width) {
		this.width 	= width;
		boxes 		= new ArrayList<>();
	}
	
	/**
	 * 
	 * @param setted
	 * @param value
	 * @param prop
	 * @param valProp
	 */
	public void add(boolean setted, String value, Object valProp) {
		JCheckBox box = new JCheckBox(value);
		box.putClientProperty(genericProp, valProp);
		box.setBounds(10, y, width, 30);
		box.setSelected(setted);
		boxes.add(box);
		y += 30;
	}
	
	/**
	 * 
	 * @param genericProp
	 */
	public void setGenericProp(String genericProp) {
		this.genericProp = genericProp;
	}
	
	/**
	 * 
	 * @return
	 */
	public JScrollPane build(int x, int y, int width, int height) {
		
		// Aggiungi al pannello
		JPanel panel = new JPanel(null);
		panel.setPreferredSize(new Dimension(width, this.y));
		for(JCheckBox box : boxes) {
			panel.add(box);
		}
		
		// Scroll pane!
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(x, y, width, height);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		return scrollPane;
	}
	
	/**
	 * 
	 */
	public void reset() {
		for(JCheckBox box : boxes) {
			box.setSelected(false);
		}
	}
	
	/**
	 * 
	 * @param value
	 * @param up
	 */
	public void update(Object value, boolean up) {
		for(JCheckBox box : boxes) {
			if(box.getClientProperty(genericProp).equals(value)) {
				box.setSelected(up);
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Object> getResult() {
		
		// Risultato
		List<Object> res = new ArrayList<>();
		
		// Cicla sui campi e fai ritornare la situazione!
		for(JCheckBox box : boxes) {
			res.add(
				new Object[] {
					box.isSelected(),
					box.getText(),
					box.getClientProperty(genericProp)
				}
			);
		}
		
		return res;
	}
	
}
