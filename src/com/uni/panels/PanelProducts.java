package com.uni.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import com.uni.frame.Form;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.dao.ProductDAO;
import com.uniproject.dao.RelationRestaurantProductDAO;
import com.uniproject.dao.RestaurantDAO;
import com.uniproject.dao.Restaurant_ProductDAO;
import com.uniproject.entity.Product;
import com.uniproject.entity.Relation_RestaurantProduct;
import com.uniproject.entity.Restaurant;
import com.uniproject.entity.Restaurant_Product;
import com.uniproject.jdbc.PostgreSQL;

public class PanelProducts implements PanelAttachInterface {

	// Resulset della select
	int indexProds = 0;
	
	// Percorso immagine
	private String filePath = null;
	
	// Id prodotto selezionato
	private int idProduct = -1;
	
	// List di ristoranti
	private List<Restaurant> rests;
	
	// Lista di relazioni
	private List<Relation_RestaurantProduct> relationRestaurantProducts;
	
	/**
	 * 
	 * @param context
	 * @param psql
	 * @param focusListener
	 */
	private void setNew(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		indexProds = 0;
		idProduct = -1;
		relationRestaurantProducts.clear();
		context.removeAll();
		attach(context, psql, focusListener);
		context.repaint();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		// Effettua selezione ristorante
		rests = (List<Restaurant>)new RestaurantDAO(new Restaurant()).select(0, psql);
		
		// Effettua selezione della join!
		relationRestaurantProducts = (List<Relation_RestaurantProduct>)new RelationRestaurantProductDAO(new Relation_RestaurantProduct()).select(0, psql);
		
		System.out.println(relationRestaurantProducts.size());
		
		// Box ristorante
		JComboBox<String> boxRest = new JComboBox<String>();
		for(Restaurant r : rests) {
			boxRest.addItem(r.getName());
			boxRest.putClientProperty(r.getName(), r.getId_restaurant());
		}
		boxRest.setBounds(205, 258, 391, 39);
		
		// Form
		Form form = new Form();
		
		GenericPanelImage genericPanelImage = new GenericPanelImage(10, 71, 185, 177);
		genericPanelImage.setBorder(new LineBorder(new Color(166, 166, 166)));
		genericPanelImage.addMouseListener(new MouseAdapter() {
			
			@Override
		    public void mouseClicked(MouseEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(null);
				
				File image = fileChooser.getSelectedFile();
				if(image != null) {
					
					if(image.getName().toLowerCase().endsWith("png")  || 
					   image.getName().toLowerCase().endsWith("jpg")  || 
					   image.getName().toLowerCase().endsWith("jpeg")) {
						
						try {
							genericPanelImage.setImage(ImageIO.read(image));
							filePath = fileChooser.getSelectedFile().getAbsolutePath();
						}catch(Exception eImg) {
							JOptionPane.showMessageDialog(null, "Errore caricamento immagine", "Errore", JOptionPane.ERROR_MESSAGE);
						}
						
					}else {
						JOptionPane.showMessageDialog(null, "Attenzione, immagine non valida!\nI formati supportati sono png, jpg e jpeg.", "Errore", JOptionPane.ERROR_MESSAGE);
					}	
				}
				
			}
			
		});
		
		JTextField txtNomeDelProdotto = new JTextField();
		txtNomeDelProdotto.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNomeDelProdotto.setHorizontalAlignment(SwingConstants.CENTER);
		txtNomeDelProdotto.setText("Nome del prodotto");
		txtNomeDelProdotto.setBounds(205, 71, 391, 45);
		txtNomeDelProdotto.setBackground(new Color(240, 240, 240));
		txtNomeDelProdotto.setColumns(10);
		txtNomeDelProdotto.addFocusListener(focusListener);
		form.addToForm(txtNomeDelProdotto);
		
		JTextField txtPrice = new JTextField();
		txtPrice.setText("Prezzo");
		txtPrice.setHorizontalAlignment(SwingConstants.CENTER);
		txtPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtPrice.setColumns(10);
		txtPrice.setBackground(SystemColor.menu);
		txtPrice.setBounds(205, 127, 185, 45);
		txtPrice.addFocusListener(focusListener);
		txtPrice.putClientProperty("tipology", "numeric");
		form.addToForm(txtPrice);
		
		JTextField txtPz = new JTextField();
		txtPz.setText("Pezzi");
		txtPz.setHorizontalAlignment(SwingConstants.CENTER);
		txtPz.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtPz.setColumns(10);
		txtPz.setBackground(SystemColor.menu);
		txtPz.setBounds(411, 127, 185, 45);
		txtPz.addFocusListener(focusListener);
		txtPz.putClientProperty("tipology", "numeric");
		form.addToForm(txtPz);
		
		JCheckBox chManageQuantity = new JCheckBox("Non gestire le quantità");
		chManageQuantity.setBounds(411, 179, 185, 23);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(205, 209, 391, 39);
		comboBox.addItem("ESENTE DA IVA");
		comboBox.addItem("IVA 4%");
		comboBox.addItem("IVA 10%");
		comboBox.addItem("IVA 22%");
		
		JButton btnNew = new JButton("Nuovo");
		btnNew.setBounds(10, 10, 150, 50);
		btnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setNew(context, psql, focusListener);
			}
		});
		
		JButton btnSave = new JButton("Salva");
		btnSave.setBounds(170, 10, 150, 50);
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Validazione fallita
				if(!form.validate()) {
					JOptionPane.showMessageDialog(null, "Attenzione, validate i campi obbligatori!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Validazione fallita!
				if(!form.validateValueForm()) {
					JOptionPane.showMessageDialog(null, "Attenzione, validare correttamente i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Ottieni iva
				String value = comboBox.getSelectedItem().toString();
				double vat   = 0;
				switch(value.toLowerCase()) {
					
					case "iva esente":
						vat = 0;
					break;
					
					case "iva 4%":
						vat = 4;
					break;
					
					case "iva 10%":
						vat = 10;
					break;
					
					case "iva 22%":
						vat = 22;
					break;
				
				}
				
				// Sposta l'immagina sotto la cartella di progetto
				if(filePath != null) {

					FileOutputStream fos = null;
					FileInputStream  fis = null;
					try {
						
						// Ottieni file!
						File img = new File(filePath);
						
						// Generic uuid
						String uuid = UUID.randomUUID().toString();
						String ext  = "";
						
						// Immagine PNG
						if(img.getName().toLowerCase().endsWith("png")) {
							ext = "png";
						}

						// Immagine JPG
						if(img.getName().toLowerCase().endsWith("jpg")) {
							ext = "jpg";
						}
						
						// Immagine JPEG
						if(img.getName().toLowerCase().endsWith("jpeg")) {
							ext = "jpeg";
						}
						
						// Scrivi sul nuovo file!
						fis = new FileInputStream(img);
						byte[] byteFile = fis.readAllBytes();
						fos = new FileOutputStream(new File(System.getProperty("user.home") + "\\delivery_elements\\" + uuid + "." + ext));
						fos.write(byteFile);
						
						// Aggiorn filePath
						filePath = System.getProperty("user.home") + "\\delivery_elements\\" + uuid + "." + ext;
						
					}catch(Exception errFis) {
						JOptionPane.showMessageDialog(null, "Attenzione, errore di scrittura/lettura immagine: " + errFis, "Errore", JOptionPane.ERROR_MESSAGE);
					}finally {
						try {
							
							// Chiusura output stream!
							if(fos != null)
								fos.close();
							
							// Chiusura input stream!
							if(fis != null)
								fis.close();
							
						} catch (IOException err) {
							JOptionPane.showMessageDialog(null, "Attenzione, errore chiusura buffer scrittura e lettura: " + err, "Errore", JOptionPane.ERROR_MESSAGE);
						}
					}
					
				}
				
				// Imposta prodotto
				Product product = new Product();
				product.setName(txtNomeDelProdotto.getText());
				product.setPrice(Double.parseDouble(txtPrice.getText().replace(",", ".")));
				product.setVat_number(vat);
				product.setManage_quantity(chManageQuantity.isSelected());
				product.setImg_path(filePath);
				
				// Salva
				psql.insertQuery(new ProductDAO(product).insert(0, psql), new InterfaceSuccessErrorDAO() {
					
					@Override
					public void ok() {
					
						// Ottieni id del ristorante
						String idRestaurant = (String)boxRest.getClientProperty(boxRest.getSelectedItem());
						
						// Effettua selezione dei dati
						int idProduct = ((List<Product>)new ProductDAO(new Product()).select(1, psql)).get(0).getId();
						
						// Crea la relazione
						int quantity = Integer.parseInt(txtPz.getText());
						
						// Crea modello
						Restaurant_Product restaurant_Product = new Restaurant_Product();
						restaurant_Product.setId_product(idProduct);
						restaurant_Product.setId_restaurant(idRestaurant);
						restaurant_Product.setQuantity(quantity);
					
						// Inserisci relazione!
						psql.insertQuery(new Restaurant_ProductDAO(restaurant_Product).insert(0, psql), new InterfaceSuccessErrorDAO() {
							
							@Override
							public void ok() {
								setNew(context, psql, focusListener);
								JOptionPane.showMessageDialog(null, "Prodotto inserito correttamente!");
							}
							
							@Override
							public void err(String e) {
								JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
							}
							
						});
						
					}
					
					@Override
					public void err(String e) {
						JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
					}
				});
				
			}
		});
		
		JButton btnDel = new JButton("Elimina");
		btnDel.setBounds(330, 10, 150, 50);
		btnDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Non in stato di nuovo!
				if(idProduct != -1) {
					
					// Set del prodotto per la cancellazione con i campi necessari!
					Product product = new Product();
					product.setId(idProduct);
					product.setPrice(-1);
					product.setVat_number(-1);
					product.setImg_path(null);
					product.setName(null);
					
					psql.deleteQuery(new ProductDAO(product).delete(0, psql, idProduct), new InterfaceSuccessErrorDAO() {
						
						@Override
						public void ok() {
							setNew(context, psql, focusListener);
							JOptionPane.showMessageDialog(null, "Prodotto cancellato correttamente!");
						}
						
						@Override
						public void err(String e) {
							JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
						}
					});
					
				}else {
					JOptionPane.showMessageDialog(null, "Attenzione, al momento ti trovi in fase di creazione di un prodotto!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnPre = new JButton("<<");
		btnPre.setBounds(490, 10, 150, 50);
		btnPre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(indexProds == 0) {
					JOptionPane.showMessageDialog(null, "Attenzione, non ci sono più prodotti!", "Attenzione", JOptionPane.WARNING_MESSAGE);
					indexProds = 0;
					setNew(context, psql, focusListener);
				}else {
					try {
						
						// Spostati indietro
						indexProds--;
						if(indexProds == (relationRestaurantProducts.size() - 1)) {
							indexProds--;
						}
						
						// Prodotto
						Relation_RestaurantProduct product = relationRestaurantProducts.get(indexProds);
						txtNomeDelProdotto.setText(product.getName());
						txtPrice.setText(String.valueOf(product.getPrice()).replace(".", ","));
						genericPanelImage.setImage(ImageIO.read(new File(product.getImg_path())));
						txtPz.setText(String.valueOf(product.getQuantity()));
						
						// Id del prodotto
						idProduct = product.getId();
						
						// Imposta valore iva nella comboBox
						switch((int)product.getVat_number()) {
						
							case 0:
								comboBox.setSelectedIndex(0);
							break;
							
							case 4:
								comboBox.setSelectedIndex(1);
							break;
						
							case 10:
								comboBox.setSelectedIndex(2);
							break;
							
							case 22:
								comboBox.setSelectedIndex(3);
							break;
						}
						
						// CheckBox
						chManageQuantity.setSelected(product.isManage_quantity());
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + e, "Attenzione", JOptionPane.ERROR_MESSAGE);
					}
				}
					
			}
		});
		
		JButton btnNext = new JButton(">>");
		btnNext.setBounds(650, 10, 150, 50);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(indexProds < relationRestaurantProducts.size()) {
					try {
						
						// Prodotto
						Relation_RestaurantProduct product = relationRestaurantProducts.get(indexProds);
						txtNomeDelProdotto.setText(product.getName());
						txtPrice.setText(String.valueOf(product.getPrice()).replace(".", ","));
						genericPanelImage.setImage(ImageIO.read(new File(product.getImg_path())));
						txtPz.setText(String.valueOf(product.getQuantity()));
						
						// Id del prodotto
						idProduct = product.getId();
						
						// Imposta valore iva nella comboBox
						switch((int)product.getVat_number()) {
						
							case 0:
								comboBox.setSelectedIndex(0);
							break;
							
							case 4:
								comboBox.setSelectedIndex(1);
							break;
						
							case 10:
								comboBox.setSelectedIndex(2);
							break;
							
							case 22:
								comboBox.setSelectedIndex(3);
							break;
						}
						
						// CheckBox
						chManageQuantity.setSelected(product.isManage_quantity());
						
						indexProds++; // Inserisci prodotto e spostati
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + e, "Attenzione", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "Attenzione, non ci sono più prodotti!", "Attenzione", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
		context.add(genericPanelImage);
		context.add(txtNomeDelProdotto);
		context.add(txtPrice);
		context.add(txtPz);
		context.add(chManageQuantity);
		context.add(comboBox);
		context.add(btnNew);
		context.add(btnSave);
		context.add(btnDel);
		context.add(btnPre);
		context.add(btnNext);
		context.add(boxRest);
		
	}

}
