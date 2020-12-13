package com.uni.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.uni.frame.SubscriptionKey;
import com.uni.frame.SubscriptionKeyInterface;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.dao.Relation_RestaurantTipologyDAO;
import com.uniproject.dao.RestaurantDAO;
import com.uniproject.dao.Restaurant_TypeDAO;
import com.uniproject.entity.Relation_RestaurantTipology;
import com.uniproject.entity.Restaurant;
import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.jdbc.PostgreSQL;

public class PanelListRestaurant implements PanelAttachInterface{
	
	// Valore selezionato della tipologia
	private int keyMapTipology = -1;
	
	// Riga selezionata
	private int rowSelected = 0;
	
	// Dialog
	private JDialog dialogChooser;
	
	// Tabella
	private JTable tableListRestType;
	
	// Errori update
	private boolean errUp = false;
	
	// Errore delete
	private boolean errDel = false;
	
	@SuppressWarnings("serial")
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
	
		// Lista ristoranti in relazione con tipologia
		List<Relation_RestaurantTipology> listRelationRestTipology = 
											(List<Relation_RestaurantTipology>)
												new Relation_RestaurantTipologyDAO(new Relation_RestaurantTipology()).select(0, psql);

		
		if(listRelationRestTipology.size() > 0) {
			
			// Seleziona tipologia presenti
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
						dialogChooser.dispose();
						tableListRestType.setValueAt(comboBoxTipology.getSelectedItem(), rowSelected, 7);
					}else {
						keyMapTipology = -1;
					}
				}
			});
			comboBoxTipology.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			// Colonne tabella
			Object [] columns = {
				"Codice", "Nome", "Città", "Indirizzo", "CAP", "Telefono", "Descrizione", "Tipologia", ""
			};
			
			// Righe
			Object [][] rows = new Object[listRelationRestTipology.size()][9];
			
			// Cicla per le associazioni
			int index = 0;
			for(Relation_RestaurantTipology rrt : listRelationRestTipology) {
				rows[index][0] = rrt.getId_restaurant();
				rows[index][1] = rrt.getName();
				rows[index][2] = rrt.getCity();
				rows[index][3] = rrt.getAddress();
				rows[index][4] = rrt.getCap();
				rows[index][5] = rrt.getPhone();
				rows[index][6] = rrt.getDescription();
				rows[index][7] = rrt.getTipology();
				rows[index][8] = Boolean.FALSE;
				index++;
			}
			
			// ----------------------------------------------------------------------
			// -- 1) Il primo metodo implementato rappresenta le celle editabili
			// -- 2) Il secondo il check della eventuale selezione
			// ----------------------------------------------------------------------
			tableListRestType = new JTable(rows, columns) {
			    
				@Override
			    public boolean isCellEditable(int row, int column) {
			        return column == 0 || column == 7 ? false : true;
			    }
				
	            @Override
	            public Class<?> getColumnClass(int column) {
	            	if(column < 8)
            			return String.class;
	            	else
	            		return Boolean.class;
	            }
	            
			};
			tableListRestType.setFont(new Font("Tahoma", Font.PLAIN, 15));
			JScrollPane sp = new JScrollPane(tableListRestType);
			sp.setBounds(10, 10, 1000, 600);
			
			
			// Click del componente
			new SubscriptionKey().clickComponent(tableListRestType, new SubscriptionKeyInterface() {
				
				@Override
				public void click(int keyCode) {
					
					// -------------------
					// -- Aggiornamento --
					// -------------------
					if(keyCode == KeyEvent.VK_ENTER) {
						
						// Cicla su tutte le righe a true
						for(int obj = 0; obj < rows.length; obj++) {
							if((Boolean)tableListRestType.getModel().getValueAt(obj, 8)) {
								
								// Ottieni codice tipologia
								List<Restaurant_Tipology> restaurantType = 
										(List<Restaurant_Tipology>)new Restaurant_TypeDAO(new Restaurant_Tipology())
											.select(1, psql, tableListRestType.getModel().getValueAt(obj, 7).toString());
								
								// Ottieni il codice
								int code = restaurantType.get(0).getId_tipology();
								
								Restaurant restaurant = new Restaurant();
								restaurant.setId_restaurant(tableListRestType.getModel().getValueAt(obj, 0).toString());
								restaurant.setName(tableListRestType.getModel().getValueAt(obj, 1).toString());
								restaurant.setCity(tableListRestType.getModel().getValueAt(obj, 2).toString());
								restaurant.setAddress(tableListRestType.getModel().getValueAt(obj, 3).toString());
								restaurant.setCap(tableListRestType.getModel().getValueAt(obj, 4).toString());
								restaurant.setPhone(tableListRestType.getModel().getValueAt(obj, 5).toString());
								restaurant.setTipology(code);
								
								// Genera query di update
								psql.updateQuery(new RestaurantDAO(restaurant).update(0, psql), new InterfaceSuccessErrorDAO() {
									
									@Override
									public void ok() {}
									
									@Override
									public void err(String e) { 
										JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
										errUp = true; 
									}
									
								});
								
								// Uscita forzata!
								if(errUp)
									break;
								else
									errUp = false;
								
							}
						}
						
						// Aggiornamento andato a buon fine!
						if(!errUp)
							JOptionPane.showMessageDialog(null, "Ristoranti aggiornati correttamente!");
						
					}
					
					// -------------------
					// -- Cancellazione --
					// -------------------
					if(keyCode == KeyEvent.VK_DELETE) {
						
						for(int obj = 0; obj < rows.length; obj++) {
							if((Boolean)tableListRestType.getModel().getValueAt(obj, 8)) {
								Restaurant restaurant = new Restaurant();
								restaurant.setId_restaurant(tableListRestType.getModel().getValueAt(obj, 0).toString());
								restaurant.setName(null);
								restaurant.setCity(null);
								restaurant.setAddress(null);
								restaurant.setCap(null);
								restaurant.setPhone(null);
								restaurant.setTipology(-1);
								psql.deleteQuery(new RestaurantDAO(restaurant).delete(0, psql), new InterfaceSuccessErrorDAO() {
									
									@Override
									public void ok() {
										
									}
									
									@Override
									public void err(String e) { 
										JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
										errDel = true; 
									}
									
								});
								
								// Uscita forzata per errore
								if(errDel)
									break;
								else {
									context.removeAll();
									attach(context, psql, focusListener); // Ricrea istanza!
								}
								
							}							
						}
						
						if(!errDel)
							JOptionPane.showMessageDialog(null, "Cancellazione avvenuta con successo");
						else
							errDel = false;
						
					}
					
				}
			});
			tableListRestType.addMouseListener(new MouseAdapter() {
			    
				@Override
			    public void mouseClicked(MouseEvent e) {
					
					// Tipologia..
					if(tableListRestType.getSelectedColumn() == 7) {
						rowSelected = tableListRestType.getSelectedRow();
						if(dialogChooser != null) {
							dialogChooser.dispose();
						}
						dialogChooser = new JDialog();
						dialogChooser.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialogChooser.getContentPane().add(comboBoxTipology);
						dialogChooser.setTitle("Tipologia");
						dialogChooser.pack();
						dialogChooser.setLocationRelativeTo(null);
						dialogChooser.setVisible(true);
					}
					
			    }
			    
			});
			
			context.add(sp);
			
		}else {
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono ristoranti associati a tipologie in lista");
		}
		
	}

}
