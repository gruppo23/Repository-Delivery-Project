package com.uni.panels;


import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.uniproject.dao.ProductDAO;
import com.uniproject.dao.Relation_AllergyProductDAO;
import com.uniproject.entity.Product;
import com.uniproject.entity.Relation_AllergyProduct;
import com.uniproject.jdbc.PostgreSQL;

public class PanelListProduct implements PanelAttachInterface{
	
	// Tabella
	private JTable tableListAllergyProduct;
	
	// Righe tabella
	private Object [][] rows;
	
	@Override
	public void attach(JPanel context, PostgreSQL psql, FocusListener focusListener) {
	
		// Lista ristoranti in relazione con tipologia
		List<Relation_AllergyProduct> listRelationAllergyProduct = 
											(List<Relation_AllergyProduct>)
												new Relation_AllergyProductDAO(new Relation_AllergyProduct()).select(0, psql);

		if(listRelationAllergyProduct.size() > 0) {
			
			// Selezionare tutte le tipologie di prodotti creati
			List<Product> productType = (List<Product>)new ProductDAO(new Product()).select(0, psql);
			
			// Controlla se il numero di prodotti è > 0
			if(productType.size() == 0) {
				JOptionPane.showMessageDialog(null, "Attenzione, registrare almeno un prodotto!", "Errore", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Colonne tabella
			Object [] columns = {
				"Codice prodotto", "Nome prodotto", "Prezzo", "IVA", "Allergene presente"
			};
			
			// Righe
			rows = new Object[listRelationAllergyProduct.size()][5];
			
			// Cicla per le associazioni
			int index = 0;
			for(Relation_AllergyProduct alp : listRelationAllergyProduct) {
				rows[index][0] = alp.getId();
				rows[index][1] = alp.getName_product();
				rows[index][2] = alp.getPrice();
				rows[index][3] = alp.getVat_number();
				rows[index][4] = alp.getName_allergen();
				index++;
			}
			
			tableListAllergyProduct = new JTable(rows, columns);
			
			JScrollPane sp = new JScrollPane(tableListAllergyProduct);
			sp.setBounds(10, 10, 1250, 550);
			
			// Aggiungi scrollbar!
			context.add(sp);			
			
		}else {
			JOptionPane.showMessageDialog(null, "Attenzione, non ci sono allergeni associati a prodotti in lista");
		}
		
	}

}
