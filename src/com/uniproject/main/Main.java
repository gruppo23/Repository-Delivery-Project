package com.uniproject.main;

import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import com.uni.frame.FrameLogin;
import com.uni.frame.SubscriptionWindowEventsClass;
import com.uni.frame.SubscriptionWindowEventsInterface;
import com.uni.logic.SingletonComuni;
import com.uniproject.jdbc.PostgreSQL;

public class Main {

	// Connessione al database
	private static PostgreSQL psql;
	
	// Controlla se apre nuova finestra
	public static boolean IS_OPENED_MENU = false;
	
	public static void main(String[] args) throws ClassNotFoundException, 
												  InstantiationException, 
												  IllegalAccessException, 
												  UnsupportedLookAndFeelException {
		
		// Carica comuni
		SingletonComuni.getInstance().loadComuni();
		
		// Se non esiste, nella home users crea una cartella di immagini 
		if(!new File(System.getProperty("user.home") + "\\delivery_elements\\").exists())
			new File(System.getProperty("user.home") + "\\delivery_elements\\").mkdir();
		
		// Look and feel
		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			System.out.println(info.getName());
		    if ("Nimbus".equals(info.getName())) {
		        UIManager.setLookAndFeel(info.getClassName());
		        break;
		    }
		}
		
		// Apertura del frame e sottoscrizione dei soli eventi necessari!
		FrameLogin frameLogin = new FrameLogin();
		new SubscriptionWindowEventsClass(frameLogin, new SubscriptionWindowEventsInterface() {
			
			@Override
			public void openWindow(WindowEvent e) {
				
				// Apri connessione e passala al frame!
				psql = new PostgreSQL("localhost", "5432", "oopbd_project", "uni_project", "oopbd2020").openConnection();
				frameLogin.setPsql(psql);
				
				// Chiama procedure per la valorizzazione degli allergeni
				try {
					psql.callProcedure("insertintoallergen").execute();
				}catch(Exception eProc) {
					System.out.println(eProc);
					return;
				}
				
			}
			
			@Override
			public void closeWindow(WindowEvent e) {
				if(!IS_OPENED_MENU)
					psql.closeConnection(); // Chiudi connessione!
			}
			
		});
		frameLogin.setVisible(true);
		
	}

}
