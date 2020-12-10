package com.uni.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.uniproject.dao.DaoRestaurant;
import com.uniproject.dao.DaoRestaurant_Type;
import com.uniproject.dao.InterfaceSuccessErrorDao;
import com.uniproject.entity.Restaurant;
import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.entity.Users;
import com.uniproject.jdbc.PostgreSQL;

@SuppressWarnings("serial")
public class FrameMenu extends JFrame{

	// Campi passati dalla login
	private Users users;
	private PostgreSQL psql;
	
	// Chiave mappa
	private int keyMapTipology;
	
	/**
	 * 
	 * @param users
	 * @param psql
	 */
	public FrameMenu(Users users, PostgreSQL psql) {
		super("Menù");
		this.users = users;
		this.psql  = psql;
		setResizable(false);
		setSize(new Dimension(1280, 720));
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(3);
		setLayout(null);
		add(buildContent());
		setLocationRelativeTo(null);
	}
	
	/**
	 * 
	 * @param frm
	 * @return
	 */
	public JPanel buildContent() {
		
		// Contenuto del pannello
		JPanel content = new JPanel();
		content.setLayout(null);
		content.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
					    (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		
		// Barra superiore
		JMenuBar menuBar = new JMenuBar();
		
		// Menù
		Map<String, String[]> mapMenu = new HashMap<>();
		mapMenu.put("Ristoranti", 		new String[] { "Registra ristorante",         "Lista ristoranti", 		"Registra tipologia ristorante" 	  });
		mapMenu.put("Prodotti",   		new String[] { "Registra prodotto",           "Lista prodotti",   		"Registra allergie alimenti"  		  });
		mapMenu.put("Clienti",    		new String[] { "Registra cliente",            "Lista clienti",    		"Registra allergie cliente" 		  });
		mapMenu.put("Drivers",    		new String[] { "Registra drivers",            "Lista drivers",    		"Registra mezzi di trasporto drivers" });
		mapMenu.put("Statistiche",    	new String[] { "Statistiche ristoranti",      "Statistiche clienti",    "Statistiche drivers" 				  });

		// Crea menu con sotto menu
		for(Map.Entry<String, String[]> menu : mapMenu.entrySet()) {
			JMenu subMenu = new JMenu(menu.getKey());
			subMenu.setFont(new Font("Tahoma", Font.BOLD, 15));
			for(String sub : menu.getValue()) {
				JMenuItem item = new JMenuItem(sub);
				item.setFont(new Font("Tahoma", Font.BOLD, 15));
				listenerMenu(item, sub, content);
				subMenu.add(item);
			}
			menuBar.add(subMenu);
		}
		
		// Aggiunta del menu al frame
		setJMenuBar(menuBar);
		
		return content;
	}
	
	/**
	 * 
	 * @param item
	 * @param text
	 * @param context
	 */
	private void listenerMenu(JMenuItem item, String text, JPanel context) {
		
		// Listener del focus
		FocusListener focusListener = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) { // Focus perso
				e.getComponent().setBackground(Color.white);
			}
			
			@Override
			public void focusGained(FocusEvent e) { // Ottiene focus
				
				// Set del colore
				e.getComponent().setBackground(new Color(255, 255, 102));
				
				// Cast dell'oggetto alla sua naturale originale..
				JTextComponent obj = null;
				if(e.getComponent() instanceof JTextArea) {
					obj = ((JTextArea)e.getComponent());
				}else {
					obj = ((JTextField)e.getComponent());
				}

			}
		};
		
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Switch testo menu
				switch(text) {
				
					// ---------------------
					// -- Crea ristorante --
					// ---------------------
					case "Registra ristorante":
						
						// Pulizia form
						Form.clearForm();
						
						// Selezionare tutte le tipologie di ristoranti creati
						List<Restaurant_Tipology> restaurantType = (List<Restaurant_Tipology>)new DaoRestaurant_Type(new Restaurant_Tipology()).select(psql);
						
						// Controlla se il numero di tipologie è > 0
						if(restaurantType.size() == 0) {
							JOptionPane.showMessageDialog(null, "Attenzione, registrare almeno una tipologia di ristorante!", "Errore", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						// Genera mappa di associazione valoer tipologia, codice tipologia
						Map<String, Integer> mapCodeValueTipology = new HashMap<>();
						for(Restaurant_Tipology rt : restaurantType) {
							mapCodeValueTipology.put(rt.getTipology(), rt.getId_tipology());
						}
						
						// Interfaccia creazione ristorante
						new PanelMenu(900, 570)
						.build(context, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {

								// Codice ristorante
								JLabel lbCode = new JLabel("CODICE RISTORANTE (*)");
								lbCode.setBounds(10, 10, 300, 40);
								lbCode.setFont(new Font("Tahoma", Font.BOLD, 20));
								
								JTextField fieldCode = new JTextField();
								fieldCode.setBounds(10, 60, 400, 40);
								fieldCode.setFont(new Font("Tahoma", Font.PLAIN, 20));
								fieldCode.setText("R0001");
								fieldCode.addFocusListener(focusListener);
								Form.addToForm(fieldCode); // Aggiungi a form
								
								// Codice ristorante
								JLabel lbTipology = new JLabel("TIPOLOGIA RISTORANTE (*)");
								lbTipology.setBounds(480, 10, 300, 40);
								lbTipology.setFont(new Font("Tahoma", Font.BOLD, 20));
								
								// Genera menu a tendina ciclando sulla mappa di chiave e valore
								JComboBox<String> comboBoxTipology = new JComboBox<>();
								for(Map.Entry<String, Integer> mapValue : mapCodeValueTipology.entrySet()) {
									comboBoxTipology.addItem(mapValue.getKey());
								}
								comboBoxTipology.setBounds(480, 60, 400, 40);
								comboBoxTipology.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										keyMapTipology = mapCodeValueTipology.get(comboBoxTipology.getSelectedItem());
									}
								});
								comboBoxTipology.setFont(new Font("Tahoma", Font.PLAIN, 20));
								
								// Codice ristorante
								JLabel lbName = new JLabel("NOME RISTORANTE (*)");
								lbName.setBounds(10, 110, 300, 40);
								lbName.setFont(new Font("Tahoma", Font.BOLD, 20));
								
								JTextField fieldName = new JTextField();
								fieldName.setBounds(10, 160, 400, 40);
								fieldName.setFont(new Font("Tahoma", Font.PLAIN, 20));
								fieldName.setText("");
								fieldName.addFocusListener(focusListener);
								Form.addToForm(fieldName); // Aggiungi a form
								
								// Città ristorante
								JLabel lbCity = new JLabel("CITTÀ RISTORANTE (*)");
								lbCity.setFont(new Font("Tahoma", Font.BOLD, 20));
								lbCity.setBounds(480, 110, 300, 40);
								
								JTextField fieldCity = new JTextField();
								fieldCity.setBounds(480, 160, 400, 40);
								fieldCity.setFont(new Font("Tahoma", Font.PLAIN, 20));
								fieldCity.setText("");
								fieldCity.addFocusListener(focusListener);
								Form.addToForm(fieldCity); // Aggiungi a form
								
								// Cap ristorante
								JLabel lbCap = new JLabel("CAP (*)");
								lbCap.setFont(new Font("Tahoma", Font.BOLD, 20));
								lbCap.setBounds(10, 210, 300, 40);
								
								JTextField fieldCap = new JTextField();
								fieldCap.setBounds(10, 260, 400, 40);
								fieldCap.setFont(new Font("Tahoma", Font.PLAIN, 20));
								fieldCap.setText("");
								fieldCap.addFocusListener(focusListener);
								Form.addToForm(fieldCap); // Aggiungi a form
								
								// Telefono ristorante
								JLabel lbPhone = new JLabel("TELEFONO");
								lbPhone.setFont(new Font("Tahoma", Font.BOLD, 20));
								lbPhone.setBounds(480, 210, 300, 40);
								
								JTextField fieldPhone = new JTextField();
								fieldPhone.setBounds(480, 260, 400, 40);
								fieldPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
								fieldPhone.setText("");
								fieldPhone.addFocusListener(focusListener);
								
								// Indirizzo ristorante
								JLabel lbAddress = new JLabel("VIA/VICOLO.. (*)");
								lbAddress.setFont(new Font("Tahoma", Font.BOLD, 20));
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
								lbAddressRest.setFont(new Font("Tahoma", Font.BOLD, 20));
								lbAddressRest.setBounds(10, 410, 300, 40);
								
								JTextField fieldAddress = new JTextField();
								fieldAddress.setBounds(10, 460, 870, 40);
								fieldAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
								fieldAddress.setText("");
								fieldAddress.addFocusListener(focusListener);
								Form.addToForm(fieldAddress); /// Aggiungi a form
								
								JButton btnAddRestaurant = new JButton("AGGIUNGI RISTORANTE");
								btnAddRestaurant.setBounds(10, 510, 870, 40);
								btnAddRestaurant.setFont(new Font("Tahoma", Font.PLAIN, 20));
								btnAddRestaurant.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										
										// Controlla validazione!
										if(!Form.validate()) {
											JOptionPane.showMessageDialog(null, "Attenzione, validare i campi evidenziati!", "Errore", JOptionPane.ERROR_MESSAGE);
											return;
										}
										
										// Controlla che ci sia connessione al db
										if(psql.isClosedConnection()) {
											JOptionPane.showMessageDialog(null, "Attenzione, connessione al db assente!", "Errore", JOptionPane.ERROR_MESSAGE);
											return;
										}
										
										// Crea istanza di ristorante e salva
										Restaurant restaurant = new Restaurant();
										restaurant.setId_restaurant(fieldCode.getText());
										restaurant.setTipology(keyMapTipology);
										restaurant.setName(fieldName.getText());
										restaurant.setCity(fieldCity.getText());
										restaurant.setCap(fieldCap.getText());
										restaurant.setAddress(fieldAddress.getText());
										restaurant.setPhone(fieldPhone.getText());
										psql.insertQuery(
											new DaoRestaurant(restaurant).insert(psql),
											new InterfaceSuccessErrorDao() {
												
												@Override
												public void ok() {
													Form.clearForm();
													JOptionPane.showMessageDialog(null, "Inserimento tipologia avvenuto con successo!");
												}
												
												@Override
												public void err(String e) {
													JOptionPane.showMessageDialog(null, "Inserimento fallito!", "Errore", JOptionPane.ERROR_MESSAGE);
												}
											}
										);
										
									}
								});
								
								panel.add(lbCode);
								panel.add(fieldCode);
								panel.add(lbTipology);
								panel.add(comboBoxTipology);
								panel.add(lbName);
								panel.add(fieldName);
								panel.add(lbCity);
								panel.add(fieldCity);
								panel.add(lbCap);
								panel.add(fieldCap);
								panel.add(lbPhone);
								panel.add(fieldPhone);
								panel.add(lbAddress);
								panel.add(comboVia);
								panel.add(lbAddressRest);
								panel.add(fieldAddress);
								panel.add(btnAddRestaurant);
							}
						});
						
					break;
				
					// ------------------------------------
					// -- Creazione tipologie ristoranti --
					// ------------------------------------
					case "Registra tipologia ristorante":
						
						// Pulizia form
						Form.clearForm();
						
						// Pannello tipologia ristorante
						new PanelMenu(500, 450)
						.build(context, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								
								// Tipologia ristorante
								JLabel lbTipology = new JLabel("TIPOLOGIA RISTORANTE (*)");
								lbTipology.setBounds(10, 10, 600, 40);
								lbTipology.setFont(new Font("Tahoma", Font.BOLD, 20));
								
								JTextField fieldTipology = new JTextField();
								fieldTipology.setBounds(10, 60, 400, 40);
								fieldTipology.setFont(new Font("Tahom", Font.PLAIN, 20));
								fieldTipology.setText("Scegli tipologia ristorante");
								fieldTipology.addFocusListener(focusListener);
								Form.addToForm(fieldTipology); // Aggiungi a form
								
								// Tipologia descrizione
								JLabel lbDesc = new JLabel("DESCRIZIONE (250)");
								lbDesc.setBounds(10, 110, 600, 40);
								lbDesc.setFont(new Font("Tahoma", Font.BOLD, 20));
								
								JTextArea fieldDesc = new JTextArea();
								fieldDesc.setBounds(10, 160, 450, 200);
								fieldDesc.setFont(new Font("Tahoma", Font.BOLD, 20));
								fieldDesc.addFocusListener(focusListener);
								
								// Bottone creazione
								JButton btnCreate = new JButton("Crea tipologia");
								btnCreate.setBounds(10, 370, 450, 40);
								btnCreate.setFont(new Font("Tahoma", Font.BOLD, 20));
								btnCreate.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										
										// Controlla validazione!
										if(!Form.validate()) {
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
											new DaoRestaurant_Type(restaurant_Tipology).insert(psql),
											new InterfaceSuccessErrorDao() {
												
												@Override
												public void ok() {
													Form.clearForm();
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
													Form.clearForm();
												}
											}
										);
										
									}
								});
								
								panel.add(lbTipology);
								panel.add(fieldTipology);
								panel.add(lbDesc);
								panel.add(fieldDesc);
								panel.add(btnCreate);
								
							}
						});
						
					break;
				
				}
				
			}
			
		});
	}
	
}
