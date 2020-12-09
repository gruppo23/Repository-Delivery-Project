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
import java.util.Map;

import javax.swing.JButton;
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

import com.uniproject.dao.DaoRestaurant_Type;
import com.uniproject.dao.InterfaceSuccessErrorDao;
import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.entity.Users;
import com.uniproject.jdbc.PostgreSQL;

@SuppressWarnings("serial")
public class FrameMenu extends JFrame{

	// Campi passati dalla login
	private Users users;
	private PostgreSQL psql;
	
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
			for(String sub : menu.getValue()) {
				JMenuItem item = new JMenuItem(sub);
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
				
				// Svuota in caso di hint
				if(obj.getText().equals("Scegli tipologia ristorante")) {
					obj.setText("");
				}
			}
		};
		
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Switch testo menu
				switch(text) {
				
					case "Registra tipologia ristorante":
						
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
										restaurant_Tipology.setType(fieldTipology.getText());
										restaurant_Tipology.setDescription(fieldDesc.getText());
										psql.insertQuery(
											new DaoRestaurant_Type(restaurant_Tipology).insert(psql),
											new InterfaceSuccessErrorDao() {
												
												@Override
												public void ok() {
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
