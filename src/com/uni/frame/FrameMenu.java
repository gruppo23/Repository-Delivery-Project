package com.uni.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
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

import com.uni.panels.PanelAddAllergy;
import com.uni.panels.PanelAddAllergyToCustomer;
import com.uni.panels.PanelCustomer;
import com.uni.panels.PanelCustomerList;
import com.uni.panels.PanelDriver;
import com.uni.panels.PanelDriverList;
import com.uni.panels.PanelListRestaurant;
import com.uni.panels.PanelOrder;
import com.uni.panels.PanelProduct;
import com.uni.panels.PanelRestaurant;
import com.uni.panels.PanelRestaurantTipology;
import com.uniproject.entity.Users;
import com.uniproject.jdbc.PostgreSQL;

@SuppressWarnings("serial")
public class FrameMenu extends JFrame{

	// Campi passati dalla login
	private Users users;
	private PostgreSQL psql;
	
	// Pannello esterno
	private JPanel content;
	
	/**
	 * 
	 * @param users
	 * @param psql
	 */
	public FrameMenu(Users users, PostgreSQL psql) {
		super("Menù");
		this.users = users;
		this.psql  = psql;
		setSize(new Dimension(1280, 720));
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(3);
		setBackground(new Color(245, 245, 245));
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
		content = new JPanel();
		content.setLayout(null);
		content.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
					    (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		content.setBackground(new Color(245, 245, 245));
				
		// Barra superiore
		JMenuBar menuBar = new JMenuBar();
		
		// Menù
		Map<String, String[]> mapMenu = new HashMap<>();
		mapMenu.put("Ristoranti", 		new String[] { "Registra ristorante",         "Lista ristoranti", 		   "Registra tipologia ristorante" 	         });
		mapMenu.put("Prodotti",   		new String[] { "Registra prodotto",           "Registra allergie alimenti"      		     						 });
		mapMenu.put("Clienti",    		new String[] { "Registra cliente",            "Lista clienti",    		   "Registra allergie cliente"               });
		mapMenu.put("Ordine",    		new String[] { "Registra ordine",             "Lista ordini",    		                                             });
		mapMenu.put("Drivers",    		new String[] { "Registra drivers",            "Lista drivers",    			                                     	 });
		mapMenu.put("Statistiche",    	new String[] { "Statistiche ristoranti",      "Statistiche clienti",        "Statistiche drivers" 	                 });

		// Crea menu con sotto menu
		for(Map.Entry<String, String[]> menu : mapMenu.entrySet()) {
			JMenu subMenu = new JMenu(menu.getKey());
			subMenu.setFont(new Font("Tahoma", Font.PLAIN, 15));
			for(String sub : menu.getValue()) {
				JMenuItem item = new JMenuItem(sub);
				item.setFont(new Font("Tahoma", Font.PLAIN, 15));
				listenerMenu(item, sub, content);
				subMenu.add(item);
			}
			menuBar.add(subMenu);
		}
		
		// Aggiunta del menu al frame
		setJMenuBar(menuBar);
		
		// JPanel immagine
		try {
			
			// Carica immagine..
			ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResourceAsStream(SingletonImage.getInstance().getImage(0)).readAllBytes());
			imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(300, 236, Image.SCALE_SMOOTH));
			JLabel labelBackImage = new JLabel(imageIcon);
			labelBackImage.setBounds(
					(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 320, 
					(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 320, 
					300, 
					236
			);
			add(labelBackImage);
			
		}catch(Exception e) {
			
		}
		
		return content;
	}
	
	/**
	 * 
	 * @param item
	 * @param text
	 * @param context
	 */
	private void listenerMenu(JMenuItem item, String text, JPanel content) {
		
		Form form = new Form();
		
		// Listener del focus
		FocusListener focusListener = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) { // Focus perso
				e.getComponent().setBackground(Color.white);
			}
			
			@Override
			public void focusGained(FocusEvent e) { // Ottiene focus
				
				// Set del colore
				e.getComponent().setBackground(Color.orange);
				
				// Cast dell'oggetto alla sua naturale originale..
				JTextComponent obj;
				if(e.getComponent() instanceof JTextArea) {
					obj = ((JTextArea)e.getComponent());
				}else {
					obj = ((JTextField)e.getComponent());
				}
				obj.select(0, obj.getText().length());

			}
		};
		
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Switch testo menu
				switch(text) {
				
					// ---------------------
					// -- Registra ordine --
					// ---------------------
					case "Registra ordine":
						
	                    // Pulizia form
	                    form.clearForm();
	                   
	                    // Interfaccia creazione ristorante
	                    new PanelMenu(1500, 900)
	                    .build(content, new PanelMenuBuilderInterface() {
	                       
	                        @Override
	                        public void attach(JPanel panel) {
	                            new PanelOrder().attach(panel, psql, focusListener);
	                        }
	                       
	                    });
						
					break;
				
	                // ---------------------
	                // ------ Allergie------
	                // ---------------------
	                case "Registra allergie alimenti":
	                   
	                    // Pulizia form
	                    form.clearForm();
	                   
	                    // Interfaccia creazione ristorante
	                    new PanelMenu(900, 570)
	                    .build(content, new PanelMenuBuilderInterface() {
	                       
	                        @Override
	                        public void attach(JPanel panel) {
	                            new PanelAddAllergy().attach(panel, psql, focusListener);
	                        }
	                       
	                    });
	                   
	                break;
	                
	                // ---------------------------------
	                // ----AGGIUNGI ALLERGIA CLIENTE----
	                // ---------------------------------
	                case "Registra allergie cliente":
	                   
	                    // Pulizia form
	                    form.clearForm();
	                   
	                    // Interfaccia creazione ristorante
	                    new PanelMenu(900, 570)
	                    .build(content, new PanelMenuBuilderInterface() {
	                       
	                        @Override
	                        public void attach(JPanel panel) {
	                            new PanelAddAllergyToCustomer().attach(panel, psql, focusListener);
	                        }
	                       
	                    });
	                   
	                break;
				
					// -------------------
					// -- Lista clienti --
					// -------------------
					case "Lista clienti":
						
						// Pulizia form
						form.clearForm();
						
						new PanelMenu(1000, 700)
						.build(content, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								new PanelCustomerList().attach(panel, psql, focusListener);
							}
						});
						
					break;
				
					// ---------------------------
					// -- Registrazione cliente --
					// ---------------------------
					case "Registra cliente":
					
						// Pulizia form
						form.clearForm();
						
						new PanelMenu(1000, 700)
						.build(content, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								new PanelCustomer().attach(panel, psql, focusListener);
							}
						});
						
					break;
				
					// -------------------
					// -- Lista drivers --
					// -------------------
					case "Lista drivers":
						
						// Pulizia form
						form.clearForm();
						
						new PanelMenu(1000, 700)
						.build(content, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								new PanelDriverList().attach(panel, psql, focusListener);
							}
						});
						
					break;
				
					// ---------------------
					// -- Registra driver --
					// ---------------------
					case "Registra drivers":
						
						// Pulizia form
						form.clearForm();
						new PanelMenu(1000, 700)
						.build(content, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								new PanelDriver().attach(panel, psql, focusListener);
							}
						});
						
					break;
				
					// ----------------------------------
					// -- Registrazione di un prodotto --
					// ----------------------------------
					case "Registra prodotto":
						
						// Pulizia form
						form.clearForm();
						
						new PanelMenu(1000, 700)
						.build(content, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								new PanelProduct().attach(panel, psql, focusListener);
							}	
							
						});
						
					break;
				
					// ----------------------
					// -- Lista ristorante --
					// ----------------------
					case "Lista ristoranti":
						
						// Pulizia form
						form.clearForm();
						
						new PanelMenu(1000, 700)
						.build(content, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								new PanelListRestaurant().attach(panel, psql, focusListener);
							}
						});
						
					break;
				
					// ---------------------
					// -- Crea ristorante --
					// ---------------------
					case "Registra ristorante":
						
						// Pulizia form
						form.clearForm();
						
						// Interfaccia creazione ristorante
						new PanelMenu(900, 570)
						.build(content, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								new PanelRestaurant().attach(panel, psql, focusListener);
							}
							
						});
						
					break;
				
					// ------------------------------------
					// -- Creazione tipologie ristoranti --
					// ------------------------------------
					case "Registra tipologia ristorante":
						
						// Pulizia form
						form.clearForm();
						
						// Pannello tipologia ristorante
						new PanelMenu(500, 450)
						.build(content, new PanelMenuBuilderInterface() {
							
							@Override
							public void attach(JPanel panel) {
								new PanelRestaurantTipology().attach(panel, psql, focusListener);
							}
							
						});
						
					break;
				
				}
				
			}
			
		});
	}
	
}
