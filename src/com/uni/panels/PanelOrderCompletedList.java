package com.uni.panels;

import java.awt.Font;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


import com.uniproject.entity.DeliveryOrderQuantity;
import com.uniproject.entity.Delivery_Order;
import com.uniproject.dao.DeliveryOrderDao;
import com.uniproject.dao.DeliveryOrderQuantityDAO;
import com.uniproject.jdbc.PostgreSQL;


public class PanelOrderCompletedList {
	// Riga selezionata
	private int rowSelected = 0;
	
	// Tabella
	private JTable tableDrivers;
	
	// Righe tabella
	private Object [][] rows;

	// Tabella
	private JTable tableOrders;
	
	// Result callback
	private boolean ok = false;
	
	@SuppressWarnings("serial")
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		// Recupera ordine
		List<Delivery_Order> orders = (List<Delivery_Order>)new DeliveryOrderDao(new Delivery_Order()).select(3, psql);
		
		if(orders.size() > 0) {
	
			// Colonne tabella
			Object [] columns = {
				"Codice ordine", "Ristorante","CF cliente","CF driver","Totale", "Data ordine", "Quantità"
			};
	
			// Righe
			rows = new Object[orders.size()][7];
			
			int index = 0;
			for(Delivery_Order o : orders) {
				
				double qta = 
					((List<DeliveryOrderQuantity>) 
							new DeliveryOrderQuantityDAO(new DeliveryOrderQuantity())
								.select(3, psql, String.valueOf(o.getId()))).get(0).getQuantity();
				
				rows[index][0] = o.getId();
				rows[index][1] = o.getId_restaurant();
				rows[index][2] = o.getId_customer();
				rows[index][3] = o.getId_driver();
				rows[index][4] = "€ " + String.valueOf(String.format("%.2f", o.getTotale()));
				rows[index][5] = o.getDate_Order();
				rows[index][6] = (int) qta;
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

					List<Delivery_Order> lDop = (List<Delivery_Order>)new DeliveryOrderDao(_do).select(4, psql, text);

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
			context.add(lblCercaOrdine);
						
		}else{
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono ordini!");
		}
	}

}

