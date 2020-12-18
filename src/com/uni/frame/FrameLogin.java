package com.uni.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.uniproject.dao.UsersDAO;
import com.uniproject.entity.Users;
import com.uniproject.jdbc.PostgreSQL;
import com.uniproject.main.Main;

@SuppressWarnings("serial")
public class FrameLogin extends JFrame {

	// Db
	private PostgreSQL psql;
	
	/**
	 * 
	 * @param psql
	 */
	public void setPsql(PostgreSQL psql) {
		this.psql = psql;
	}
	
	/**
	 * Costruttore
	 */
	public FrameLogin() {
		super("Login");
		setResizable(false);
		setSize(new Dimension(500, 500));
		setBackground(new Color(245, 245, 245));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		add(buildContent());
		setLocationRelativeTo(null);
	}
	
	/**
	 * 
	 * @return
	 */
	private JPanel buildContent() {
		
		Form form = new Form();
		
		// Listener del focus
		FocusListener focusListener = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) { // Focus perso
				e.getComponent().setBackground(Color.white);
			}
			
			@Override
			public void focusGained(FocusEvent e) { // Ottiene focus
				e.getComponent().setBackground(Color.orange);
			}
		};
		
		// Pannello
		JPanel panelLogin = new JPanel();
		panelLogin.setBounds(0, 0, 500, 500);
		panelLogin.setLayout(null);
		panelLogin.setBackground(new Color(245, 245, 245));
		
		// Username
		JLabel lbUser = new JLabel("Username");
		lbUser.setBounds(10, 10, 240, 30);
		lbUser.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 17));
		
		JTextField fieldUser = new JTextField();
		fieldUser.setBounds(10, 40, 200, 30);
		fieldUser.addFocusListener(focusListener);
		lbUser.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 17));
		form.addToForm(fieldUser);
		
		// Password
		JLabel lbPass = new JLabel("Password");
		lbPass.setBounds(10, 80, 240, 30);
		lbPass.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 17));
		
		JPasswordField fieldPass = new JPasswordField();
		fieldPass.setBounds(10, 110, 200, 30);
		fieldPass.addFocusListener(focusListener);
		fieldPass.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 17));
		form.addToForm(fieldPass);
		
		// Immagine di sfondo
		FileInputStream fisBack = null;
		JLabel labelBackImage   = null;
		try {
			ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResourceAsStream(SingletonImage.getInstance().getImage(0)).readAllBytes());
			imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(300, 236, Image.SCALE_SMOOTH));
			labelBackImage = new JLabel(imageIcon);
			labelBackImage.setBounds(180, 200, 300, 236);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Attenzione, si è verificato un errore: " + e, "Errore", JOptionPane.ERROR_MESSAGE);
		}finally {
			if(fisBack != null)
				try {
					fisBack.close();
				} catch (IOException e1) {}
		}
		
		// Parent da passare alle dialog
		JFrame parent = this;
		
		// Bottone login
		JButton btnLogin = new JButton("Accedi");
		btnLogin.setBounds(10, 160, 200, 40);
		btnLogin.setBackground(Color.orange);
		btnLogin.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 17));
		btnLogin.addActionListener(new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Controlla validazione!
				if(!form.validate()) {
					JOptionPane.showMessageDialog(parent, "Attenzione, validare i campi evidenziati!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Controlla che ci sia connessione al db
				if(psql.isClosedConnection()) {
					JOptionPane.showMessageDialog(parent, "Attenzione, connessione al db assente!", "Errore", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Valida oggetto per la selezine
				Users userLogin = new Users();
				userLogin.setUsername(fieldUser.getText());
				userLogin.setPassword(fieldPass.getText());
				
				// Dao per la selezine, controlla login!
				List<?> result = new UsersDAO(userLogin).select(0, psql, userLogin.getUsername(), userLogin.getPassword());
				if(result.size() > 0) {
					JFrame frameMenu = new FrameMenu((Users)result.get(0), psql);
					new SubscriptionWindowEventsClass(frameMenu, new SubscriptionWindowEventsInterface() {
						
						@Override
						public void openWindow(WindowEvent e) {}
						
						@Override
						public void closeWindow(WindowEvent e) { psql.closeConnection(); } // Chiusura connessione
						
					});
					frameMenu.setVisible(true);
					Main.IS_OPENED_MENU = true;
					dispose(); // Chiudi finestra login
				}else {
					JOptionPane.showMessageDialog(parent, "Attenzione, login fallito.\nRiprova!", "Errore", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		// Aggiungi componenti al pannello
		panelLogin.add(lbUser);
		panelLogin.add(fieldUser);
		panelLogin.add(lbPass);
		panelLogin.add(fieldPass);
		panelLogin.add(btnLogin);
		if(labelBackImage != null)
			panelLogin.add(labelBackImage);
		
		return panelLogin;
	}
	
}
