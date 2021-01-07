package com.uni.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import com.uni.frame.Form;
import com.uni.logic.FiscalCode;
import com.uni.logic.SingletonComuni;
import com.uniproject.dao.AllergenDAO;
import com.uniproject.dao.AllergyCustomerDAO;
import com.uniproject.dao.CustomerDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Allergen;
import com.uniproject.entity.AllergyCustomer;
import com.uniproject.entity.Customer;
import com.uniproject.jdbc.PostgreSQL;

public class PanelCustomer implements PanelAttachInterface{

	// Campi di testo
	private JTextField txtCodiceFiscale;
	private JTextField txtNome;
	private JTextField txtCognome;
	private JComboBox<String> txtCitta;
	private JTextField txtCap;
	private JTextField txtIndirizzo;
	private JTextField txtTelefono;
	private JComboBox<String> comboGender;
	private JTextField txtData;

	// Allergie e clienti relazione
	private boolean theresErrorInsertRelation = false;
	
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		Form form = new Form();
		Form formCf = new Form();
		
		// Aggiungi città
		SingletonComuni instance = SingletonComuni.getInstance();
		
		// Recupera allergeni
		List<Allergen> allergens = (List<Allergen>) new AllergenDAO(new Allergen()).select(0, psql);
		
		// Salva allergeni
		ModelListCheck modelListCheck = new ModelListCheck(300);
		modelListCheck.setGenericProp("id");
		for(Allergen allergen : allergens) {
			modelListCheck.add(false, allergen.getName_allergen(), allergen.getId_allergen());
		}
		JScrollPane paneAllergen = modelListCheck.build(714, 384, 263, 113);
		
		JLabel lbFiscalCode = new JLabel("CODICE FISCALE");
		lbFiscalCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbFiscalCode.setBounds(10, 11, 263, 44);

		txtCodiceFiscale = new JTextField();
		txtCodiceFiscale.setBounds(10, 66, 615, 40);
		txtCodiceFiscale.setColumns(10);
		txtCodiceFiscale.putClientProperty("pattern", "^([A-Z]{6}[0-9LMNPQRSTUV]{2}[ABCDEHLMPRST]{1}[0-9LMNPQRSTUV]{2}[A-Z]{1}[0-9LMNPQRSTUV]{3}[A-Z]{1})$|([0-9]{11})$\r\n");
		txtCodiceFiscale.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
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
		txtNome.putClientProperty("pattern", "\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+");
		txtCognome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
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
								   txtData.getText(), 
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
		
		JLabel lblCitta = new JLabel("CITT\u00C0");
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
		
		JLabel  lblData = new JLabel("DATA NASCITA (gg/mm/aaaa)");
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblData.setBounds(714, 223, 273, 44);
		
		txtData = new JTextField();
		txtData.setColumns(10);
		txtData.setBounds(714, 278, 263, 40);
		txtData.putClientProperty("pattern", "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
		txtData.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		
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

		JLabel lblAllergie = new JLabel("ALLERGIE");
		lblAllergie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAllergie.setBounds(714, 329, 263, 44);
		
		JButton btnSalvaCustomer = new JButton("SALVA CLIENTE");
		btnSalvaCustomer.setBackground(Color.ORANGE);
		btnSalvaCustomer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSalvaCustomer.setBounds(10, 448, 615, 49);
		btnSalvaCustomer.addActionListener(new ActionListener() {
			
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
				
				List<AllergyCustomer> allergyCustomerRelationToSave = new ArrayList<>();
				
				// Seleziona tutte le allergie del cliente
				for(Object obj : modelListCheck.getResult()) {
					
					Object [] objArray = (Object[]) obj;
					boolean checked = (boolean)	objArray[0];
					
					if(checked) {
						String id = (String)objArray[2];
						AllergyCustomer allergyCustomer = new AllergyCustomer();
						allergyCustomer.setId_allergen(id);
						allergyCustomer.setFiscal_code(txtCodiceFiscale.getText());
						allergyCustomerRelationToSave.add(allergyCustomer);
					}
					
				}	
				
				// Registra cliente
				Customer customer = new Customer();
				customer.setFiscal_code(txtCodiceFiscale.getText());
				customer.setName(txtNome.getText());
				customer.setSurname(txtCognome.getText());
				customer.setCity(txtCitta.getSelectedItem().toString());
				customer.setCap(txtCap.getText());
				customer.setAddress(txtIndirizzo.getText());
				customer.setPhone(txtTelefono.getText());
				customer.setGender(comboGender.getSelectedItem().toString());
				customer.setDate(txtData.getText());
				
				psql.insertQuery(new CustomerDAO(customer).insert(0, psql), new InterfaceSuccessErrorDAO() {
					
					@Override
					public void ok() {
						
						modelListCheck.reset();
						
						// Cliente è stato registrato, adesso registra le allergie
						if(allergyCustomerRelationToSave.size()  == 0) {
							form.clearField();
							JOptionPane.showMessageDialog(null, "Cliente inserito con successo!");
							return;
						}
						
						for(AllergyCustomer ac : allergyCustomerRelationToSave) {
							psql.insertQuery(new AllergyCustomerDAO(ac).insert(0, psql), new InterfaceSuccessErrorDAO() {
								
								@Override
								public void ok() {}
								
								@Override
								public void err(String e) { theresErrorInsertRelation = true; }
								
							});
						}
						
						form.clearField();
						String e = "";
						if(theresErrorInsertRelation)
							e = "Cliente inserito con successo! Tuttavia non è stato possibile registare tutte le allergie!";
						else
							e = "Cliente e allergie inserito con successo!";
						
						JOptionPane.showMessageDialog(null, e, e.equals("") ? "Messaggio" : "Attenzione", !theresErrorInsertRelation ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE);
						
					}
					
					@Override
					public void err(String e) {
						if(e.startsWith("ERRORE: un valore chiave duplicato viola il vincolo univoco")) {
							e = "Il cliente è già presente in archivio!";
						}else {
							e = "";
						}
						JOptionPane.showMessageDialog(null, e.equals("") ? "Inserimento cliente fallito: " : e, "Errore", JOptionPane.ERROR_MESSAGE);						
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
		form.addToForm(txtIndirizzo);
		form.addToForm(txtTelefono);
		form.addToForm(txtData);
		
		// Aggiunta al form del CF!
		formCf.addToForm(txtNome);
		formCf.addToForm(txtCognome);
		formCf.addToForm(txtData);
		
		txtCodiceFiscale.addFocusListener(focusListener);
		txtNome.addFocusListener(focusListener);
		txtCap.addFocusListener(focusListener);
		txtCognome.addFocusListener(focusListener);
		txtIndirizzo.addFocusListener(focusListener);
		txtTelefono.addFocusListener(focusListener);
		txtData.addFocusListener(focusListener);
		
		context.add(lbFiscalCode);
		context.add(lblNome);
		context.add(lblCognome);
		context.add(lblCap);
		context.add(lblCitta);
		context.add(lblIndirizzo);
		context.add(lblTelefono);
		context.add(lblGender);
		context.add(lblData);
		context.add(lblAllergie);
		context.add(txtCodiceFiscale);
		context.add(txtNome);
		context.add(txtCap);
		context.add(txtCognome);
		context.add(txtCitta);
		context.add(txtIndirizzo);
		context.add(txtTelefono);
		context.add(comboGender);
		context.add(txtData);
		context.add(btnSalvaCustomer);
		context.add(calcCf);
		context.add(paneAllergen);
	}
	
}
