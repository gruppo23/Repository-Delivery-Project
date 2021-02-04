package com.uni.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.uni.frame.Form;
import com.uniproject.dao.Restaurant_TypeDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.jdbc.PostgreSQL;

public class PanelRestaurantTipology implements PanelAttachInterface{

	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		Form form = new Form();
		
		// Tipologia ristorante
		JLabel lbTipology = new JLabel("TIPOLOGIA RISTORANTE (*)");
		lbTipology.setBounds(10, 10, 600, 40);
		lbTipology.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JTextField fieldTipology = new JTextField();
		fieldTipology.setBounds(10, 60, 450, 40);
		fieldTipology.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fieldTipology.addFocusListener(focusListener);
		form.addToForm(fieldTipology); // Aggiungi a form
		
		// Tipologia descrizione
		JLabel lbDesc = new JLabel("DESCRIZIONE (250)");
		lbDesc.setBounds(10, 110, 600, 40);
		lbDesc.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JTextArea fieldDesc = new JTextArea();
		fieldDesc.setBounds(10, 160, 450, 200);
		fieldDesc.setFont(new Font("Tahoma", Font.PLAIN, 20));
		fieldDesc.addFocusListener(focusListener);
		
		// Bottone creazione
		JButton btnCreate = new JButton("Crea tipologia");
		btnCreate.setBounds(10, 370, 450, 40);
		btnCreate.setBackground(Color.orange);
		btnCreate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCreate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Controlla validazione!
				if(!form.validate()) {
					JOptionPane.showMessageDialog(null, "Attenzione, validare i campi evidenziati!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Controlla che ci sia connessione al db
				if(psql.isClosedConnection()) {
					JOptionPane.showMessageDialog(null, "Attenzione, connessione al db assente!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Crea istanza di tipologia menu e salva
				Restaurant_Tipology restaurant_Tipology = new Restaurant_Tipology();
				restaurant_Tipology.setTipology(fieldTipology.getText());
				restaurant_Tipology.setDescription(fieldDesc.getText());
				psql.insertQuery(
					new Restaurant_TypeDAO(restaurant_Tipology).insert(0, psql),
					new InterfaceSuccessErrorDAO() {
						
						@Override
						public void ok() {
						   fieldDesc.setText(null);
						   form.clearField();
							JOptionPane.showMessageDialog(null, "Inserimento tipologia avvenuto con successo!");
						}
						
						@Override
						public void err(String e) {
							if(e.startsWith("ERRORE: un valore chiave duplicato viola il vincolo univoco")) {
								e = "La tipologia è già presente in archivio!";
							}else {
								e = "";
							}
							JOptionPane.showMessageDialog(null, e.equals("") ? "Inserimento tipologia fallito: " : e, "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}
				);
				
			}
		});
		
		context.add(lbTipology);
		context.add(fieldTipology);
		context.add(lbDesc);
		context.add(fieldDesc);
		context.add(btnCreate);
		
	}

}
