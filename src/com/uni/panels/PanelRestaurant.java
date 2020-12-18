package com.uni.panels;

import java.awt.Color;
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
import javax.swing.JTextField;

import com.uni.frame.Form;
import com.uniproject.dao.RestaurantDAO;
import com.uniproject.dao.Restaurant_TypeDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Restaurant;
import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.jdbc.PostgreSQL;

public class PanelRestaurant implements PanelAttachInterface{

	// Valore selezionato della tipologia
	private int keyMapTipology = -1;
	
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		Form form = new Form();
		
		// Selezionare tutte le tipologie di ristoranti creati
		List<Restaurant_Tipology> restaurantType = (List<Restaurant_Tipology>)new Restaurant_TypeDAO(new Restaurant_Tipology()).select(0, psql);
		
		// Controlla se il numero di tipologie è > 0
		if(restaurantType.size() == 0) {
			JOptionPane.showMessageDialog(null, "Attenzione, registrare almeno una tipologia di ristorante!", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Genera mappa di associazione valore tipologia, codice tipologia
		Map<String, Integer> mapCodeValueTipology = new HashMap<>();
		for(Restaurant_Tipology rt : restaurantType) {
			mapCodeValueTipology.put(rt.getTipology(), rt.getId_tipology());
		}
		
		// Codice ristorante
		JLabel lbCode = new JLabel("CODICE RISTORANTE (*)");
		lbCode.setBounds(10, 10, 300, 40);
		lbCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JTextField fieldCode = new JTextField();
		fieldCode.setBounds(10, 60, 400, 40);
		fieldCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fieldCode.addFocusListener(focusListener);
		fieldCode.putClientProperty("pattern", "[A-Z]{1}[0-9]{4}");
		form.addToForm(fieldCode); // Aggiungi a form
		
		// Codice ristorante
		JLabel lbTipology = new JLabel("TIPOLOGIA RISTORANTE (*)");
		lbTipology.setBounds(480, 10, 300, 40);
		lbTipology.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		// Genera menu a tendina ciclando sulla mappa di chiave e valore
		JComboBox<String> comboBoxTipology = new JComboBox<>();
		comboBoxTipology.addItem("-- Tipologie --");
		for(Map.Entry<String, Integer> mapValue : mapCodeValueTipology.entrySet()) {
			comboBoxTipology.addItem(mapValue.getKey());
		}
		comboBoxTipology.setBounds(480, 60, 400, 40);
		comboBoxTipology.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!comboBoxTipology.getSelectedItem().equals("-- Tipologie --")) {
					keyMapTipology = mapCodeValueTipology.get(comboBoxTipology.getSelectedItem());
				}else {
					keyMapTipology = -1;
				}
			}
		});
		comboBoxTipology.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		// Codice ristorante
		JLabel lbName = new JLabel("NOME RISTORANTE (*)");
		lbName.setBounds(10, 110, 300, 40);
		lbName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JTextField fieldName = new JTextField();
		fieldName.setBounds(10, 160, 400, 40);
		fieldName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fieldName.setText("");
		fieldName.addFocusListener(focusListener);
		form.addToForm(fieldName); // Aggiungi a form
		
		// Città ristorante
		JLabel lbCity = new JLabel("CITTÀ RISTORANTE (*)");
		lbCity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbCity.setBounds(480, 110, 300, 40);
		
		JTextField fieldCity = new JTextField();
		fieldCity.setBounds(480, 160, 400, 40);
		fieldCity.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fieldCity.setText("");
		fieldCity.addFocusListener(focusListener);
		form.addToForm(fieldCity); // Aggiungi a form
		
		// Cap ristorante
		JLabel lbCap = new JLabel("CAP (*)");
		lbCap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbCap.setBounds(10, 210, 300, 40);
		
		JTextField fieldCap = new JTextField();
		fieldCap.setBounds(10, 260, 400, 40);
		fieldCap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fieldCap.setText("");
		fieldCap.addFocusListener(focusListener);
		fieldCap.putClientProperty("pattern", "[0-9]{5}");
		form.addToForm(fieldCap); // Aggiungi a form
		
		// Telefono ristorante
		JLabel lbPhone = new JLabel("TELEFONO (*)");
		lbPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbPhone.setBounds(480, 210, 300, 40);
		
		JTextField fieldPhone = new JTextField();
		fieldPhone.setBounds(480, 260, 400, 40);
		fieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fieldPhone.setText("");
		fieldPhone.addFocusListener(focusListener);
		fieldPhone.putClientProperty("pattern", "[0-9]{10}");
		fieldPhone.putClientProperty("nullable", true);
		form.addToForm(fieldPhone);
		
		// Indirizzo ristorante
		JLabel lbAddress = new JLabel("VIA/VICOLO.. (*)");
		lbAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbAddress.setBounds(10, 310, 300, 40);
		
		JComboBox<String> comboVia = new JComboBox<String>();
		comboVia.addItem("Via");
		comboVia.addItem("Vicolo");
		comboVia.addItem("Viale");
		comboVia.addItem("Piazza");
		comboVia.addItem("Piazzale");
		comboVia.addItem("Corso");
		comboVia.addItem("Largo");
		comboVia.addItem("Parco");
		comboVia.setBounds(10, 360, 870, 40);
		comboVia.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		// Indirizzo ristorante
		JLabel lbAddressRest = new JLabel("INDIRIZZO (*)");
		lbAddressRest.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbAddressRest.setBounds(10, 410, 300, 40);
		
		JTextField fieldAddress = new JTextField();
		fieldAddress.setBounds(10, 460, 870, 40);
		fieldAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fieldAddress.setText("");
		fieldAddress.addFocusListener(focusListener);
		form.addToForm(fieldAddress); /// Aggiungi a form
		
		JButton btnAddRestaurant = new JButton("AGGIUNGI RISTORANTE");
		btnAddRestaurant.setBounds(10, 510, 870, 40);
		btnAddRestaurant.setBackground(Color.orange);
		btnAddRestaurant.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAddRestaurant.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Controlla validazione!
				if(!form.validate()) {
					JOptionPane.showMessageDialog(null, "Attenzione, validare i campi evidenziati!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Controlla inputazione corretta dei campi
				if(!form.validateValueForm()) {
					JOptionPane.showMessageDialog(null, "Attenzione, validare i campi in modo corretto!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Scelta tipologia
				if(keyMapTipology == -1) {
					JOptionPane.showMessageDialog(null, "Attenzione, scegliere una tipologia!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Crea istanza di ristorante e salva
				Restaurant restaurant = new Restaurant();
				restaurant.setId_restaurant(fieldCode.getText());
				restaurant.setTipology(keyMapTipology);
				restaurant.setName(fieldName.getText());
				restaurant.setCity(fieldCity.getText());
				restaurant.setCap(fieldCap.getText());
				restaurant.setAddress(comboVia.getSelectedItem() + " " + fieldAddress.getText());
				restaurant.setPhone(fieldPhone.getText());
				psql.insertQuery(
					new RestaurantDAO(restaurant).insert(0, psql),
					new InterfaceSuccessErrorDAO() {
						
						@Override
						public void ok() {
							form.clearField();
							JOptionPane.showMessageDialog(null, "Inserimento tipologia avvenuto con successo!");
						}
						
						@Override
						public void err(String e) {
							if(e.startsWith("ERRORE: un valore chiave duplicato viola il vincolo univoco")) {
								e = "Il codice ristorante è già presente in archivio!";
							}else {
								e = "";
							}
							JOptionPane.showMessageDialog(null, e.equals("") ? "Inserimento tipologia fallito: " : e, "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}
				);
				
			}
		});
		
		context.add(lbCode);
		context.add(fieldCode);
		context.add(lbTipology);
		context.add(comboBoxTipology);
		context.add(lbName);
		context.add(fieldName);
		context.add(lbCity);
		context.add(fieldCity);
		context.add(lbCap);
		context.add(fieldCap);
		context.add(lbPhone);
		context.add(fieldPhone);
		context.add(lbAddress);
		context.add(comboVia);
		context.add(lbAddressRest);
		context.add(fieldAddress);
		context.add(btnAddRestaurant);
		
	}

}
