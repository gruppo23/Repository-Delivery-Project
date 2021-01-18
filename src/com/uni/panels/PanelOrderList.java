package com.uni.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationTargetException;
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
import com.uniproject.dao.DeliveryOrderProductDAO;
import com.uniproject.dao.DeliveryOrderQuantityDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.entity.Delivery_Order_Product;
import com.uniproject.jdbc.PostgreSQL;


public class PanelOrderList {
	// Riga selezionata
	private int rowSelected = 0;
	
	// Cancellato
	private int deleted  = 0;
	
	// Tabella
	private JTable tableDrivers;
		
	// Errore delete
	private boolean errDel = false;
	// Righe tabella
	private Object [][] rows;

	// Tabellao
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

				
			}
			
		});
		
		// ---------------------------
		// -- Bottone cancellazione --
		// ---------------------------
		JButton btnDel = new JButton("Elimina");
		btnDel.setBounds(850, 65, 150, 40);
		btnDel.setBackground(Color.orange);
		btnDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				deleted = 0;
				for(int obj = 0; obj < rows.length; obj++) {
					
					if((Boolean)tableOrders.getModel().getValueAt(obj, 6)) {

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
				"Codice ordine", "Ristorante","CF cliente","CF driver","Totale", "Quantità", "Elimina ordine"
			};
	
			// Righe
			rows = new Object[orders.size()][7];
			
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
				rows[index][5] = (int) qta;
				rows[index][6] = Boolean.FALSE;
				index++;
				
			}
			
			tableOrders = new JTable(rows,columns) {
				@Override
			    public boolean isCellEditable(int row, int column) {
			        return column < 6 ? false : true; 
			    }
				
	            @Override
	            public Class<?> getColumnClass(int column) {
            		return column < 6 ? String.class : Boolean.class;
	            }
					
			};
			
			// ScrollPanel 
			JScrollPane sp = new JScrollPane(tableOrders);
			
			GenericResearch gr = new GenericResearch(new Delivery_Order_Product());
			gr.appendElement(context, 10, 65, new IGet() {
				
				@Override
				public void put(String text) {
					
					Delivery_Order_Product dOrderProduct = null;
					Delivery_Order_Product dOrder = null;
					try {
						dOrderProduct = (Delivery_Order_Product) gr.invokeSet(text);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						JOptionPane.showMessageDialog(null, "Si è verificato un errore durante la ricerca: " + e.getMessage() , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					List<Delivery_Order_Product> dOrderProducts = (List<Delivery_Order_Product>)new DeliveryOrderProductDAO(dOrderProduct).select(0, psql);
					List<Delivery_Order> dOrders= (List<Delivery_Order>)new DeliveryOrderDao(dOrder).select(0, psql);
					// Righe
					rows = new Object[dOrderProducts.size()][9];
					
					// Cicla per le associazioni
					int index1 = 0;
					int index2 = 0;
					for(Delivery_Order_Product dop : dOrderProducts) {
						rows[index1][0] = dop.getId();
						rows[index1][1] = dop.getId_product();
						rows[index1][2] = dop.getId_order();
						rows[index1][3] = dop.getQuantity();
						index1++;
					}
					for(Delivery_Order o: dOrders ) {
						rows[index2][4] = o.getId_restaurant();
						rows[index2][5] = o.getId_customer();
						rows[index2][6] = o.getId_driver();
						rows[index2][7] ="€ "+ o.getTotale();
						rows[index2][8] = Boolean.FALSE;
						index2++;
				}
					
					gr.getNewModel(tableOrders, columns, rows);
					
				}
			});
			
			JLabel lblCercaOrdine= new JLabel("Cerca ordine in base all'id");
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

