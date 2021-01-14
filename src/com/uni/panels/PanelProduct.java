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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import com.uni.frame.Form;
import com.uniproject.dao.AllergenDAO;
import com.uniproject.dao.AllergyProductDAO;
import com.uniproject.dao.InterfaceSuccessErrorDAO;
import com.uniproject.dao.ProductDAO;
import com.uniproject.dao.RelationRestaurantProductDAO;
import com.uniproject.dao.RestaurantDAO;
import com.uniproject.dao.Restaurant_ProductDAO;
import com.uniproject.entity.Allergen;
import com.uniproject.entity.AllergyProduct;
import com.uniproject.entity.Product;
import com.uniproject.entity.Relation_RestaurantProduct;
import com.uniproject.entity.Restaurant;
import com.uniproject.entity.Restaurant_Product;
import com.uniproject.jdbc.PostgreSQL;

@FunctionalInterface
interface IScroll{
	void doneOperation();
}

public class PanelProduct implements PanelAttachInterface {

	// Resulset della select
	int indexProds = -1;
	
	// Percorso immagine
	private String filePath = null;
	
	// Id prodotto selezionato
	private int idProduct = -1;
	
	// List di ristoranti
	private List<Restaurant> rests;
	
	// Lista di relazioni
	private List<Relation_RestaurantProduct> relationRestaurantProducts;
	
	// Lista allergie
	private List<Allergen> listAllergy;
	private ModelListCheck modelListCheckAllergy;
	
	// Errore inserimento ristoranti
	private boolean errInsertRestaurant = false;
	private String eInsertRestaurant	= "";

	// Errore inserimento allerge
	private boolean errInsertAllergy = false;
	private String eInsertAllergy	 = "";
	
	// Oggetti grafici
	private JTextField txtNomeDelProdotto;
	private JTextField txtPrice;
	private JTextField txtPz;
	private JCheckBox chManageQuantity;
	private JComboBox<String> comboBoxIva;
	private GenericPanelImage genericPanelImage;
	private ModelListCheck modelListCheck;
	
	// Ultimo id prodotto
	private int lastIdProduct = 0;
	
	// Errori di aggiornamento
	private boolean errUpAllergy;
	private String eUpAllergy;
	private boolean errUpRest;
	private String eUpRest;
	
