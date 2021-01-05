package com.uni.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.uniproject.dao.DriverDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Driver;
import com.uniproject.entity.Restaurant_Tipology;
import com.uniproject.jdbc.PostgreSQL;

public class PanelDriverList implements PanelAttachInterface{
	
	// Valore selezionato della tipologia
	private int keyMapTipology = -1;
	
	// Dialog
	private JDialog dialogChooser;
	
	// Riga selezionata
	private int rowSelected = 0;
	
	// Tabella
	private JTable tableDrivers;
	
	// Errori update
	private boolean errUp = false;
	
	// Errore delete
	private boolean errDel = false;
	
	// Righe tabella
	private Object [][] rows;
	
	// Righe modificate e cancellate
	private int modified = 0;
	private int deleted  = 0;
	
	// Operazioni duplicate
	private boolean isDuplicatedOperation; 
	
	@SuppressWarnings("serial")
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
	
		List<Driver> drivers = (List<Driver>)new DriverDAO(new Driver()).select(0, psql);

		if(drivers.size() > 0) {
			
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
						if((Boolean)tableDrivers.getModel().getValueAt(obj, 7)) {
							
							if((Boolean)tableDrivers.getModel().getValueAt(obj, 8)) {
								isDuplicatedOperation = true;
								break;
							}
														
							Driver driver = new Driver();
							driver.setFiscal_code(tableDrivers.getModel().getValueAt(obj, 0).toString());
							driver.setName(tableDrivers.getModel().getValueAt(obj, 1).toString());
							driver.setSurname(tableDrivers.getModel().getValueAt(obj, 2).toString());
							driver.setCity(tableDrivers.getModel().getValueAt(obj, 3).toString());
							driver.setCap(tableDrivers.getModel().getValueAt(obj, 4).toString());
							driver.setPhone(tableDrivers.getModel().getValueAt(obj, 6).toString());
							driver.setTransport(tableDrivers.getModel().getValueAt(obj, 5).toString());
							
							// Genera query di update
							psql.updateQuery(new DriverDAO(driver).update(0, psql), new InterfaceSuccessErrorDAO() {
								
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
						JOptionPane.showMessageDialog(null, modified > 0 ? "Driver/s aggiornato correttamente!" : "Attenzione, nessuna riga modificata!");
						modified = 0;
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
					
					isDuplicatedOperation = false;
					for(int obj = 0; obj < rows.length; obj++) {
						
						if((Boolean)tableDrivers.getModel().getValueAt(obj, 8)) {
						
							if((Boolean)tableDrivers.getModel().getValueAt(obj, 7)) {
								isDuplicatedOperation = true;
								break;
							}
							
							Driver driver = new Driver();
							driver.setFiscal_code(tableDrivers.getModel().getValueAt(obj, 0).toString());
							driver.setName(null);
							driver.setSurname(null);
							driver.setCity(null);
							driver.setCap(null);
							driver.setPhone(null);
							driver.setTransport(null);
							psql.deleteQuery(new DriverDAO(driver).delete(0, psql), new InterfaceSuccessErrorDAO() {
								
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
			});
					
			
			// Colonne tabella
			Object [] columns = {
				"Codice Fiscale", "Nome", "Cognome", "Città", "CAP", "Mezzo di trasporto", "Telefono", "Modifica", "Cancellazione"
			};
			
			// Righe
			rows = new Object[drivers.size()][9];
			
			// Cicla per le associazioni
			int index = 0;
			for(Driver dr : drivers) {
				rows[index][0] = dr.getFiscal_code();
				rows[index][1] = dr.getName();
				rows[index][2] = dr.getSurname();
				rows[index][3] = dr.getCity();
				rows[index][4] = dr.getCap();
				rows[index][5] = dr.getTransport();
				rows[index][6] = dr.getPhone();
				rows[index][7] = Boolean.FALSE;
				rows[index][8] = Boolean.FALSE;
				index++;
			}
			
			// ----------------------------------------------------------------------
			// -- 1) Il primo metodo implementato rappresenta le celle editabili
			// -- 2) Il secondo il check della eventuale selezione
			// ----------------------------------------------------------------------
			tableDrivers = new JTable(rows, columns) {
			    
				@Override
			    public boolean isCellEditable(int row, int column) {
			        return column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 ? false : true;
			    }
				
	            @Override
	            public Class<?> getColumnClass(int column) {
	            	if(column < 7)
            			return String.class;
	            	else
	            		return Boolean.class;
	            }
	            
			};
			
			//GENERA MAPPA
			List<String> ValueTipology = new ArrayList<>(3);
			ValueTipology.add("Automobile");
			ValueTipology.add("Bicicletta");
			ValueTipology.add("Scooter");
			
			// Genera menu a tendina per i mezzi di trasporto
						JComboBox<String> comboBoxTransport = new JComboBox<>();
						comboBoxTransport.addItem("-- Mezzi di trasporto --");
						comboBoxTransport.addItem("Automobile");
						comboBoxTransport.addItem("Bicicletta");
						comboBoxTransport.addItem("Scooter");
						comboBoxTransport.setBounds(480, 60, 400, 40);
						comboBoxTransport.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if(!comboBoxTransport.getSelectedItem().equals("-- Mezzi di trasporto --")) {
									// NON SO SE SERVE keyMapTipology = ValueTipology.getItemText(comboBoxTransport.getSelectedItem());
									dialogChooser.dispose();
									tableDrivers.setValueAt(comboBoxTransport.getSelectedItem(), rowSelected, 5);
								}else {
									keyMapTipology = -1;
								}
							}
						});
						comboBoxTransport.setFont(new Font("Tahoma", Font.PLAIN, 20));
						
					
			JScrollPane sp = new JScrollPane(tableDrivers);
			sp.setBounds(10, 115, 1000, 550);
			
			// ---------------------------
			// -- Mouse listener tabella -
			// ---------------------------
			tableDrivers.addMouseListener(new MouseAdapter() {
			    
				@Override
			    public void mouseClicked(MouseEvent e) {
					
					// Tipologia..
					if(tableDrivers.getSelectedColumn() == 5) {
						rowSelected = tableDrivers.getSelectedRow();
						if(dialogChooser != null) {
							dialogChooser.dispose();
						}
						dialogChooser = new JDialog();
						dialogChooser.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialogChooser.getContentPane().add(comboBoxTransport);
						dialogChooser.setTitle("Tipologia");
						dialogChooser.pack();
						dialogChooser.setLocationRelativeTo(null);
						dialogChooser.setVisible(true);
					}

			    }
			    
			});
			
			GenericResearch gr = new GenericResearch(new Driver());
			gr.appendElement(context, 10, 65, new IGet() {
				
				@Override
				public void put(String text) {
					
					Driver driver = null;
					try {
						driver = (Driver) gr.invokeSet(text);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la ricerca: " + e.getMessage() , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					List<Driver> drivers = (List<Driver>)new DriverDAO(driver).select(1, psql);

					// Righe
					rows = new Object[drivers.size()][9];
					
					// Cicla per le associazioni
					int index = 0;
					for(Driver dr : drivers) {
						rows[index][0] = dr.getFiscal_code();
						rows[index][1] = dr.getName();
						rows[index][2] = dr.getSurname();
						rows[index][3] = dr.getCity();
						rows[index][4] = dr.getCap();
						rows[index][5] = dr.getTransport();
						rows[index][6] = dr.getPhone();
						rows[index][7] = Boolean.FALSE;
						rows[index][8] = Boolean.FALSE;
						index++;
					}
					
					gr.getNewModel(tableDrivers, columns, rows);
					
				}
			});
			
			context.add(sp);
			context.add(btnUp);
			context.add(btnDel);
			
		}else {
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono driver!");
		}
		
	}

}
