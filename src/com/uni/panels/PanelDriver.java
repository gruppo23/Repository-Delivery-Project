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
import javax.swing.JTextField;

import com.uni.frame.Form;
import com.uniproject.dao.DriversDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Drivers;
import com.uniproject.jdbc.PostgreSQL;

public class PanelDriver implements PanelAttachInterface{

	// Campi di resto
	private JTextField txtCodiceFiscale;
	private JTextField txtNome;
	private JTextField txtCognome;
	private JTextField txtCitta;
	private JTextField txtCap;
	private JTextField txtIndirizzo;
	private JTextField txtTelefono;
	
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		Form form = new Form();
		
		JLabel lbFiscalCode = new JLabel("CODICE FISCALE");
		lbFiscalCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbFiscalCode.setBounds(10, 11, 263, 44);
		
		JLabel lblNome = new JLabel("NOME");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNome.setBounds(10, 117, 263, 44);
		
		txtCodiceFiscale = new JTextField();
		txtCodiceFiscale.setBounds(10, 66, 615, 40);
		txtCodiceFiscale.setColumns(10);
		txtCodiceFiscale.putClientProperty("pattern", "^([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1})$|([0-9]{11})$\r\n");
		txtCodiceFiscale.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblCognome = new JLabel("COGNOME");
		lblCognome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCognome.setBounds(362, 117, 263, 44);
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(10, 172, 263, 40);
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		txtCognome = new JTextField();
		txtCognome.setColumns(10);
		txtCognome.setBounds(362, 172, 263, 40);
		txtCognome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblCitta = new JLabel("CITT\u00C0");
		lblCitta.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCitta.setBounds(10, 223, 263, 44);
		
		txtCitta = new JTextField();
		txtCitta.setColumns(10);
		txtCitta.setBounds(10, 278, 263, 40);
		txtCitta.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel  lblCap = new JLabel("CAP");
		lblCap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCap.setBounds(362, 223, 263, 44);
		
		txtCap = new JTextField();
		txtCap.setColumns(10);
		txtCap.setBounds(362, 278, 263, 40);
		txtCap.putClientProperty("pattern", "[0-9]{5}");
		txtCap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblIndirizzo = new JLabel("INDIRIZZO");
		lblIndirizzo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblIndirizzo.setBounds(10, 329, 263, 44);
		
		txtIndirizzo = new JTextField();
		txtIndirizzo.setColumns(10);
		txtIndirizzo.setBounds(10, 384, 263, 40);
		txtIndirizzo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel  lblTelefono = new JLabel("TELEFONO");
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTelefono.setBounds(362, 329, 263, 44);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(362, 384, 263, 40);
		txtTelefono.putClientProperty("pattern", "[0-9]{10}");
		txtTelefono.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnSalvaDriver = new JButton("SALVA DRIVER");
		btnSalvaDriver.setBackground(Color.ORANGE);
		btnSalvaDriver.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSalvaDriver.setBounds(10, 448, 615, 49);
		btnSalvaDriver.addActionListener(new ActionListener() {
			
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
				
				// Registra driver
				Drivers driver = new Drivers();
				driver.setFiscal_code(txtCodiceFiscale.getText());
				driver.setName(txtNome.getText());
				driver.setSurname(txtCognome.getText());
				driver.setCity(txtCitta.getText());
				driver.setCap(txtCap.getText());
				driver.setAddress(txtIndirizzo.getText());
				driver.setPhone(txtTelefono.getText());
				
				psql.insertQuery(new DriversDAO(driver).insert(0, psql), new InterfaceSuccessErrorDAO() {
					
					@Override
					public void ok() {
						form.clearField();
						JOptionPane.showMessageDialog(null, "Driver inserito con successo!");
					}
					
					@Override
					public void err(String e) {
						if(e.startsWith("ERRORE: un valore chiave duplicato viola il vincolo univoco")) {
							e = "Il driver è già presente in archivio!";
						}else {
							e = "";
						}
						JOptionPane.showMessageDialog(null, e.equals("") ? "Inserimento driver fallito: " : e, "Errore", JOptionPane.ERROR_MESSAGE);						
					}
				});
				
			}
		});

		// Aggiunta al form!
		form.addToForm(txtCodiceFiscale);
		form.addToForm(txtNome);
		form.addToForm(txtCap);
		form.addToForm(txtCognome);
		form.addToForm(txtCitta);
		form.addToForm(txtIndirizzo);
		form.addToForm(txtTelefono);
		
		txtCodiceFiscale.addFocusListener(focusListener);
		txtNome.addFocusListener(focusListener);
		txtCap.addFocusListener(focusListener);
		txtCognome.addFocusListener(focusListener);
		txtCitta.addFocusListener(focusListener);
		txtIndirizzo.addFocusListener(focusListener);
		txtTelefono.addFocusListener(focusListener);
		
		context.add(lbFiscalCode);
		context.add(lblNome);
		context.add(lblCognome);
		context.add(lblCap);
		context.add(lblCitta);
		context.add(lblIndirizzo);
		context.add(lblTelefono);
		context.add(txtCodiceFiscale);
		context.add(txtNome);
		context.add(txtCap);
		context.add(txtCognome);
		context.add(txtCitta);
		context.add(txtIndirizzo);
		context.add(txtTelefono);
		context.add(btnSalvaDriver);
		
	}
	
}
