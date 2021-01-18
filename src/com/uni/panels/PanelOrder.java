package com.uni.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import com.uniproject.dao.CustomerDAO;
import com.uniproject.dao.DeliveryOrderDao;
import com.uniproject.dao.DeliveryOrderProductDAO;
import com.uniproject.dao.DriverCounterDeliveryDAO;
import com.uniproject.dao.DriverDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.dao.ProductDAO;
import com.uniproject.dao.RestaurantDAO;
import com.uniproject.entity.Customer;
import com.uniproject.entity.Delivery_Order;
import com.uniproject.entity.Delivery_Order_Product;
import com.uniproject.entity.Driver;
import com.uniproject.entity.DriverCounterDelivery;
import com.uniproject.entity.Product;
import com.uniproject.entity.Restaurant;
import com.uniproject.jdbc.PostgreSQL;

public class PanelOrder implements PanelAttachInterface{
	
	class Row{
		private int tipology; // 1 Prodotto - 0 Sconto
		private int id_prod;
		private String desc;
		private int quantity;
		private double vat_number;
		private double sconto;
		private double price;
		private double subTot;
		private boolean isPercentuale;
		private double valuePercent;
		
	}
	
	/**
	 * 
	 * @return
	 */
	private double getSubTot() {
		
		double subTot = 0.00;
		
		for(Row row : rows_sale) {
			if(row.tipology == 1) {
				subTot += ((row.price + (row.vat_number / 100 * row.price)) * row.quantity);
			}else {
				if(!row.isPercentuale) {
					subTot -= row.sconto;
				}else {
					double subPer = (subTot * row.valuePercent) / 100;
					subTot -= subPer;
				}
			}
			row.subTot = subTot;
		}
		
		return subTot;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	private double getSubTot(int index) {
		
		double subTot = 0.00;
		
		for(int i = 0; i < index; i++) {
			if(rows_sale.get(i).tipology == 1) {
				subTot += ((rows_sale.get(i).price + (rows_sale.get(i).vat_number / 100 * rows_sale.get(i).price)) * rows_sale.get(i).quantity);
			}else {
				if(!rows_sale.get(i).isPercentuale) {
					subTot -= rows_sale.get(i).sconto;
				}else {
					double subPer = (subTot * rows_sale.get(i).valuePercent) / 100;
					subTot -= subPer;
				}
			}
			rows_sale.get(i).subTot = subTot;
		}
		
		return subTot;
	}
	
	/**
	 * 
	 * @param table_prods
	 */
	private void clickTable(JTable table_prods, Row row_param, int incr, boolean isOnlyRepaint) {
		
		if(!isOnlyRepaint) {
			
			int id = row_param != null ? row_param.id_prod : Integer.parseInt(table_prod.getValueAt(table_prod.getSelectedRow(), 0).toString());
			
			boolean founded = false;
			for(Row row_sale : rows_sale) {
				if(row_sale.id_prod == id) {
					row_sale.quantity += incr;
					founded = true;
					break;
				}
			}	
				
			if(!founded) {
				Row row = new Row();
				row.id_prod    = Integer.parseInt(table_prod.getValueAt(table_prod.getSelectedRow(), 0).toString());
				row.desc 	   = table_prod.getValueAt(table_prod.getSelectedRow(), 1).toString();
				row.price 	   = Double.parseDouble(table_prod.getValueAt(table_prod.getSelectedRow(), 2).toString());
				row.vat_number = Double.parseDouble(table_prod.getValueAt(table_prod.getSelectedRow(), 3).toString());
				row.quantity   = 1;
				row.tipology   = 1;
				rows_sale.add(row);
			}
		}
		
		double tot = getSubTot();
		
		if(tot > 0) {
			
			lb_tot.setText("Totale : € " + String.format("%.2f", tot));
			
			panel_prods.removeAll();
			int y = 0;
			
			int rowIndex = 0;
			for(Row row_sale : rows_sale) {
				
				JPanel panel_row = new JPanel(null);
				panel_row.setBounds(0, y, 400, 110);
				panel_row.setBorder(new EtchedBorder());
				
				JPanel panel_desc = new JPanel(null);
				panel_desc.setBounds(10, 10, 200, 80);
				
				JLabel lb_title = new JLabel(row_sale.desc);
				lb_title.setHorizontalAlignment(SwingConstants.CENTER);
				lb_title.setBounds(0, 10, 200, 20);
				lb_title.setFont(new Font("Tahoma", Font.BOLD, 15));

				if(row_sale.tipology == 1) {
					
					double tot_value = Double.valueOf(row_sale.price) + (Double.valueOf(row_sale.vat_number) / 100 * Double.valueOf(row_sale.price)) ;
					JLabel lb_price_quantity = new JLabel(tot_value + " x " + String.valueOf(row_sale.quantity) + " (IVA " + String.valueOf(row_sale.vat_number).replace(".0", "%") + ")");
					
					lb_price_quantity.setHorizontalAlignment(SwingConstants.CENTER);
					lb_price_quantity.setBounds(0, 30, 200, 20); 
					lb_price_quantity.setFont(new Font("Tahoma", Font.ITALIC, 15));
					panel_desc.add(lb_price_quantity);
				}else {
					JLabel lb_sub_tot = new JLabel("SubTOT " + String.format( "%.2f", row_sale.subTot ));
					lb_sub_tot.setHorizontalAlignment(SwingConstants.CENTER);
					lb_sub_tot.setBounds(0, 30, 200, 20);
					lb_sub_tot.setFont(new Font("Tahoma", Font.ITALIC, 15));
					panel_desc.add(lb_sub_tot);
				}
				
				panel_desc.add(lb_title);
				panel_row.add(panel_desc);
				
				JPanel operative_panel = new JPanel(null);
				operative_panel.setBounds(210, 10, 100, 90);
				
				JButton button_qta_plus = new JButton("+");
				button_qta_plus.setBounds(0, 0, 40, 40);
				if(row_sale.tipology == 1)
					operative_panel.add(button_qta_plus);
				button_qta_plus.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						clickTable(null, row_sale, 1, false);
					}
				});

				JButton button_qta_min = new JButton("-");
				button_qta_min.setBounds(45, 0, 40, 40);
				button_qta_min.putClientProperty("index", rowIndex);
				if(row_sale.tipology == 1)
					operative_panel.add(button_qta_min);
				button_qta_min.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						int index = Integer.parseInt(button_qta_min.getClientProperty("index").toString());
						
						if(row_sale.quantity >= 1) {
							
							row_sale.quantity -= 1;
							double subTot = getSubTot();
							if(subTot > 0.00) {
								if(row_sale.quantity == 0) {
									rows_sale.remove(index);
								}
								clickTable(table_prods, row_param, 0, true);
							}else {
								row_sale.quantity += 1;
							}
							
						}
						
					}
				});
				
				JButton button_qta_remove = new JButton("X");
				button_qta_remove.putClientProperty("index", rowIndex);
				button_qta_remove.setBounds(0, 45, 40, 40);
				operative_panel.add(button_qta_remove);
				button_qta_remove.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						int index = Integer.parseInt(button_qta_remove.getClientProperty("index").toString());
						Row row = rows_sale.get(index); 
						rows_sale.remove(Integer.parseInt(button_qta_remove.getClientProperty("index").toString()));
						if(getSubTot() <= 0.00) {
							rows_sale.add(index, row);
						}else {
							clickTable(table_prods, row_param, 0, true);
						}
					}
				});

				JButton button_qta_sc = new JButton("S");
				button_qta_sc.setBounds(45, 45, 40, 40);
				button_qta_sc.putClientProperty("index", rowIndex);
				operative_panel.add(button_qta_sc);
				button_qta_sc.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						String savedSconto = "";
						
						String sconto = JOptionPane.showInputDialog("Inserisci valore di sconto (% per sconti in percentuale)");
						savedSconto = sconto;

						boolean isPercentuale = false;
						if(sconto.endsWith("%")) {
							sconto = sconto.replace("%", "");
							isPercentuale = true;
						}
						
						sconto = sconto.replace(",", ".");
						try {
							
							double sct = Double.parseDouble(sconto);
							double savedValuePercent = sct;
							
							Row row_sconto = new Row();
							row_sconto.desc 	= "Sconto " + savedSconto;
							//row_sconto.sconto 	= sct;
							row_sconto.tipology = 0;
							
							int index = Integer.parseInt(button_qta_sc.getClientProperty("index").toString()) + 1;
							rows_sale.add(index, row_sconto);
							
							double subTot = getSubTot(index);
							if(isPercentuale) {
								sct = (subTot * sct) / 100;
								subTot -= sct;
							}else {
								subTot -= sct;
							}
							row_sconto.sconto = sct;
							
							if(subTot <= 0.00) {
								rows_sale.remove(index);
							}else {
								row_sconto.isPercentuale = isPercentuale;
								if(row_sconto.isPercentuale) {
									row_sconto.valuePercent = savedValuePercent;
								}
								clickTable(table_prods, row_param, incr, true);
							}
							
						}catch(Exception err) {
							JOptionPane.showMessageDialog(null, "Sconto non valido", "Errore", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				});
				
				panel_row.add(operative_panel);
				
				panel_prods.add(panel_row);
				y += 100;
				
				rowIndex++;
			}
			panel_prods.setPreferredSize(new Dimension(400, y));
			panel_prods.revalidate();
			panel_prods.repaint();
		}
	}
	
	// ------------------------
	// -- Modello di vendita --
	// ------------------------
	class ModelVendita{
		
		private String code_restaurant = "";
		private String fiscal_code_customer = "";
		private String fiscal_code_driver = "";
		private List<ModelProduct> products;
		
		public ModelVendita() {
			products = new ArrayList<>();
		}
		
	}
	
	// --------------------------
	// -- Modello del prodotto --
	// --------------------------
	class ModelProduct{
		private int id_product;
		private int quantity;
	}
	
	// Pannelli
	private JPanel panel_order_cliente;
	private JPanel panel_order_trasporto;
	private JPanel panel_order_driver;
	private JPanel panel_order_filter_allergy;
	private JPanel panel_order_filter_price;
	private JPanel panel_order_product;
	private JPanel panel_container_table_product;
	private JPanel panel_order_restaurant;
	private JPanel panel_riepilogo;
	private JPanel panel_prods;
	
	// Bottoni
	private JButton btn_order_cliente;
	private JButton btn_order_driver;
	private JButton btn_order_save_order;
	
	// Label
	private JLabel lb_order_cliente;
	private JLabel lb_order_restaurant;
	private JLabel lb_order_driver;
	private JLabel lb_order_products;
	
	// Combobox
	private JComboBox<String> combo_order_select_transport;
	
	// Checkbox
	private JCheckBox check_order_filter_allergy;
	private JCheckBox check_order_filter_price;
	
	// Oggetti grafici
	private JTextField txtPriceMin;
	private JTextField txtPriceMax;
	
	// Tabella ristoranti
	private DefaultTableModel dtm_rest;
	private JTable table_rest;
	
	// Tabella prodotti
	private DefaultTableModel dtm_prod;
	private JTable table_prod;
	
	// Dialogs
	private JDialog dialogCustomer;
	private JDialog dialogDriver;
	
	// Codice ristorante selezionato
	private String selectedCodeRestaurant;
	
	// Codice fiscale cliente
	private String fiscal_code_customer;
	
	// Liste
	private List<Restaurant> restaurants;
	private List<Customer>   customers;
	private List<Product>    products;
	private List<Driver>     drivers;
	
	// Lista riepilogo
	private List<Row> rows_sale;
	
	// Totale
	private JLabel lb_tot;
	
	// Modelli
	private ModelVendita modelVendita;
	
	// Righe dettaglio
	private int inserted_row_detailt;
	
	public PanelOrder() {
		rows_sale 		= new ArrayList<>();
		modelVendita 	= new ModelVendita();
	}
	
	@SuppressWarnings("serial")
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
	
		
		// Lista dei ristoranti
		restaurants   = (List<Restaurant>) new RestaurantDAO(new Restaurant()).select(0, psql);
		customers     = (List<Customer>)   new CustomerDAO(new Customer()).select(0, psql);
		products      = (List<Product>)    new ProductDAO(new Product()).select(0, psql);
		drivers       = (List<Driver>)     new DriverDAO(new Driver()).select(0, psql);
		
		String e = "";
		
		if( restaurants.size() == 0 ) { e = "Non ci sono ristoranti."; }

		if( customers.size() == 0 )   { e = "Non ci sono clienti."; }
		
		if( products.size() == 0 )    { e = "Non ci sono prodotti."; }
		
		if( drivers.size() == 0 )     { e = "Non ci sono drivers."; }
		
		if(!e.equals("")) {
			JOptionPane.showMessageDialog(null, e + " Impossibile effettuare ordini!");
			return;
		}
		
		String [] columns_rest = { "Codice", "Nome Ristorante" };
		String [][] rows       = new String[restaurants.size()][2];
		
		int index = 0;
		for(Restaurant rest : restaurants) {
			rows[index][0] = rest.getId_restaurant();
			rows[index][1] = rest.getName();
			index++;
		}
		dtm_rest   = new DefaultTableModel(rows, columns_rest);
		table_rest = new JTable(dtm_rest) {
			
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
			
		};
		
		
		 table_rest.getColumnModel().getColumn(0).setPreferredWidth(10);
		 table_rest.getColumnModel().getColumn(1).setPreferredWidth(200);
		
		table_rest.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				selectedCodeRestaurant = (String)table_rest.getValueAt(table_rest.getSelectedRow(), 0);
				
				if(!modelVendita.code_restaurant.equals("") && !selectedCodeRestaurant.equals(modelVendita.code_restaurant)) {
					int option = JOptionPane.showConfirmDialog(null, "Sicuro di voler cambiare ristorante", "Attenzione", JOptionPane.YES_NO_OPTION);
					boolean cancel = option == JOptionPane.YES_OPTION ? true : false;
					if(cancel) {
						lb_tot.setText("Totale : € 0,00");
						rows_sale.clear();
						panel_prods.removeAll();
						panel_prods.repaint();
					}else {
						return;
					}
				}
				
				if(check_order_filter_allergy.isSelected())
					products = (List<Product>) new ProductDAO(new Product()).select(4, psql, fiscal_code_customer, selectedCodeRestaurant);
				else
					products = (List<Product>) new ProductDAO(new Product()).select(3, psql, selectedCodeRestaurant);
			
				// Etichetta seleziona prodotti :
				lb_order_products = new JLabel("Seleziona prodotti : ");
				lb_order_products.setFont(new Font("Tahoma", Font.BOLD, 16));
				lb_order_products.setBounds(15, 10, 317, 42);
				panel_order_product.add(lb_order_products);
				
				// Nuove righe
				String [] columns_prod      = { "Codice", "Nome", "Prezzo", "IVA" };
				String [][] rows_prod       = new String[products.size()][4];
				
				int indexProd = 0;
				for(Product prod : products) {
					rows_prod[indexProd][0] = String.valueOf(prod.getId());
					rows_prod[indexProd][1] = prod.getName();
					rows_prod[indexProd][2] = String.valueOf(prod.getPrice());
					rows_prod[indexProd][3] = String.valueOf(prod.getVat_number());
					indexProd++;
				}
				dtm_prod   = new DefaultTableModel(rows_prod, columns_prod);
				table_prod = new JTable(dtm_prod) {
					@Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				 table_prod.getColumnModel().getColumn(0).setPreferredWidth(0);
				 table_prod.getColumnModel().getColumn(1).setPreferredWidth(80);
				 table_prod.getColumnModel().getColumn(2).setPreferredWidth(0);
				 table_prod.getColumnModel().getColumn(3).setPreferredWidth(0);
				
				table_prod.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						clickTable(table_prod, null, 1, false);
					}
				});
				
				// Registra codice ristorante!
				modelVendita.code_restaurant = selectedCodeRestaurant;
				
				panel_order_product.removeAll();
				JScrollPane scroll_table_prod = new JScrollPane(table_prod);
				scroll_table_prod.setBounds(10, 40, 404, 200);
				panel_order_product.add(scroll_table_prod);
				panel_order_product.add(lb_order_products);
				panel_order_product.repaint();
				
			}
			
		});
		
		String [] columns_prod      = { "Codice", "Nome", "Prezzo", "IVA" };
		String [][] rows_prod       = new String[products.size()][4];
		
		int indexProd = 0;
		for(Product prod : products) {
			rows_prod[indexProd][0] = String.valueOf(prod.getId());
			rows_prod[indexProd][1] = prod.getName();
			rows_prod[indexProd][2] = String.valueOf(prod.getPrice());
			rows_prod[indexProd][3] = String.valueOf(prod.getVat_number());
			indexProd++;
		}
		dtm_prod   = new DefaultTableModel(rows_prod, columns_prod);
		table_prod = new JTable(dtm_prod) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		 table_prod.getColumnModel().getColumn(0).setPreferredWidth(0);
		 table_prod.getColumnModel().getColumn(1).setPreferredWidth(80);
		 table_prod.getColumnModel().getColumn(2).setPreferredWidth(0);
		 table_prod.getColumnModel().getColumn(3).setPreferredWidth(0);
		
		panel_order_cliente = new JPanel();
		panel_order_cliente.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order_cliente.setBounds(10, 302, 424, 64);
		panel_order_cliente.setLayout(null);
		//ALDO PROVE
		lb_order_cliente = new JLabel("Cliente : ");
		lb_order_cliente.setFont(new Font("Tahoma", Font.BOLD, 16));
		lb_order_cliente.setBounds(10, 11, 317, 42);
		panel_order_cliente.add(lb_order_cliente);
		
		btn_order_cliente = new JButton("Cerca");
		btn_order_cliente.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String [] columns   = { "Codice fiscale cliente", "Nome", "Cognome" };
				String [][] rows    = new String[customers.size()][3];
				
				int index = 0;
				for(Customer customer : customers) {
					rows[index][0] = customer.getFiscal_code();
					rows[index][1] = customer.getName();
					rows[index][2] = customer.getSurname();
					index++;
				}
				
				DefaultTableModel dtm = new DefaultTableModel(rows, columns);
				JTable tableCustomer = new JTable(dtm);
				JScrollPane scrollPane = new JScrollPane(tableCustomer);
				
				tableCustomer.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						fiscal_code_customer = (String)tableCustomer.getValueAt(tableCustomer.getSelectedRow(), 0);
						lb_order_cliente.setText("Cliente : " + fiscal_code_customer);
						modelVendita.fiscal_code_customer = fiscal_code_customer;
						dialogCustomer.dispose();
						check_order_filter_allergy.setEnabled(true);
						check_order_filter_allergy.setSelected(false);
						 {
		                    products = (List<Product>) new ProductDAO(new Product()).select ( 3 , psql, selectedCodeRestaurant);
		                   
		                    String [] columns_prod      = { "Codice", "Nome", "Prezzo", "IVA" };
		                    String [][] rows_prod       = new String[products.size()][4];
		                   
		                    int indexProd = 0;
		                    for(Product prod : products) {
		                        rows_prod[indexProd][0] = String.valueOf(prod.getId());
		                        rows_prod[indexProd][1] = prod.getName();
		                        rows_prod[indexProd][2] = String.valueOf(prod.getPrice());
		                        rows_prod[indexProd][3] = String.valueOf(prod.getVat_number());
		                        indexProd++;
		                    }
		                    dtm_prod   = new DefaultTableModel(rows_prod, columns_prod);
		                    //table_prod.addMouseListener(table_prod.getMouseListeners()[0]);
		                    table_prod.setModel(dtm_prod);
		               
		                     table_prod.getColumnModel().getColumn(0).setPreferredWidth(0);
		                     table_prod.getColumnModel().getColumn(1).setPreferredWidth(80);
		                     table_prod.getColumnModel().getColumn(2).setPreferredWidth(0);
		                     table_prod.getColumnModel().getColumn(3).setPreferredWidth(0);
		                   
		                    panel_order_product.removeAll();
		                    JScrollPane scroll_table_prod = new JScrollPane(table_prod);
		                    scroll_table_prod.setBounds(10, 40, 404, 200);
		                    panel_order_product.add(scroll_table_prod);
		                    panel_order_product.add(lb_order_products);
		                    panel_order_product.repaint();
		                }
							
					}
					
				});
				
				dialogCustomer = new JDialog();
				dialogCustomer.setLocationRelativeTo(null);
				dialogCustomer.add(scrollPane);
				dialogCustomer.pack();
				dialogCustomer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogCustomer.setVisible(true);
				
			}
			
		});
		btn_order_cliente.setBounds(337, 11, 77, 42);
		panel_order_cliente.add(btn_order_cliente);
		
		panel_order_trasporto = new JPanel();
		panel_order_trasporto.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order_trasporto.setBounds(10, 377, 424, 64);
		panel_order_trasporto.setLayout(null);
		
		combo_order_select_transport = new JComboBox<String>();
		combo_order_select_transport.setModel(new DefaultComboBoxModel<String>(new String[] {"-- Mezzo di trasporto --", "Automobile", "Scooter", "Bicicletta"}));
		combo_order_select_transport.setFont(new Font("Tahoma", Font.BOLD, 14));
		combo_order_select_transport.setBounds(10, 11, 404, 42);
		combo_order_select_transport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!combo_order_select_transport.getSelectedItem().toString().equals("-- Mezzo di trasporto --")) {
					btn_order_driver.setEnabled(true);
				}else {
					lb_order_driver.setText("Driver : ");
					btn_order_driver.setEnabled(false);
					modelVendita.fiscal_code_driver = "";
				}
				
			}
			
		});
		panel_order_trasporto.add(combo_order_select_transport);
		
		panel_order_driver = new JPanel();
		panel_order_driver.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order_driver.setBounds(10, 452, 424, 64);
		panel_order_driver.setLayout(null);
		
		lb_order_driver = new JLabel("Driver :");
		lb_order_driver.setFont(new Font("Tahoma", Font.BOLD, 16));
		lb_order_driver.setBounds(10, 11, 317, 42);
		panel_order_driver.add(lb_order_driver);
		
		btn_order_driver = new JButton("Cerca");
		btn_order_driver.setEnabled(false);
		btn_order_driver.setBounds(337, 11, 77, 42);
		btn_order_driver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				List<DriverCounterDelivery> dcd = 
						(List<DriverCounterDelivery>)new DriverCounterDeliveryDAO(new DriverCounterDelivery()).select(0, psql);
				
				String [] columns   = { "Codice fiscale driver", "Nome", "Cognome", "Stato" };
				String [][] rows    = new String[drivers.size()][4];
				
				int index = 0;
				for(DriverCounterDelivery driver : dcd) {
					rows[index][0] = driver.getFiscal_code();
					rows[index][1] = driver.getName();
					rows[index][2] = driver.getSurname();
					rows[index][3] = driver.getOrdini() < 3 ? "Disponibile" : "Occupato";
					index++;
				}	
				
				DefaultTableModel dtm = new DefaultTableModel(rows, columns);
				JTable tableDriver = new JTable(dtm);
				JScrollPane scrollPane = new JScrollPane(tableDriver);
				
				tableDriver.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						if(((String)tableDriver.getValueAt(tableDriver.getSelectedRow(), 3)).equals("Occupato")) {
							JOptionPane.showMessageDialog(null, "Driver occupato", "Attenzione", JOptionPane.WARNING_MESSAGE);
							return;
						}
						String fiscal_code = (String)tableDriver.getValueAt(tableDriver.getSelectedRow(), 0);
						lb_order_driver.setText("Driver : " + fiscal_code);
						modelVendita.fiscal_code_driver = fiscal_code;
						dialogDriver.dispose();
					}
					
				});
				
				dialogDriver = new JDialog();
				dialogDriver.setLocationRelativeTo(null);
				dialogDriver.add(scrollPane);
				dialogDriver.pack();
				dialogDriver.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialogDriver.setVisible(true);
				
			}
		});
		
		panel_order_driver.add(btn_order_driver);
		
		panel_order_filter_allergy = new JPanel();
		panel_order_filter_allergy.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order_filter_allergy.setBounds(444, 270, 424, 64);
		panel_order_filter_allergy.setLayout(null);
		
		check_order_filter_allergy = new JCheckBox(" Filtra prodotti per allergie cliente");
		check_order_filter_allergy.setEnabled(false);
		check_order_filter_allergy.setFont(new Font("Tahoma", Font.BOLD, 14));
		check_order_filter_allergy.setBounds(6, 7, 412, 50);
		check_order_filter_allergy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(check_order_filter_allergy.isSelected()) 
					products = (List<Product>) new ProductDAO(new Product()).select(4, psql, fiscal_code_customer, selectedCodeRestaurant);
				else
					products = (List<Product>) new ProductDAO(new Product()).select ( 3 , psql, selectedCodeRestaurant);
					
				// Nuove righe
				String [] columns_prod      = { "Codice", "Nome", "Prezzo", "IVA" };
				String [][] rows_prod       = new String[products.size()][4];
				
				int indexProd = 0;
				for(Product prod : products) {
					rows_prod[indexProd][0] = String.valueOf(prod.getId());
					rows_prod[indexProd][1] = prod.getName();
					rows_prod[indexProd][2] = String.valueOf(prod.getPrice());
					rows_prod[indexProd][3] = String.valueOf(prod.getVat_number());
					indexProd++;
				}
				dtm_prod   = new DefaultTableModel(rows_prod, columns_prod);
				//table_prod.addMouseListener(table_prod.getMouseListeners()[0]);
				table_prod.setModel(dtm_prod);
			
				 table_prod.getColumnModel().getColumn(0).setPreferredWidth(0);
				 table_prod.getColumnModel().getColumn(1).setPreferredWidth(80);
				 table_prod.getColumnModel().getColumn(2).setPreferredWidth(0);
				 table_prod.getColumnModel().getColumn(3).setPreferredWidth(0);
				
				panel_order_product.removeAll();
				JScrollPane scroll_table_prod = new JScrollPane(table_prod);
				scroll_table_prod.setBounds(10, 40, 404, 200);
				panel_order_product.add(scroll_table_prod);
				panel_order_product.add(lb_order_products);
				panel_order_product.repaint();
					

            }
           
        });
		
		panel_order_filter_allergy.add(check_order_filter_allergy);
		//INIZIO
		panel_order_filter_price = new JPanel();
		panel_order_filter_price.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order_filter_price.setBounds(444, 345, 424, 64);
		panel_order_filter_price.setLayout(null);
		
		check_order_filter_price = new JCheckBox(" Filtra prodotti per prezzo (MIN-MAX)");
		check_order_filter_price.setEnabled(true);
		check_order_filter_price.setFont(new Font("Tahoma", Font.BOLD, 14));
		check_order_filter_price.setBounds(6, 7, 293, 50);
		
		txtPriceMin = new JTextField();
		txtPriceMin.setText("0");
		txtPriceMin.setHorizontalAlignment(SwingConstants.CENTER);
		txtPriceMin.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtPriceMin.setBackground(SystemColor.menu);
		txtPriceMin.setBounds(309, 10, 50, 45);
		txtPriceMin.addFocusListener(focusListener);
		txtPriceMin.putClientProperty("tipology", "double");
		panel_order_filter_price.add(txtPriceMin);
		
		txtPriceMax = new JTextField();
		txtPriceMax.setText("1000");
		txtPriceMax.setHorizontalAlignment(SwingConstants.CENTER);
		txtPriceMax.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtPriceMax.setBackground(SystemColor.menu);
		txtPriceMax.setBounds(364, 10, 50, 45);
		txtPriceMax.addFocusListener(focusListener);
		txtPriceMax.putClientProperty("tipology", "double");
		panel_order_filter_price.add(txtPriceMax);
		
		panel_order_filter_price.add(check_order_filter_price);
		//FINE
		panel_order_product = new JPanel();
		panel_order_product.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order_product.setBounds(444, 11, 425, 250);
		panel_order_product.setLayout(null);
		
		panel_container_table_product = new JPanel();
		panel_container_table_product.setBounds(10, 11, 304, 107);
		panel_order_product.add(panel_container_table_product);
		
		panel_order_restaurant = new JPanel(null);
		panel_order_restaurant.setPreferredSize(new Dimension(400, 400));
		panel_order_restaurant.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_order_restaurant.setBounds(10, 11, 424, 280);
		
		//ETICHETTA RISTORANTI
		lb_order_restaurant = new JLabel("Seleziona un ristorante : ");
		lb_order_restaurant.setFont(new Font("Tahoma", Font.BOLD, 16));
		lb_order_restaurant.setBounds(10,5,317,42);
		panel_order_restaurant.add(lb_order_restaurant);
		
		//TABELLA RISTORANTI
		JScrollPane scroll_table_rest = new JScrollPane(table_rest);
		scroll_table_rest.setBounds(10, 40, 404, 200);
		scroll_table_rest.setBorder(new EtchedBorder());
		scroll_table_rest.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll_table_rest.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel_order_restaurant.add(scroll_table_rest);
		
		panel_riepilogo = new JPanel(null);
		panel_riepilogo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_riepilogo.setBounds(880, 11, 451, 505);
		
		JLabel lb_riepilogo = new JLabel("Riepilogo");
		lb_riepilogo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_riepilogo.setBounds(10, 10, 250, 30);
		panel_riepilogo.add(lb_riepilogo);
		
		panel_prods = new JPanel(null);
		panel_prods.setPreferredSize(new Dimension(400, 400));
		
		JScrollPane scroll_products = new JScrollPane(panel_prods);
		scroll_products.setBounds(10, 50, 410, 400);
		scroll_products.setBorder(new EtchedBorder());
		scroll_products.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll_products.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel_riepilogo.add(scroll_products);
		
		lb_tot = new JLabel("Totale: € 0,00");
		lb_tot.setFont(new Font("Tahoma", Font.BOLD, 20));
		lb_tot.setBounds(10, 460, 410, 40);
		panel_riepilogo.add(lb_tot);
		
		// Effettua l'ordine
		btn_order_save_order = new JButton("Effettua ordine");
		btn_order_save_order.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_order_save_order.setBounds(880, 525, 165, 48);
		
		btn_order_save_order.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(modelVendita.fiscal_code_customer.equals("")) {
					JOptionPane.showMessageDialog(null, "Associare cliente!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(modelVendita.fiscal_code_driver.equals("")) {
					JOptionPane.showMessageDialog(null, "Associare driver!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(rows_sale.size() == 0) {
					JOptionPane.showMessageDialog(null, "Selezionare prodotti!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				for(Row row : rows_sale) {
					if(row.tipology == 1) {
						ModelProduct model_product = new ModelProduct();
						model_product.id_product = row.id_prod;
						model_product.quantity = row.quantity;
						modelVendita.products.add(model_product);
					}
				}
				
				// Totale
				double totale = Double.parseDouble(lb_tot.getText().replaceAll("Totale : € ", "").replaceAll(",", ".").trim());
				
				// Inserisci la testa dell'ordine
				Delivery_Order delivery_order_head = new Delivery_Order();
				delivery_order_head.setId_customer(modelVendita.fiscal_code_customer);
				delivery_order_head.setId_driver(modelVendita.fiscal_code_driver);
				delivery_order_head.setId_restaurant(modelVendita.code_restaurant);
				delivery_order_head.setStatus(0);
				delivery_order_head.setTotale(totale);
				
				psql.insertQuery(
					new DeliveryOrderDao(delivery_order_head).insert(0, psql),
					new InterfaceSuccessErrorDAO() {
						
						@Override
						public void ok() {
							
							int id = ((List<Delivery_Order>)new DeliveryOrderDao(new Delivery_Order()).select(1, psql)).get(0).getId();
							int toInsert = modelVendita.products.size();
							
							for(ModelProduct modelProduct : modelVendita.products) {
								
								Delivery_Order_Product deliveryOrderProduct = new Delivery_Order_Product();
								deliveryOrderProduct.setId_order(id);
								deliveryOrderProduct.setId_product(modelProduct.id_product);
								deliveryOrderProduct.setQuantity(modelProduct.quantity);
								
								psql.insertQuery(new DeliveryOrderProductDAO(deliveryOrderProduct).insert(0, psql), new InterfaceSuccessErrorDAO() {
									
									@Override
									public void ok() {
										inserted_row_detailt++;
									}
									
									@Override
									public void err(String e) {
										System.out.println(e);
									}
									
								});
								
							}
							
							if(inserted_row_detailt == toInsert) {
								JOptionPane.showMessageDialog(null, "Ordine effettuato con successo!");
								context.removeAll();
								context.repaint();
								context.revalidate();
								attach(context, psql, focusListener); // Ricrea istanza!
							}else {
								JOptionPane.showMessageDialog(null, "Si è verificato un errore durante l'ordinazione", "Errore", JOptionPane.ERROR_MESSAGE);
							}
							
						}
						
						@Override
						public void err(String e) {
							JOptionPane.showMessageDialog(null, "Si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}
				);
				
				
			}
			
		});
		
		context.add(panel_order_cliente);
		context.add(panel_order_trasporto);
		context.add(panel_order_driver);
		context.add(panel_order_filter_allergy);
		context.add(panel_order_filter_price);
		context.add(panel_order_product);
		context.add(panel_order_restaurant);
		context.add(panel_riepilogo);
		context.add(btn_order_save_order);
		context.add(btn_order_save_order);
	}
	
}
