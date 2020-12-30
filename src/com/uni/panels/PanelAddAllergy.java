package com.uni.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.uni.frame.Form;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.dao.ProductDAO;
import com.uniproject.dao.AllergenDAO;
import com.uniproject.dao.AllergyProductDAO;

import com.uniproject.entity.Product;
import com.uniproject.entity.Allergen;
import com.uniproject.entity.AllergyProduct;
import com.uniproject.jdbc.PostgreSQL;


public class PanelAddAllergy implements PanelAttachInterface{

	// Valore selezionato del prodotto
	private int keyMapValueName_Product = -1;
	
	// Valore selezionato della tipologia
	private String keyMapAllergen = "no";
	
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		Form form = new Form();
		
		// Selezionare tutte le tipologie di prodotti creati
		List<Product> productType = (List<Product>)new ProductDAO(new Product()).select(0, psql);
		
		// Controlla se il numero di prodotti è > 0
		if(productType.size() == 0) {
			JOptionPane.showMessageDialog(null, "Attenzione, registrare almeno un prodotto!", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Genera mappa di associazione valore prodotto, codice prodotto
		Map<String, Integer> mapCodeValueName_Product = new HashMap<>();
		for(Product p : productType) {
			mapCodeValueName_Product.put(p.getName(), p. getId());
		}
		
		// Nome prodotto
		JLabel lbNameProduct = new JLabel("NOME PRODOTTO (*)");
		lbNameProduct.setBounds(10, 10, 300, 40);
		lbNameProduct.setFont(new Font("Tahoma", Font.PLAIN, 20));
				
		// Genera menu a tendina ciclando sulla mappa di chiave e valore
		JComboBox<String> comboBoxNameProduct = new JComboBox<>();
		comboBoxNameProduct.addItem("-- Prodotti --");
		for(Map.Entry<String, Integer> mapValue : mapCodeValueName_Product.entrySet()) {
			comboBoxNameProduct.addItem(mapValue.getKey());
		}
		comboBoxNameProduct.setBounds(10, 60, 400, 40);
		comboBoxNameProduct.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!comboBoxNameProduct.getSelectedItem().equals("-- Prodotti --")) {
					keyMapValueName_Product = mapCodeValueName_Product.get(comboBoxNameProduct.getSelectedItem());
				}else {
			       keyMapValueName_Product = -1;
				}
			}
		});
		comboBoxNameProduct.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		// Selezionare tutte le tipologie di allergeni
		List<Allergen> allerg = (List<Allergen>)new AllergenDAO(new Allergen()).select(0, psql);
		
		// Genera mappa di associazione valore tipologia, codice tipologia
		Map<String, String> mapCodeValueAllergen = new HashMap<>();
		for(Allergen all : allerg) {
			mapCodeValueAllergen.put(all.getName_allergen(), all.getId_allergen());
		}	
				
		// Nome allergene
		JLabel lbAllergen = new JLabel("ALLERGENE PRESENTE (*)");
		lbAllergen.setBounds(480, 10, 300, 40);
		lbAllergen.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		// Genera menu a tendina ciclando sulla mappa di chiave e valore
		JComboBox<String> comboBoxAllergen = new JComboBox<>();
		comboBoxAllergen.addItem("-- Allergeni --");
		for(Map.Entry<String, String> mapValue : mapCodeValueAllergen.entrySet()) {
			comboBoxAllergen.addItem(mapValue.getKey());
		}
		comboBoxAllergen.setBounds(480, 60, 400, 40);
		comboBoxAllergen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!comboBoxAllergen.getSelectedItem().equals("-- Allergeni --")) {
					keyMapAllergen = mapCodeValueAllergen.get(comboBoxAllergen.getSelectedItem());
				}else {
					keyMapAllergen = "no";
				}
			}
		});
		comboBoxAllergen.setFont(new Font("Tahoma", Font.PLAIN, 20));

		
		JButton btnAddAllergy = new JButton("REGISTRA ALLERGENE A PRODOTTO");
		btnAddAllergy.setBounds(10, 110, 870, 40);
		btnAddAllergy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddAllergy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Scelta prodotto controllo 1
				if(keyMapValueName_Product == -1 || keyMapAllergen.equals("no")) {
					if(keyMapValueName_Product == -1 && keyMapAllergen.equals("no")) {
						JOptionPane.showMessageDialog(null, "Attenzione, selezionare un prodotto ed un allergene!", "Errore", JOptionPane.ERROR_MESSAGE);
					}
					else if (keyMapValueName_Product == -1) {
						JOptionPane.showMessageDialog(null, "Attenzione, selezionare un prodotto!", "Errore", JOptionPane.ERROR_MESSAGE);
						System.out.println(keyMapAllergen);
						System.out.println(keyMapValueName_Product);
					}
					else if (keyMapAllergen.equals("no")) {
						JOptionPane.showMessageDialog(null, "Attenzione, selezionare un allergene!", "Errore", JOptionPane.ERROR_MESSAGE);
					}
					return;
				}
				
				// Crea collegamento tra allergia e prodotto e salva
				AllergyProduct allergyproduct = new AllergyProduct();
				allergyproduct.setId(keyMapValueName_Product);
				allergyproduct.setId_allergen(keyMapAllergen);
				
				    psql.insertQuery(new AllergyProductDAO(allergyproduct).insert(0, psql),new InterfaceSuccessErrorDAO() {
				    	
						@Override
						public void ok() {
							form.clearField();
							JOptionPane.showMessageDialog(null, "Inserimento avvenuto con successo!");
						}
						
						@Override
						public void err(String e) {
							if(e.startsWith("ERRORE: un valore chiave duplicato viola il vincolo univoco")) {
								e = "Allergene per questo prodotto già presente in archivio!";
							}else {
								e = "";
							}
							JOptionPane.showMessageDialog(null, e.equals("") ? "Inserimento allergene per questo prodotto fallito: " : e, "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}
				);
				
			}
		});
		
		context.add(lbNameProduct);
		context.add(comboBoxNameProduct);
		context.add(lbAllergen);
		context.add(comboBoxAllergen);
		context.add(btnAddAllergy);
		
	}

}
