package com.uni.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
	
	// Righe tabella
	private Object [][] rows;
	
	// Righe modificate e cancellate
	private int modified = 0;
	private int deleted  = 0;
	
	// Id dei prodotti associati
	private List<Integer> idProds;
	
	// Cancellazione e modifica in contemporanea
	private boolean isDuplicatedOperation = false;
	
	@SuppressWarnings("serial")
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		// Lista ristoranti in relazione con tipologia
		List<Relation_RestaurantTipology> listRelationRestTipology = 
											(List<Relation_RestaurantTipology>)
												new Relation_RestaurantTipologyDAO(new Relation_RestaurantTipology()).select(0, psql);

		if(listRelationRestTipology.size() > 0) {
			
			// -------------------------
			// -- Bottone di modifica --
			// -------------------------
			JButton btnUp = new JButton("Modifica");
			btnUp.setBounds(10, 10, 200, 40);
			btnUp.setBackground(Color.orange);
			btnUp.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					// Cicla su tutte le righe a true
					isDuplicatedOperation = false;
					for(int obj = 0; obj < rows.length; obj++) {
						if((Boolean)tableListRestType.getModel().getValueAt(obj, 7)) {
							
							if((Boolean)tableListRestType.getModel().getValueAt(obj, 8)) {
								isDuplicatedOperation = true;
								break;
							}
							
							// Ottieni codice tipologia
							List<Restaurant_Tipology> restaurantType = 
									(List<Restaurant_Tipology>)new Restaurant_TypeDAO(new Restaurant_Tipology())
										.select(1, psql, tableListRestType.getModel().getValueAt(obj, 6).toString());
							
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
								public void ok() { modified++; }
								
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
					
					// Operazione duplicata!
					if(isDuplicatedOperation) {
						JOptionPane.showMessageDialog(null, "Attenzione, eseguire solo aggiornamento o cancellazione!", "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Aggiornamento andato a buon fine!
					if(!errUp) {
						JOptionPane.showMessageDialog(null, modified > 0 ? "Ristoranti aggiornati correttamente!" : "Attenzione, nessuna riga modificata!");
						modified = 0;
						context.removeAll();
						context.repaint();
						context.revalidate();
						attach(context, psql, focusListener); // Ricrea istanza!
					}
					
				}
			});
			
			// ---------------------------
			// -- Bottone cancellazione --
			// ---------------------------
			JButton btnDel = new JButton("Elimina");
			btnDel.setBounds(220, 10, 200, 40);
			btnDel.setBackground(Color.orange);
			btnDel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					// Avvertenza alla cancellazione!
					int confirm = JOptionPane.showConfirmDialog(null, "Attenzione, eliminando un ristorante considera che:					     " + 
																	   "\n1)i prodotti associati esclusivamente al ristorante saranno eliminati; " + 
														               "\n2)i prodotti associati a più ristoranti non saranno eliminati.\n       " + 
																	   "Sicuro di voler cancellare?".trim());
					
					// Conferma è 0!
					if(confirm == 0) {
						isDuplicatedOperation = false;
						for(int obj = 0; obj < rows.length; obj++) {
							
							if((Boolean)tableListRestType.getModel().getValueAt(obj, 8)) {
							
								if((Boolean)tableListRestType.getModel().getValueAt(obj, 7)) {
									isDuplicatedOperation = true;
									break;
								}
								
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
									public void ok() { deleted++; }
									
									@Override
									public void err(String e) { 
										JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
										errDel = true; 
									}
									
								});
								
								// Uscita forzata per errore
								if(errDel)
									break;
								
							}							
						}
						
						// Operazione duplicata!
						if(isDuplicatedOperation) {
							JOptionPane.showMessageDialog(null, "Attenzione, eseguire solo aggiornamento o cancellazione!", "Errore", JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if(!errDel) {
							JOptionPane.showMessageDialog(null, deleted > 0 ? "Cancellazione avvenuta con successo" : "Nessuna riga cancellata!");
							context.removeAll();
							context.repaint();
							context.revalidate();
							attach(context, psql, focusListener); // Ricrea istanza!
						}else {
							errDel = false;
						}	
					}
					
				}
			});
					
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
						tableListRestType.setValueAt(comboBoxTipology.getSelectedItem(), rowSelected, 6);
					}else {
						keyMapTipology = -1;
					}
				}
			});
			comboBoxTipology.setFont(new Font("Tahoma", Font.PLAIN, 20));
			
			// Colonne tabella
			Object [] columns = {
				"Codice", "Nome", "Città", "Indirizzo", "CAP", "Telefono", "Tipologia", "Modifica", "Cancellazione"
			};
			
			// Righe
			rows = new Object[listRelationRestTipology.size()][9];
			
			// Cicla per le associazioni
			int index = 0;
			for(Relation_RestaurantTipology rrt : listRelationRestTipology) {
				rows[index][0] = rrt.getId_restaurant();
				rows[index][1] = rrt.getName();
				rows[index][2] = rrt.getCity();
				rows[index][3] = rrt.getAddress();
				rows[index][4] = rrt.getCap();
				rows[index][5] = rrt.getPhone();
				rows[index][6] = rrt.getTipology();
				rows[index][7] = Boolean.FALSE;
				rows[index][8] = Boolean.FALSE;
				index++;
			}
			
			DefaultTableModel defaultTableModel = new DefaultTableModel(rows, columns);
			
			// ----------------------------------------------------------------------
			// -- 1) Il primo metodo implementato rappresenta le celle editabili
			// -- 2) Il secondo il check della eventuale selezione
			// ----------------------------------------------------------------------
			tableListRestType = new JTable(defaultTableModel) {
			    
				@Override
			    public boolean isCellEditable(int row, int column) {
			        return column == 0 || column == 6 ? false : true;
			    }
				
	            @Override
	            public Class<?> getColumnClass(int column) {
	            	if(column < 7)
            			return String.class;
	            	else
	            		return Boolean.class;
	            }
	            
			};
			JScrollPane sp = new JScrollPane(tableListRestType);
			sp.setBounds(10, 120, 1000, 550);
			
			// ---------------------------
			// -- Mouse listener tabella -
			// ---------------------------
			tableListRestType.addMouseListener(new MouseAdapter() {
			    
				@Override
			    public void mouseClicked(MouseEvent e) {
					
					// Tipologia..
					if(tableListRestType.getSelectedColumn() == 6) {
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
			
			GenericResearch gr = new GenericResearch(new Relation_RestaurantTipology());
			gr.appendElement(context, 10, 70, new IGet() {
				
				@Override
				public void put(String value) {
					
					Relation_RestaurantTipology rrtResearch = null;
					try {
						rrtResearch = (Relation_RestaurantTipology) gr.invokeSet(value);
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la ricerca: " + e.getMessage() , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Lista ristoranti in relazione con tipologia
					List<Relation_RestaurantTipology> listRelationRestTipology = 
														(List<Relation_RestaurantTipology>)
															new Relation_RestaurantTipologyDAO(rrtResearch)
																.select(1, psql);
					// Righe
					rows = new Object[listRelationRestTipology.size()][9];
					
					// Cicla per le associazioni
					int index = 0;
					for(Relation_RestaurantTipology rrt : listRelationRestTipology) {
						rows[index][0] = rrt.getId_restaurant();
						rows[index][1] = rrt.getName();
						rows[index][2] = rrt.getCity();
						rows[index][3] = rrt.getAddress();
						rows[index][4] = rrt.getCap();
						rows[index][5] = rrt.getPhone();
						rows[index][6] = rrt.getTipology();
						rows[index][7] = Boolean.FALSE;
						rows[index][8] = Boolean.FALSE;
						index++;
					}
					
					gr.getNewModel(tableListRestType, columns, rows);
				}
				
			});
			
			context.add(sp);
			context.add(btnUp);
			context.add(btnDel);
			
		}else {
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono ristoranti associati a tipologie in lista");
		}
		
	}

}