	/**
	 * 
	 * @param context
	 * @param psql
	 * @param focusListener
	 */
	private void setNew(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		indexProds = -1;
		idProduct = -1;
		relationRestaurantProducts.clear();
		filePath = null;
		context.removeAll();
		attach(context, psql, focusListener);
		context.repaint();
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
		
		// Inizializza lista di relazione
		relationRestaurantProducts = new ArrayList<>();
		
		// Effettua selezione ristorante
		rests = (List<Restaurant>)new RestaurantDAO(new Restaurant()).select(0, psql);
		
		// Ottieni relazione
		List<Relation_RestaurantProduct> relationRestaurantProductsTmp = (List<Relation_RestaurantProduct>)new RelationRestaurantProductDAO(new Relation_RestaurantProduct()).select(0, psql);
		
		// Pulizia dei campi duplicati nella selezione
		for(Relation_RestaurantProduct rrp : relationRestaurantProductsTmp) {
			boolean contains = false;
			for(Relation_RestaurantProduct rrpi : relationRestaurantProducts) {
				if(rrpi.getId() == rrp.getId()) {
					contains = true;
					break;
				}
			}
			if(!contains)
				relationRestaurantProducts.add(rrp);
		}
		
		// Box ristorante
		modelListCheck = new ModelListCheck(391);
		modelListCheck.setGenericProp("id");
		
		// Ristoranti nella lista
		for(Restaurant r : rests) {
			modelListCheck.add(false, r.getName(), r.getId_restaurant());
		}
		JScrollPane scrollPane = modelListCheck.build(205, 258, 435, 200);
		
		// Ottieni lista allegie
		listAllergy = (List<Allergen>)new AllergenDAO(new Allergen()).select(0, psql);
		
		modelListCheckAllergy = new ModelListCheck(185);
		modelListCheckAllergy.setGenericProp("id");
		
		// Cicla su allergeni
		for(Allergen al : listAllergy) {
			modelListCheckAllergy.add(false, al.getName_allergen(), al.getId_allergen());
		}
		JScrollPane scrollPaneListAllergy = modelListCheckAllergy.build(10, 258, 185, 200);
		
		// Form
		Form form = new Form();
		
		genericPanelImage = new GenericPanelImage(10, 71, 185, 177);
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
		
		txtNomeDelProdotto = new JTextField();
		txtNomeDelProdotto.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNomeDelProdotto.setHorizontalAlignment(SwingConstants.CENTER);
		txtNomeDelProdotto.setText("Nome del prodotto");
		txtNomeDelProdotto.setBounds(205, 71, 435, 45);
		txtNomeDelProdotto.setBackground(new Color(240, 240, 240));
		txtNomeDelProdotto.setColumns(10);
		txtNomeDelProdotto.addFocusListener(focusListener);
		form.addToForm(txtNomeDelProdotto);
		
		txtPrice = new JTextField();
		txtPrice.setText("Prezzo");
		txtPrice.setHorizontalAlignment(SwingConstants.CENTER);
		txtPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		//txtPrice.setColumns(10);
		txtPrice.setBackground(SystemColor.menu);
		txtPrice.setBounds(205, 127, 207, 45);
		txtPrice.addFocusListener(focusListener);
		txtPrice.putClientProperty("tipology", "double");
		form.addToForm(txtPrice);
		
		txtPz = new JTextField();
		txtPz.setText("Pezzi");
		txtPz.setHorizontalAlignment(SwingConstants.CENTER);
		txtPz.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtPz.setColumns(10);
		txtPz.setBackground(SystemColor.menu);
		txtPz.setBounds(433, 127, 207, 45);
		txtPz.addFocusListener(focusListener);
		txtPz.putClientProperty("tipology", "integer");
		form.addToForm(txtPz);
		
		chManageQuantity = new JCheckBox("Non gestire le quantità");
		chManageQuantity.setBounds(433, 179, 185, 23);
		
		comboBoxIva = new JComboBox<String>();
		comboBoxIva.setBounds(205, 209, 435, 39);
		comboBoxIva.addItem("ESENTE DA IVA");
		comboBoxIva.addItem("IVA 4%");
		comboBoxIva.addItem("IVA 10%");
		comboBoxIva.addItem("IVA 22%");
		
		// -----------
		// -- Nuovo --
		// -----------
		JButton btnNew = new JButton("Nuovo Prodotto");
		btnNew.setBackground(Color.orange);
		btnNew.setBounds(10, 10, 150, 50);
		btnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setNew(context, psql, focusListener);
			}
		});
		
		// ----------------- 
		// -- Salvataggio --
		// -----------------
		JButton btnSave = new JButton("Salva Prodotto");
		btnSave.setBounds(170, 10, 150, 50);
		btnSave.setBackground(Color.orange);
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(idProduct != -1) {
					JOptionPane.showMessageDialog(null, "Sei in stato di modifica", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
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
				
				// Ottieni numeri ristoranti selezionati
				int count = 0;
				for(Object o : modelListCheck.getResult()) {
					if((boolean) ((Object[])o)[0]) {
						count++;
						if(count > 1) break;
					}
				}
				
				// Prodotti con gestione quantità
				if(count > 1 && !chManageQuantity.isSelected()) {
					JOptionPane.showMessageDialog(null, "Attenzione, prodotti con gestione delle quantità possono essere associati ad un singolo ristorante!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Ottieni iva
				String value = comboBoxIva.getSelectedItem().toString();
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

				boolean theresOneTrue = false;
				for(Object o : modelListCheck.getResult()) {
					if((boolean) ((Object[])o)[0]) {
						theresOneTrue = true; 
						break;
					}
				}
				
				if(!theresOneTrue) {
					JOptionPane.showMessageDialog(null, "Associare il prodotto ad almeno un ristorante!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
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
						filePath = null;
						insertRelations(psql, context, focusListener, true); 
					}
					
					@Override
					public void err(String e) {
						if(e.startsWith("ERRORE: un valore chiave duplicato viola il vincolo univoco")) {
							e = "Il codice ristorante è già presente in archivio!";
						}else {
							e = "";
						}
						JOptionPane.showMessageDialog(null, e.equals("") ? "Inserimento tipologia fallito: " : e, "Errore", JOptionPane.ERROR_MESSAGE);
					}
				});
				
			}
		});
		
		// -------------------
		// -- Cancellazione --
		// -------------------
		JButton btnDel = new JButton("Elimina Prodotto");
		btnDel.setBackground(Color.orange);
		btnDel.setBounds(330, 10, 150, 50);
		btnDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Non in stato di nuovo!
				if(idProduct != -1) {
					
					if(JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare il prodotto?", "Attenzione", JOptionPane.YES_NO_OPTION) == 0) {

						// Ottieni prima il path dell'immagine
						filePath = null;
						List<Product> pImgPath = (List<Product>)new ProductDAO(new Product()).select(2, psql, String.valueOf(idProduct));
						if(!pImgPath.get(0).getImg_path().equals("null")) {
							filePath = pImgPath.get(0).getImg_path();
						}
						
						// Set del prodotto per la cancellazione con i campi necessari! E' in cascata!
						Product product = new Product();
						product.setId(idProduct);
						
						psql.deleteQuery(new ProductDAO(product).delete(0, psql), new InterfaceSuccessErrorDAO() {
							
							@Override
							public void ok() {
								
								// Elimina l'immagine del prodotto!
								if(filePath != null)
									new File(filePath).delete();
								JOptionPane.showMessageDialog(null, "Cancellazione prodotto avvenuta con successo!");
								setNew(context, psql, focusListener);
								
							}
							
							@Override
							public void err(String e) {
								JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
							}
						});
						
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Attenzione, al momento ti trovi in fase di creazione di un prodotto!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		// --------------
		// -- Modifica --
		// --------------
		JButton btnMod = new JButton("Salva Modifica");
		btnMod.setBackground(Color.orange);
		btnMod.setBounds(490, 10, 150, 50);
		btnMod.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Non in stato di nuovo!
				if(idProduct != -1) {
					
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
					
					// Prodotti con gestione quantità
					if(modelListCheck.getResult().size() > 1 && !chManageQuantity.isSelected()) {
						JOptionPane.showMessageDialog(null, "Attenzione, prodotti con gestione delle quantità possono essere associati ad un singolo ristorante!", "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					AllergyProduct allergyProduct = new AllergyProduct();
					allergyProduct.setId(idProduct);
					psql.deleteQuery(new AllergyProductDAO(allergyProduct).delete(0, psql), new InterfaceSuccessErrorDAO() {
						
						@Override
						public void ok() {
							
							Restaurant_Product restProd = new Restaurant_Product();
							restProd.setId_product(idProduct);
							
							psql.deleteQuery(new Restaurant_ProductDAO(restProd).delete(0, psql), new InterfaceSuccessErrorDAO() {
								
								@Override
								public void ok() {
									
									// Ottieni iva
									String value = comboBoxIva.getSelectedItem().toString();
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
									
									boolean theresOneTrue = false;
									for(Object o : modelListCheck.getResult()) {
										if((boolean) ((Object[])o)[0]) {
											theresOneTrue = true; 
											break;
										}
									}
									
									if(!theresOneTrue) {
										JOptionPane.showMessageDialog(null, "Associare il prodotto ad almeno un ristorante!", "Errore", JOptionPane.ERROR_MESSAGE);
										return;
									}
									
									// Cancellazione immagine!
									String img_path = ((Product)(new ProductDAO(new Product()).select(2, psql, String.valueOf(idProduct))).get(0)).getImg_path();
									if(!img_path.toString().equals("null"))
										new File(img_path).delete();
										
									Product product = new Product();
									product.setId(idProduct);
									product.setName(txtNomeDelProdotto.getText());
									product.setPrice(Double.parseDouble(txtPrice.getText().replace(",", ".")));
									product.setVat_number(vat);
									product.setManage_quantity(chManageQuantity.isSelected());
									product.setImg_path(filePath);
									
									psql.updateQuery(new ProductDAO(product).update(0, psql), new InterfaceSuccessErrorDAO() {
										
										@Override
										public void ok() { 
											filePath = null;
											insertRelations(psql, context, focusListener, false);
										}
										
										@Override
										public void err(String e) {
											JOptionPane.showMessageDialog(null, "Attenzione, si è verificato di aggiornamento: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
										}
										
									});
									
								}
								
								@Override
								public void err(String e) {
									errUpRest = true;
									eUpRest = e;
								}
							});
							
							if(errUpAllergy) { 
								JOptionPane.showMessageDialog(null, "Attenzione, si è verificato di aggiornamento: " + errUpRest, "Errore", JOptionPane.ERROR_MESSAGE);
								return;
							}
							
						}
						
						@Override
						public void err(String e) {
							errUpAllergy = true;
							eUpAllergy = e;
						}
						
					});
					
					if(errUpAllergy) { 
						JOptionPane.showMessageDialog(null, "Attenzione, si è verificato di aggiornamento: " + errUpAllergy, "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "Attenzione, al momento ti trovi in fase di creazione di un prodotto!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		});
		
		// ----------------
		// -- Precedente --
		// ----------------
		JButton btnPre = new JButton("<<");
		btnPre.setBackground(Color.orange);
		btnPre.setBounds(170, 480, 150, 50);
		btnPre.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scrollProducts(psql, true, new IScroll() {
					
					@Override
					public void doneOperation() {
						setNew(context, psql, focusListener);
					}
					
				});
			}
		});
		
		// ----------------
		// -- Successivo --
		// ----------------
		JButton btnNext = new JButton(">>");
		btnNext.setBackground(Color.orange);
		btnNext.setBounds(330, 480, 150, 50);
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scrollProducts(psql, false, null);
			}
		});
		
		context.add(genericPanelImage);
		context.add(txtNomeDelProdotto);
		context.add(txtPrice);
		context.add(txtPz);
		context.add(chManageQuantity);
		context.add(comboBoxIva);
		context.add(btnNew);
		context.add(btnSave);
		context.add(btnDel);
		context.add(btnPre);
		context.add(btnNext);
		context.add(btnMod);
		context.add(scrollPane);
		context.add(scrollPaneListAllergy);
		
	}
	
	/**
	 * 
	 * @param psql
	 * @param isPrevious
	 * @param iScroll
	 */
	private void scrollProducts(PostgreSQL psql, boolean isPrevious, IScroll iScroll) {
		
		if(relationRestaurantProducts.size() > 0) {
		
			if(isPrevious) {
				
				if(indexProds <= 0) {
					JOptionPane.showMessageDialog(null, "Attenzione, non ci sono più prodotti!", "Attenzione", JOptionPane.WARNING_MESSAGE);
					iScroll.doneOperation();
					return;
				}else {
						
					// Spostati indietro
					indexProds--;
					if(indexProds == (relationRestaurantProducts.size() - 1)) {
						indexProds--;
					}
					
				}
				
			}else {
				indexProds++;
				if(indexProds >= relationRestaurantProducts.size()) {
					indexProds--;
					JOptionPane.showMessageDialog(null, "Attenzione, non ci sono più prodotti!", "Attenzione", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			
			try {						
				
				// Prodotto
				genericPanelImage.setImage(null);
				Relation_RestaurantProduct relProdRest = relationRestaurantProducts.get(indexProds);
				txtNomeDelProdotto.setText(relProdRest.getName_product());
				txtPrice.setText(String.valueOf(relProdRest.getPrice()).replace(".", ","));
				if(!relProdRest.getImg_path().equals("null")) {
					try {
						genericPanelImage.setImage(ImageIO.read(new File(relProdRest.getImg_path())));
						filePath = relProdRest.getImg_path();
					}catch(Exception e) {}
				}else {
					filePath = null;
				}
				txtPz.setText(String.valueOf(relProdRest.getQuantity()));
				
				// Id del prodotto
				idProduct = relProdRest.getId();
				
				// Imposta valore iva nella comboBox
				switch((int)relProdRest.getVat_number()) {
				
					case 0:
						comboBoxIva.setSelectedIndex(0);
					break;
					
					case 4:
						comboBoxIva.setSelectedIndex(1);
					break;
				
					case 10:
						comboBoxIva.setSelectedIndex(2);
					break;
					
					case 22:
						comboBoxIva.setSelectedIndex(3);
					break;
				}
				
				// CheckBox
				chManageQuantity.setSelected(relProdRest.isManage_quantity());
				
				// Aggiorna lista
				modelListCheck.reset();
				
				// Ottieni la lista di ristoranti collegati al prodotto
				List<Restaurant_Product> restaurant_Products = (List<Restaurant_Product>)
																	new Restaurant_ProductDAO(new Restaurant_Product()).select(0, psql, idProduct);
				
				for(Restaurant_Product rp : restaurant_Products) {
					modelListCheck.update(rp.getId_restaurant(), true);
				}
				
				// Aggiorna lista allergie
				modelListCheckAllergy.reset();
				
				List<AllergyProduct> allergyProduct = (List<AllergyProduct>)
														new AllergyProductDAO(new AllergyProduct()).select(2, psql, idProduct);
				
				for(AllergyProduct ap : allergyProduct) {
					modelListCheckAllergy.update(ap.getId_allergen(), true);
				}
				
			} catch (Exception err) {
				JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + err, "Attenzione", JOptionPane.ERROR_MESSAGE);
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono prodotti!", "Attenzione", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
			
	/**
	 * 
	 * @param psql
	 * @param context
	 * @param focusListener
	 */
	private void insertRelations(PostgreSQL psql, JPanel context, FocusListener focusListener, boolean insertState) {
		
		// Ottieni id dei ristoranti
		List<Object> modelCheckRest = modelListCheck.getResult();
		List<String> codes          = new ArrayList<>();
		
		// Aggiungi codici ristoranti selezionati!
		for(Object o : modelCheckRest) {
			if((boolean) ((Object[])o)[0])
				codes.add((String) ((Object[])o)[2]);
		}
		
		// Effettua selezione dell'ultimo id
		if(insertState)
			lastIdProduct = ((List<Product>)new ProductDAO(new Product()).select(1, psql)).get(0).getId();
		else
			lastIdProduct = idProduct;
		
		// Crea la relazione
		int quantity = Integer.parseInt(txtPz.getText());
		
		// Inserimento dei ristoranti
		for(String idRest : codes) {
			Restaurant_Product restaurant_Product = new Restaurant_Product();
			restaurant_Product.setId_product(lastIdProduct);
			restaurant_Product.setId_restaurant(idRest);
			restaurant_Product.setQuantity(quantity);
			// Inserisci relazione!
			psql.insertQuery(new Restaurant_ProductDAO(restaurant_Product).insert(0, psql), new InterfaceSuccessErrorDAO() {
				
				@Override
				public void ok() {
					
					// Ottieni id allergie
					List<Object> modelCheckAllergen     = modelListCheckAllergy.getResult();
					List<String> codesAllergen          = new ArrayList<>();
					
					// Aggiungi codici ristoranti selezionati!
					for(Object o : modelCheckAllergen) {
						if((boolean) ((Object[])o)[0])
							codesAllergen.add((String) ((Object[])o)[2]);
					}
					
					if(codesAllergen.size() == 0) {
						//setNew(context, psql, focusListener);
						//JOptionPane.showMessageDialog(null, (insertState ? "Inserimento" : "Modifica") + " avvenuto con successo!");
						return;
					}
					
					// Inserire la relazione con l'id
					for(String codeAllergen : codesAllergen) {
						
						// Crea modello relazione allergie prodotto
						AllergyProduct allergyProduct = new AllergyProduct();
						allergyProduct.setId(lastIdProduct);
						allergyProduct.setId_allergen(codeAllergen);
						
						psql.insertQuery(new AllergyProductDAO(allergyProduct).insert(0, psql), new InterfaceSuccessErrorDAO() {
							
							@Override
							public void ok() {
								// .. Non fa nulla..
							}
							
							@Override
							public void err(String e) {
								eInsertAllergy = e;
								errInsertAllergy = true;
							}
						});
						
					}
					
				}
				
				@Override
				public void err(String e) {
					errInsertRestaurant = true;
					eInsertRestaurant = e;
				}
				
			});
		}
		
		// Errore di inserimento!
		if(errInsertRestaurant || errInsertAllergy) {
			JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + eInsertRestaurant, "Errore", JOptionPane.ERROR_MESSAGE);
			errInsertRestaurant = false;
			lastIdProduct = 0;
		}else {
			filePath = null;
			setNew(context, psql, focusListener);
			JOptionPane.showMessageDialog(null, (insertState ? "Inserimento" : "Modifica") + " avvenuto con successo!");
		}
		
	}
	
}

