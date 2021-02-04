package com.uni.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.uniproject.entity.DeliveryOrderQuantity;
import com.uniproject.entity.Delivery_Order;
import com.uniproject.dao.DeliveryOrderDao;
import com.uniproject.dao.DeliveryOrderQuantityDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.jdbc.PostgreSQL;


public class PanelOrderList {
	// Riga selezionata
	private int rowSelected = 0;
	
	// Cancellato
	private int deleted  = 0;
	
	// Updatato
	private int updated = 0;
	
	// Tabella
	private JTable tableDrivers;
		
	// Errore delete
	private boolean errDel = false;
	
	// Errore update
	private boolean errUp = false;
	
	// Righe tabella
	private Object [][] rows;

	// Tabella
	private JTable tableOrders;
	
	// Result callback
	private boolean ok = false;
	
	@SuppressWarnings("serial")
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		// ----------------------
		// -- Bottone conferma --
		// ----------------------
		JButton btnCon = new JButton("Conferma");
		btnCon.setBounds(675, 65, 150, 40);
		btnCon.setBackground(Color.orange);
		btnCon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				errUp = false;
				updated = 0;
				for(int obj = 0; obj < rows.length; obj++) {
					
					if((Boolean)tableOrders.getModel().getValueAt(obj, 7)) {
						
						Delivery_Order _do = new Delivery_Order();
						_do.setId(Integer.parseInt(tableOrders.getModel().getValueAt(obj, 0).toString()));
						_do.setStatus(1);
						psql.updateQuery(new DeliveryOrderDao(_do).update(0, psql), new InterfaceSuccessErrorDAO() {
							
							@Override
							public void ok() { updated++; }
							
							@Override
							public void err(String e) { errUp = true; }
						});
						
					}
					
				}
				
				if(!errUp) {
					JOptionPane.showMessageDialog(null, updated > 0 ? "Aggiornamento avvenuto con successo" : "Nessuna riga aggiornata");
					context.removeAll();
					context.repaint();
					context.revalidate();
					attach(context, psql, focusListener); // Ricrea istanza!
				}else {
					JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore di aggiornamento!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		
		// ---------------------------
		// -- Bottone cancellazione --
		// ---------------------------
		JButton btnDel = new JButton("Annulla");
		btnDel.setBounds(850, 65, 150, 40);
		btnDel.setBackground(Color.orange);
		btnDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				deleted = 0;
				for(int obj = 0; obj < rows.length; obj++) {
					
					if((Boolean)tableOrders.getModel().getValueAt(obj, 7)) {

						Delivery_Order _do = new Delivery_Order();
						_do.setId(Integer.parseInt(tableOrders.getModel().getValueAt(obj, 0).toString()));
						psql.deleteQuery(new DeliveryOrderDao(_do).delete(0, psql), new InterfaceSuccessErrorDAO() {
							
							@Override
							public void ok() { deleted++; }
							
							@Override
							public void err(String e) { errDel = true; }
						});
						
					}
					
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
		
		// Recupera ordine
		List<Delivery_Order> orders = (List<Delivery_Order>)new DeliveryOrderDao(new Delivery_Order()).select(0, psql);
		
		if(orders.size() > 0) {
	
			// Colonne tabella
			Object [] columns = {
				"Codice ordine", "Ristorante","CF cliente","CF driver","Totale", "Data ordine", "Quantità", "Seleziona ordine"
			};
	
			// Righe
			rows = new Object[orders.size()][8];
			
			int index = 0;
			for(Delivery_Order o : orders) {
				
				double qta = 
					((List<DeliveryOrderQuantity>) 
							new DeliveryOrderQuantityDAO(new DeliveryOrderQuantity())
								.select(0, psql, String.valueOf(o.getId()))).get(0).getQuantity();
				
				rows[index][0] = o.getId();
				rows[index][1] = o.getId_restaurant();
				rows[index][2] = o.getId_customer();
				rows[index][3] = o.getId_driver();
				rows[index][4] = "€ " + String.valueOf(String.format("%.2f", o.getTotale()));
				rows[index][5] = o.getDate_Order();
				rows[index][6] = (int) qta;
				rows[index][7] = Boolean.FALSE;
				index++;
				
			}
			
			tableOrders = new JTable(rows,columns) {
				@Override
			    public boolean isCellEditable(int row, int column) {
			        return column < 7 ? false : true; 
			    }
				
	            @Override
	            public Class<?> getColumnClass(int column) {
            		return column < 7 ? String.class : Boolean.class;
	            }
					
			};
			
			// ScrollPanel 
			JScrollPane sp = new JScrollPane(tableOrders);
			
			GenericResearch gr = new GenericResearch(new Delivery_Order());
			gr.appendElement(context, 10, 65, new IGet() {
				
				@Override
				public void put(String text) {
					

					Delivery_Order _do = null;
					try {
						_do = (Delivery_Order) gr.invokeSet(text);
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la ricerca: " + e.getMessage() , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}

					List<Delivery_Order> lDop = (List<Delivery_Order>)new DeliveryOrderDao(_do).select(2, psql, text);

					// Righe
					rows = new Object[lDop.size()][8];
					
					// Cicla per le associazioni
					int index = 0;
					for(Delivery_Order o : lDop) {
						
						double qta = 
							((List<DeliveryOrderQuantity>) 
									new DeliveryOrderQuantityDAO(new DeliveryOrderQuantity())
										.select(0, psql, String.valueOf(o.getId()))).get(0).getQuantity();
						
						rows[index][0] = o.getId();
						rows[index][1] = o.getId_restaurant();
						rows[index][2] = o.getId_customer();
						rows[index][3] = o.getId_driver();
						rows[index][4] = "€ " + String.valueOf(String.format("%.2f", o.getTotale()));
						rows[index][5] = o.getDate_Order();
						rows[index][6] = (int) qta;
						rows[index][7] = Boolean.FALSE;
						index++;
						
					}
					
					gr.getNewModel(tableOrders, columns, rows);
					
				}
				
			});
			
			JLabel lblCercaOrdine= new JLabel("Cerca ordine");
			lblCercaOrdine.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblCercaOrdine.setBounds(15, 25, 150, 35);
			
			
			sp.setBounds(10, 110, 1000, 550);
			context.add(sp);	
			context.add(btnDel);
			context.add(lblCercaOrdine);
			context.add(btnCon);
						
		}else{
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono ordini!");
		}
	}

}

