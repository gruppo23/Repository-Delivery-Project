package com.uni.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.uni.frame.Form;
import com.uni.logic.FiscalCode;
import com.uni.logic.SingletonComuni;
import com.uniproject.dao.DriverDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Driver;
import com.uniproject.jdbc.PostgreSQL;

public class PanelDriver implements PanelAttachInterface{

	// Campi di testo
	private JTextField txtCodiceFiscale;
	private JTextField txtNome;
	private JTextField txtCognome;
	private JComboBox<String> txtCitta;
	private JTextField txtCap;
	private JComboBox<String> comboMezzo;
	private JTextField txtTelefono;
	private JComboBox<String> comboGender;
	private JTextField txtDate;
	
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		Form form = new Form();
		Form formCf = new Form();
		
		// Aggiungi cittÃ 
		SingletonComuni instance = SingletonComuni.getInstance();
		
		JLabel lbFiscalCode = new JLabel("CODICE FISCALE");
		lbFiscalCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbFiscalCode.setBounds(10, 11, 263, 44);
		
		JLabel lblNome = new JLabel("NOME");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNome.setBounds(10, 117, 263, 44);
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBounds(10, 172, 263, 40);
		txtNome.putClientProperty("pattern", "\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+");
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblCognome = new JLabel("COGNOME");
		lblCognome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCognome.setBounds(362, 117, 263, 44);
		
		txtCognome = new JTextField();
		txtCognome.setColumns(10);
		txtCognome.setBounds(362, 172, 263, 40);
		txtCognome.putClientProperty("pattern", "\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+");
		txtCognome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		txtCodiceFiscale = new JTextField();
		txtCodiceFiscale.setBounds(10, 66, 615, 40);
		txtCodiceFiscale.setColumns(10);
		txtCodiceFiscale.putClientProperty("pattern", "^([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1})$|([0-9]{11})$\r\n");
		txtCodiceFiscale.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton calcCf = new JButton("CALCOLA");
		calcCf.setBackground(Color.ORANGE);
		calcCf.setBounds(655, 66, 170, 40);
		calcCf.setFont(new Font("Tahoma", Font.PLAIN, 20));
		calcCf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Controlla validazione!
				if(!formCf.validate()) {
					JOptionPane.showMessageDialog(null, "Attenzione, validare i campi evidenziati!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Controlla inputazione corretta dei campi
				if(!formCf.validateValueForm()) {
					JOptionPane.showMessageDialog(null, "Attenzione, validare i campi in modo corretto!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				txtCodiceFiscale.setText(
					new FiscalCode(txtNome.getText(), 
								   txtCognome.getText(), 
								   txtDate.getText(), 
								   txtCitta.getSelectedItem().toString(), 
								   comboGender.getSelectedItem().toString()).getFiscalCode()
				);
			}
		});
		
		
		JLabel lblGender = new JLabel("GENERE");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGender.setBounds(714, 117, 263, 44);
		
		comboGender = new JComboBox<String>();
		comboGender.addItem("M");
		comboGender.addItem("F");
		comboGender.setBounds(714, 172, 150, 40);
		comboGender.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		txtDate = new JTextField();
		txtDate.setColumns(10);
		txtDate.setBounds(362, 384, 263, 40);
		txtDate.putClientProperty("pattern", "[0-9]{10}");
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblCitta = new JLabel("CITT\u00C0 DI NASCITA");
		lblCitta.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCitta.setBounds(10, 223, 263, 44);
		
		txtCitta = new JComboBox<String>();
		txtCitta.setBounds(10, 278, 263, 40);
		txtCitta.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		for(Map.Entry<String, String> map : instance.getComuneCap().entrySet()) {
			txtCitta.addItem(map.getKey());
		}
		
		txtCitta.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtCap.setText(instance.getComuneCap().get(txtCitta.getSelectedItem()));
			}
		});
		
		JLabel  lblCap = new JLabel("CAP");
		lblCap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCap.setBounds(362, 223, 263, 44);
		
		txtCap = new JTextField();
		txtCap.setColumns(10);
		txtCap.setBounds(362, 278, 263, 40);
		txtCap.putClientProperty("pattern", "[0-9]{5}");
		txtCap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel  lblDate = new JLabel("DATA NASCITA (gg/mm/aaaa)");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDate.setBounds(714, 223, 273, 44);
		
		txtDate = new JTextField();
		txtDate.setColumns(10);
		txtDate.setBounds(714, 278, 263, 40);
		txtDate.putClientProperty("pattern", "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
		txtDate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblMezzo = new JLabel("MEZZO DI TRASPORTO");
		lblMezzo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMezzo.setBounds(10, 329, 263, 44);
		
		comboMezzo = new JComboBox<String>();
		comboMezzo.addItem("Automobile");
		comboMezzo.addItem("Bicicletta");
		comboMezzo.addItem("Scooter");
		comboMezzo.setBounds(10, 384, 263, 40);
		comboMezzo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
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
				Driver driver = new Driver();
				driver.setFiscal_code(txtCodiceFiscale.getText());
				driver.setName(txtNome.getText());
				driver.setSurname(txtCognome.getText());
				driver.setCity(txtCitta.getSelectedItem().toString());
				driver.setCap(txtCap.getText());
				driver.setTransport(comboMezzo.getSelectedItem().toString());
				driver.setPhone(txtTelefono.getText());
				driver.setGender(comboGender.getSelectedItem().toString());
				driver.setData_n(txtDate.getText().replace("/", "-"));
				
				psql.insertQuery(new DriverDAO(driver).insert(0, psql), new InterfaceSuccessErrorDAO() {
					
					@Override
					public void ok() {
						comboGender.setSelectedIndex(0);
						txtCitta.setSelectedIndex(0);
						comboMezzo.setSelectedIndex(0);
						form.clearField();
						JOptionPane.showMessageDialog(null, "Driver inserito con successo!");
					}
					
					@Override
					public void err(String e) {
						if(e.startsWith("ERRORE: un valore chiave duplicato viola il vincolo univoco")) {
							e = "Il driver è già  presente in archivio!";
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
		//form.addToForm(txtCitta);
		form.addToForm(txtTelefono);
		form.addToForm(txtDate);
		
		// Aggiunta al form del CF!
		formCf.addToForm(txtNome);
		formCf.addToForm(txtCognome);
		formCf.addToForm(txtDate);
		
		txtCodiceFiscale.addFocusListener(focusListener);
		txtNome.addFocusListener(focusListener);
		txtCap.addFocusListener(focusListener);
		txtCognome.addFocusListener(focusListener);
		txtTelefono.addFocusListener(focusListener);
		txtDate.addFocusListener(focusListener);
		
		context.add(lbFiscalCode);
		context.add(lblNome);
		context.add(lblCognome);
		context.add(lblCap);
		context.add(lblCitta);
		context.add(lblMezzo);
		context.add(lblTelefono);
		context.add(lblGender);
		context.add(lblDate);
		context.add(txtCodiceFiscale);
		context.add(txtNome);
		context.add(txtCap);
		context.add(txtCognome);
		context.add(txtCitta);
		context.add(comboMezzo);
		context.add(txtTelefono);
		context.add(comboGender);
		context.add(txtDate);
		context.add(btnSalvaDriver);
		context.add(calcCf);
		
	}
	
}
