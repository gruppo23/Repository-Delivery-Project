package com.uni.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.uniproject.dao.DriversDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Drivers;
import com.uniproject.jdbc.PostgreSQL;

public class PanelDriverList implements PanelAttachInterface{
	
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
	
		List<Drivers> drivers = (List<Drivers>)new DriversDAO(new Drivers()).select(0, psql);

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
														
							Drivers driver = new Drivers();
							driver.setFiscal_code(tableDrivers.getModel().getValueAt(obj, 0).toString());
							driver.setName(tableDrivers.getModel().getValueAt(obj, 1).toString());
							driver.setSurname(tableDrivers.getModel().getValueAt(obj, 2).toString());
							driver.setCity(tableDrivers.getModel().getValueAt(obj, 3).toString());
							driver.setCap(tableDrivers.getModel().getValueAt(obj, 4).toString());
							driver.setPhone(tableDrivers.getModel().getValueAt(obj, 6).toString());
							driver.setAddress(tableDrivers.getModel().getValueAt(obj, 5).toString());
							
							// Genera query di update
							psql.updateQuery(new DriversDAO(driver).update(0, psql), new InterfaceSuccessErrorDAO() {
								
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
							
							Drivers driver = new Drivers();
							driver.setFiscal_code(tableDrivers.getModel().getValueAt(obj, 0).toString());
							driver.setName(null);
							driver.setSurname(null);
							driver.setCity(null);
							driver.setCap(null);
							driver.setPhone(null);
							driver.setAddress(null);
							psql.deleteQuery(new DriversDAO(driver).delete(0, psql), new InterfaceSuccessErrorDAO() {
								
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
				"Codice Fiscale", "Nome", "Cognome", "Città", "CAP", "Indirizzo", "Telefono", "Modifica", "Cancellazione"
			};
			
			// Righe
			rows = new Object[drivers.size()][9];
			
			// Cicla per le associazioni
			int index = 0;
			for(Drivers dr : drivers) {
				rows[index][0] = dr.getFiscal_code();
				rows[index][1] = dr.getName();
				rows[index][2] = dr.getSurname();
				rows[index][3] = dr.getCity();
				rows[index][4] = dr.getCap();
				rows[index][5] = dr.getAddress();
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
			        return column == 0 ? false : true;
			    }
				
	            @Override
	            public Class<?> getColumnClass(int column) {
	            	if(column < 7)
            			return String.class;
	            	else
	            		return Boolean.class;
	            }
	            
			};
			JScrollPane sp = new JScrollPane(tableDrivers);
			sp.setBounds(10, 65, 1000, 550);
			
			context.add(sp);
			context.add(btnUp);
			context.add(btnDel);
			
		}else {
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono driver!");
		}
		
	}

}
