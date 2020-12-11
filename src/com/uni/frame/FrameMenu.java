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

import com.uni.panels.PanelRestaurant;
import com.uni.panels.PanelRestaurantTipology;
import com.uniproject.dao.RestaurantDAO;
import com.uniproject.dao.Restaurant_TypeDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.dao.Relation_RestaurantTipologyDAO;
import com.uniproject.entity.Relation_RestaurantTipology;
import com.uniproject.entity.Restaurant;
import com.uniproject.entity.Restaurant_Tipology;
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
		super("Men�");
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
		content = new JPanel();
		content.setLayout(null);
		content.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 
					    (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		
		// Barra superiore
		JMenuBar menuBar = new JMenuBar();
		
		// Men�
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
				e.getComponent().setBackground(new Color(255, 255, 102));
				
				// Cast dell'oggetto alla sua naturale originale..
				JTextComponent obj;
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
				
					// ----------------------
					// -- Lista ristorante --
					// ----------------------
					case "Lista ristoranti":
						
						// Pulizia form
						form.clearForm();
						
						// Lista ristoranti in relazione con tipologia
						List<Relation_RestaurantTipology> listRelationRestTipology = 
															(List<Relation_RestaurantTipology>)
																new Relation_RestaurantTipologyDAO(new Relation_RestaurantTipology()).select(psql);
						
						if(listRelationRestTipology.size() > 0) {
							for(Relation_RestaurantTipology rrt : listRelationRestTipology) {
								String nameRestaurant = rrt.getName();
							}
						}
						
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
