package com.uni.frame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class SubscriptionWindowEventsClass {

	/**
	 * 
	 * @param frm
	 * @param eventsInterface
	 */
	public SubscriptionWindowEventsClass(JFrame frm, SubscriptionWindowEventsInterface eventsInterface) {
		frm.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) { eventsInterface.openWindow(e); }
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) { eventsInterface.closeWindow(e); }
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
