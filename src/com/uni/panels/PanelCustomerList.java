package com.uni.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.uniproject.dao.AllergyCustomer_RelationDAO;
import com.uniproject.dao.CustomerDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Customer;
import com.uniproject.entity.Relation_AllergyCustomerDescription;
import com.uniproject.jdbc.PostgreSQL;

public class PanelCustomerList implements PanelAttachInterface{
	
	// Tabella
	private JTable tableCustomer;
	
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
		
		List<Customer> customers = (List<Customer>)new CustomerDAO(new Customer()).select(0, psql);

		if(customers.size() > 0) {
			
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
						if((Boolean)tableCustomer.getModel().getValueAt(obj, 8)) {
							
							if((Boolean)tableCustomer.getModel().getValueAt(obj, 9)) {
								isDuplicatedOperation = true;
								break;
							}
							
							Customer customer = new Customer();
							customer.setFiscal_code(tableCustomer.getModel().getValueAt(obj, 0).toString());
							customer.setName(tableCustomer.getModel().getValueAt(obj, 1).toString());
							customer.setSurname(tableCustomer.getModel().getValueAt(obj, 2).toString());
							customer.setDate(tableCustomer.getModel().getValueAt(obj, 3).toString());
							customer.setCity(tableCustomer.getModel().getValueAt(obj, 4).toString());
							customer.setCap(tableCustomer.getModel().getValueAt(obj, 5).toString());
							customer.setPhone(tableCustomer.getModel().getValueAt(obj, 7).toString());
							customer.setAddress(tableCustomer.getModel().getValueAt(obj, 6).toString());
							
							// Genera query di update
							psql.updateQuery(new CustomerDAO(customer).update(0, psql), new InterfaceSuccessErrorDAO() {
								
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
						JOptionPane.showMessageDialog(null, modified > 0 ? "Cliente/i aggiornato/i correttamente!" : "Attenzione, nessuna riga modificata!");
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
						
						if((Boolean)tableCustomer.getModel().getValueAt(obj, 9)) {
						
							if((Boolean)tableCustomer.getModel().getValueAt(obj, 8)) {
								isDuplicatedOperation = true;
								break;
							}
							
							Customer customer = new Customer();
							customer.setFiscal_code(tableCustomer.getModel().getValueAt(obj, 0).toString());
							customer.setName(null);
							customer.setSurname(null);
							customer.setCity(null);
							customer.setCap(null);
							customer.setPhone(null);
							customer.setAddress(null);
							customer.setDate(null);
							psql.deleteQuery(new CustomerDAO(customer).delete(0, psql), new InterfaceSuccessErrorDAO() {
								
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
				"Codice Fiscale", "Nome", "Cognome", "Data", "Città", "CAP", "Indirizzo", "Telefono", "Modifica", "Cancellazione"
			};
			
			// Righe
			rows = new Object[customers.size()][10];
			
			// Cicla per le associazioni
			int index = 0;
			for(Customer cr : customers) {
				rows[index][0] = cr.getFiscal_code();
				rows[index][1] = cr.getName();
				rows[index][2] = cr.getSurname();
				rows[index][3] = cr.getDate();
				rows[index][4] = cr.getCity();
				rows[index][5] = cr.getCap();
				rows[index][6] = cr.getAddress();
				rows[index][7] = cr.getPhone();
				rows[index][8] = Boolean.FALSE;
				rows[index][9] = Boolean.FALSE;
				index++;
			}
			
			// Modello della tabella
			DefaultTableModel defaultTableModel = new DefaultTableModel(rows, columns);
			
			// ----------------------------------------------------------------------
			// -- 1) Il primo metodo implementato rappresenta le celle editabili
			// -- 2) Il secondo il check della eventuale selezione
			// ----------------------------------------------------------------------
			tableCustomer = new JTable(defaultTableModel) {
			    
				@Override
			    public boolean isCellEditable(int row, int column) {
			        return column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 ? false : true;
			    }
				
	            @Override
	            public Class<?> getColumnClass(int column) {
	            	if(column < 8)
            			return String.class;
	            	else
	            		return Boolean.class;
	            }
	            
			};
			JScrollPane sp = new JScrollPane(tableCustomer);
			sp.setBounds(10, 115, 1000, 550);
			
			tableCustomer.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if(tableCustomer.getSelectedColumn() == 0) {
						String fiscal_code = (String) tableCustomer.getValueAt(tableCustomer.getSelectedRow(), 0);
						List<Relation_AllergyCustomerDescription> relationAllergyCustomer = 
								(List<Relation_AllergyCustomerDescription>)new AllergyCustomer_RelationDAO(new Relation_AllergyCustomerDescription()).select(0, psql, fiscal_code);

						if(relationAllergyCustomer.size() == 1) {
							if(relationAllergyCustomer.get(0).getName_allergen() == null) {
								JOptionPane.showMessageDialog(null, "Il cliente non presenta allergie");
								return;
							}
						}
						
						String [] columns = {
								"Allergie"
							};
						String [][] rows = new String[relationAllergyCustomer.size()][1];
						
						int index = 0;
						for(Relation_AllergyCustomerDescription rad : relationAllergyCustomer) {
							rows[index][0] = rad.getName_allergen();
							index++;
						}
						
						DefaultTableModel dtm = new DefaultTableModel(rows, columns);
						JTable tableAllergen = new JTable(dtm);
						JScrollPane scrollPane = new JScrollPane(tableAllergen);
						
						JDialog dialogAllergen = new JDialog();
						dialogAllergen.setLocationRelativeTo(null);
						dialogAllergen.add(scrollPane);
						dialogAllergen.pack();
						dialogAllergen.setVisible(true);
						
					}
				}
				
			});
			
			// Ricerca
			GenericResearch gr = new GenericResearch(new Customer());
			gr.appendElement(context, 10, 65, new IGet() {
				
				@Override
				public void put(String value) {
					
					Customer customer = null;
					try {
						customer = (Customer) gr.invokeSet(value);
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la ricerca: " + e.getMessage() , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					// Lista clienti
					List<Customer> customersList = (List<Customer>)new CustomerDAO(customer).select(1, psql);
					
					// Righe
					rows = new Object[customersList.size()][10];
					
					// Cicla per le associazioni
					int index = 0;
					for(Customer cr : customersList) {
						rows[index][0] = cr.getFiscal_code();
						rows[index][1] = cr.getName();
						rows[index][2] = cr.getSurname();
						rows[index][3] = cr.getDate();
						rows[index][4] = cr.getCity();
						rows[index][5] = cr.getCap();
						rows[index][6] = cr.getAddress();
						rows[index][7] = cr.getPhone();
						rows[index][8] = Boolean.FALSE;
						rows[index][9] = Boolean.FALSE;
						index++;
					}
					
					gr.getNewModel(tableCustomer, columns, rows);
					
				}
			});
			
			context.add(sp);
			context.add(btnUp);
			context.add(btnDel);
			
		}else {
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono clienti!");
		}
		
	}

}
