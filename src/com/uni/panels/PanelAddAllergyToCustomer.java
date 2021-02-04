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
import com.uniproject.dao.CustomerDAO;
import com.uniproject.dao.AllergenDAO;
import com.uniproject.dao.AllergyCustomerDAO;

import com.uniproject.entity.Customer;
import com.uniproject.entity.Allergen;
import com.uniproject.entity.AllergyCustomer;
import com.uniproject.jdbc.PostgreSQL;


public class PanelAddAllergyToCustomer implements PanelAttachInterface{

	// Valore selezionato del cliente
	private String keyMapValueName_Customer = "noname";
	
	// Valore selezionato dell'allergia
	private String keyMapAllergen = "no";
	
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		Form form = new Form();
		
		// Selezionare tutti i clienti creati
		List<Customer> customerId = (List<Customer>)new CustomerDAO(new Customer()).select(0, psql);
		
		// Controlla se il numero di clienti è > 0
		if(customerId.size() == 0) {
			JOptionPane.showMessageDialog(null, "Attenzione, registrare almeno un cliente!", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Genera mappa di associazione nome cliente, cf cliente
		Map<String, String> mapCodeValueName_Customer = new HashMap<>();
		for(Customer c : customerId) {
			mapCodeValueName_Customer.put(c.getName().concat(" ").concat(c.getSurname()).concat(" (").concat(c.getFiscal_code()).concat(")"), c. getFiscal_code());
		}
		
		// Nome cliente
		JLabel lbNameCustomer = new JLabel("CLIENTE (*)");
		lbNameCustomer.setBounds(10, 10, 300, 40);
		lbNameCustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
				
		// Genera menu a tendina ciclando sulla mappa di chiave e valore
		JComboBox<String> comboBoxNameCustomer = new JComboBox<>();
		comboBoxNameCustomer.addItem("-- Cliente --");
		for(Map.Entry<String, String> mapValue : mapCodeValueName_Customer.entrySet()) {
			comboBoxNameCustomer.addItem(mapValue.getKey());
		}
		comboBoxNameCustomer.setBounds(10, 60, 400, 40);
		comboBoxNameCustomer.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!comboBoxNameCustomer.getSelectedItem().equals("-- Cliente --")) {
					keyMapValueName_Customer = mapCodeValueName_Customer.get(comboBoxNameCustomer.getSelectedItem());
				}else {
			       keyMapValueName_Customer.equals("noname");
				}
			}
		});
		comboBoxNameCustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		// Selezionare tutte le tipologie di allergie
		List<Allergen> allerg = (List<Allergen>)new AllergenDAO(new Allergen()).select(0, psql);
		
		// Genera mappa di associazione valore tipologia, codice tipologia
		Map<String, String> mapCodeValueAllergen = new HashMap<>();
		for(Allergen all : allerg) {
			mapCodeValueAllergen.put(all.getName_allergen(), all.getId_allergen());
		}	
				
		// Nome allergia
		JLabel lbAllergen = new JLabel("ALLERGIA (*)");
		lbAllergen.setBounds(480, 10, 300, 40);
		lbAllergen.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		// Genera menu a tendina ciclando sulla mappa di chiave e valore
		JComboBox<String> comboBoxAllergen = new JComboBox<>();
		comboBoxAllergen.addItem("-- Allergie --");
		for(Map.Entry<String, String> mapValue : mapCodeValueAllergen.entrySet()) {
			comboBoxAllergen.addItem(mapValue.getKey());
		}
		comboBoxAllergen.setBounds(480, 60, 400, 40);
		comboBoxAllergen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!comboBoxAllergen.getSelectedItem().equals("-- Allergie --")) {
					keyMapAllergen = mapCodeValueAllergen.get(comboBoxAllergen.getSelectedItem());
				}else {
					keyMapAllergen = "no";
				}
			}
		});
		comboBoxAllergen.setFont(new Font("Tahoma", Font.PLAIN, 20));

		
		JButton btnAddAllergy = new JButton("REGISTRA ALLERGIA A CLIENTE");
		btnAddAllergy.setBounds(10, 110, 870, 40);
		btnAddAllergy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddAllergy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Scelta cliente controllo 1
				if(keyMapValueName_Customer.equals("noname") || keyMapAllergen.equals("no")) {
					if(keyMapValueName_Customer.equals("noname") && keyMapAllergen.equals("no")) {
						JOptionPane.showMessageDialog(null, "Attenzione, selezionare un cliente ed un'allergia!", "Errore", JOptionPane.ERROR_MESSAGE);
					}
					else if (keyMapValueName_Customer.equals("noname")) {
						JOptionPane.showMessageDialog(null, "Attenzione, selezionare un cliente!", "Errore", JOptionPane.ERROR_MESSAGE);
						System.out.println(keyMapAllergen);
						System.out.println(keyMapValueName_Customer);
					}
					else if (keyMapAllergen.equals("no")) {
						JOptionPane.showMessageDialog(null, "Attenzione, selezionare un'allergia!", "Errore", JOptionPane.ERROR_MESSAGE);
					}
					return;
				}
				
				// Crea collegamento tra allergia e cliente e salva
				AllergyCustomer allergycustomer = new AllergyCustomer();
				allergycustomer.setFiscal_code(keyMapValueName_Customer);
				allergycustomer.setId_allergen(keyMapAllergen);
				
				    psql.insertQuery(new AllergyCustomerDAO(allergycustomer).insert(0, psql),new InterfaceSuccessErrorDAO() {
				    	
						@Override
						public void ok() {
							comboBoxAllergen.setSelectedIndex(0);
							comboBoxNameCustomer.setSelectedIndex(0);
							form.clearField();
							JOptionPane.showMessageDialog(null, "Inserimento avvenuto con successo!");
						}
						
						@Override
						public void err(String e) {
							if(e.startsWith("ERRORE: un valore chiave duplicato viola il vincolo univoco")) {
								e = "Allergia per questo cliente già presente in archivio!";
							}else {
								e = "";
							}
							JOptionPane.showMessageDialog(null, e.equals("") ? "Inserimento allergia per questo cliente fallito: " : e, "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}
				);
				
			}
		});
		
		context.add(lbNameCustomer);
		context.add(comboBoxNameCustomer);
		context.add(lbAllergen);
		context.add(comboBoxAllergen);
		context.add(btnAddAllergy);
		
	}

}
